package it.fi.meucci;

import it.fi.meucci.exceptions.HandlerException;
import it.fi.meucci.utils.CommandType;
import it.fi.meucci.utils.Message;
import it.fi.meucci.utils.ServerAnnouncement;
import it.fi.meucci.utils.Username;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestListener implements Runnable {

    // L'username (Può essere nullable)
    private Username username;

    // La connessione con il client
    private Socket socket;

    // Dice se il thread può andare oppure no
    // è falso quando c'è un'eccezione, il socket si chiude etc.
    private boolean allowedToRun;

    private ObjectMapper om = new ObjectMapper();
    private DataOutputStream outputStream;
    private BufferedReader inputStream;
    /**
     * Costruttore di RequestListener
     * 
     * @param socket Il socket
     * @param father Il server madre
     * @throws IOException
     */
    public RequestListener(Socket socket) throws IOException {
        this.socket = socket;
        allowedToRun = true;
        outputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void changeName(Username usr) throws IOException {
        //L'username non deve contenere spazi e non può essere vuoto
        if(usr.getUsername().trim().equals("")){
            send(ServerAnnouncement
            .createServerAnnouncement(ServerAnnouncement.NAME_NOT_OK, username));
        }
        if(App.server.isUserAvailable(usr)){
            // Mando all'utente che il nome è ok
            send(ServerAnnouncement 
            .createServerAnnouncement(ServerAnnouncement.NAME_OK, username));
            // dico a tutti gli altri che l'utente ha cambiato nome
            if(username != null){
                send(ServerAnnouncement
                .createUsernameChangedAnnouncement(username, usr));
            }
            this.username = usr;
        } else {
            send(ServerAnnouncement 
            .createServerAnnouncement(ServerAnnouncement.NAME_NOT_OK, username));
        }
    }

    /**
     * Implementazione del metodo Thread.run()
     */
    @Override
    public void run() {
        System.out.println("Nuova connessione iniziata. ");
        try {
            // Invio al client appena connesso una lista di client attualmente connessi e abilitati.
            sendList();
            // Dice al client che ha bisogno di un nome.
            send(ServerAnnouncement.createServerAnnouncement(ServerAnnouncement.NEED_NAME, username));
        } catch (JsonProcessingException e) {    
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Fino a che non ha un nome
        while(username==null){
            try {
                // leggi il messaggio
                Message msg = read();
                CommandType t_msg = null;
                Username usr = null;

                try {
                    // Estraggo il tipo e il parametro per leggibilità
                    t_msg = CommandType.fromString(msg.getArgs().get(0));
                    usr = new Username(msg.getArgs().get(1));
                } catch (Exception e) {
                    send(ServerAnnouncement.createServerAnnouncement(
                        ServerAnnouncement.COMMAND_NOT_RECOGNIZED, usr));
                    continue;
                }
                

                // Se il tipo di comando è CHANGE NAME
                if(t_msg.equals(CommandType.CHANGE_NAME)){
                    changeName(usr);
                } else {
                    // Ha mandato un messaggio diverso da name, quindi 
                    send(ServerAnnouncement.createServerAnnouncement(
                        ServerAnnouncement.NEED_NAME, usr));
                    // La richiesta effettuata dal client non viene presa in considerazione.
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                // I parametri indirizzati non sono validi per args, quindi il comando non è valido
                e.printStackTrace();
            }
        }
        // L'utente ha un nome. 
        // Dovrei aggiungere un massimo di errori per non ingolfare il server. 

        // Si è connesso il client!
        try {
            send(ServerAnnouncement.createJoinedAnnouncement(username));
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }

        // Inizio della procedura "ciclata"
        while (allowedToRun) { // oppure finché il socket non è chiuso
            try {
                Message msg = read();

                new Thread(new MessageHandlerThread(msg) {
                    @Override
                    public void run() {
                        try {
                            handle(this.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }

        // Appena la socket è chiusa, manda un messaggio SERVER ANNOUNCEMENT con LEFT
        Message msgLeft = ServerAnnouncement.createLeftAnnouncement(username);
        try {
            send(msgLeft);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * In base al messaggio ricevuto, ha un comportamento diverso.
     * 
     * @param msg È il messaggio appena ricevuto
     * @throws IOException
     */
    public void handle(Message msg) throws IOException {
        try {
            switch (msg.getType()) {
                case COMMAND:
                    if(msg.getArgs().get(0).equals(CommandType.CHANGE_NAME.toString())){
                        changeName(new Username(msg.getArgs().get(1)));
                    }
                    else {
                        Handler.handleCommand(msg);
                    }
                    
                break;
                case MESSAGE:
                    Handler.handleMessage(msg);
                break;
                default:
                    Handler.handle(msg);
                break;
            }
        } catch (HandlerException e){
            e.print();
            send(
                ServerAnnouncement
                .createServerAnnouncement(
                    e.getServerAnnouncement(),
                username)
            );
        }
    }

    /**
     * Manda al client un messaggio con dentro la lista degli utenti
     * 
     * @throws JsonProcessingException Lanciata quando il parsing non riesce
     * @throws IOException             Lanciata quando non il mandato non può essere
     *                                 inviato
     */
    public void sendList() throws JsonProcessingException, IOException {
        // Lista di utenti autorizzati: Server.getUsername
        ArrayList<Username> usernames = App.server.getUsernames();
        // Generazione del messaggio ServerAnnouncement.listAnnouncement(lista di utenti
        // autorizzati)
        Message msg = ServerAnnouncement.createListAnnouncement(usernames, username);
        // send
        send(msg);
    }

    /**
     * Manda un messaggio al client
     * 
     * @param msg Il messaggio da inviare
     * @throws IOException Lanciata quando il messaggio non può essere mandato
     */
    public void send(Message msg) throws IOException {
        if(msg.getTo()==Username.everyone())
        {
            App.server.send(msg);
            return;
        }
        String str = om.writeValueAsString(msg);
        outputStream.writeBytes(str);
    }


    public Message read() throws IOException{
        String read = inputStream.readLine();
        return om.readValue(read, Message.class);
    }

    public boolean isAllowedToRun() {
        return allowedToRun;
    }

    public void setAllowedToRun(boolean allowedToRun) {
        this.allowedToRun = allowedToRun;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    //prova
}
