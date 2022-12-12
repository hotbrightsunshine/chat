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
import java.net.SocketException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestListener implements Runnable {

    // L'username (Può essere nullable)
    private String username;

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
     * @throws IOException
     */
    public RequestListener(Socket socket) throws IOException {
        this.socket = socket;
        allowedToRun = true;
        outputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void changeName(String usr) throws IOException {
        //L'username non deve contenere spazi e non può essere vuoto
        if(usr.trim().equals("")){
            write(ServerAnnouncement
            .createServerAnnouncement(ServerAnnouncement.NAME_NOT_OK, username));
        } else 
        if(App.server.isUserAvailable(usr)){
            // Mando all'utente che il nome è ok
            write(ServerAnnouncement
            .createServerAnnouncement(ServerAnnouncement.NAME_OK, username));
            // dico a tutti gli altri che l'utente ha cambiato nome, solo se quello di prima non era "", altrimenti vuol dire che è entrato
            if(!getUsername().equals("")){
                sendBroadcast(ServerAnnouncement
                .createUsernameChangedAnnouncement(username, usr));
            }
            this.username = usr;
        } else {
            write(ServerAnnouncement
            .createServerAnnouncement(ServerAnnouncement.NAME_NOT_OK, username));
        }
        System.out.println(this.username);
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
            write(ServerAnnouncement.createServerAnnouncement(ServerAnnouncement.NEED_NAME, username));
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
                if(msg == null){
                    this.allowedToRun = false;
                    break;
                }
                CommandType t_msg = null;
                String usr = null;

                try {
                    // Estraggo il tipo e il parametro per leggibilità
                    t_msg = CommandType.fromString(msg.getArgs().get(0));
                    usr = msg.getArgs().get(1);
                } catch (Exception e) {
                    write(ServerAnnouncement.createServerAnnouncement(
                        ServerAnnouncement.COMMAND_NOT_RECOGNIZED, usr));
                    continue;
                }
                

                // Se il tipo di comando è CHANGE NAME
                if(t_msg.equals(CommandType.CHANGE_NAME)){
                    changeName(usr);
                } else if (t_msg.equals(CommandType.DISCONNECT)){
                    allowedToRun = false;
                    break;
                }
                else {
                    // Ha mandato un messaggio diverso da name, quindi 
                    write(ServerAnnouncement.createServerAnnouncement(
                        ServerAnnouncement.NEED_NAME, usr));
                    // La richiesta effettuata dal client non viene presa in considerazione.
                }

            } catch (IOException e) {
                //e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                // I parametri indirizzati non sono validi per args, quindi il comando non è valido
                e.printStackTrace();
            }
        }
        // L'utente ha un nome. 
        // Dovrei aggiungere un massimo di errori per non ingolfare il server. 

        // Si è connesso il client! (se il username è giusto)
        if (username != null){
            try {
                sendBroadcast(ServerAnnouncement.createJoinedAnnouncement(username));
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }
        }


        // Inizio della procedura "ciclata"
        while (allowedToRun) { // oppure finché il socket non è chiuso
            try {
                System.out.println("Aspetto");
                Message msg = read();
                if(msg == null){
                    break; // La connessione si è chiusa
                } else {
                    handle(msg);
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        
        // Appena la socket è chiusa, manda un messaggio SERVER ANNOUNCEMENT con LEFT
        // Questa porzione di codice è accessibile anche quando l'username è uguale a null; 
        // Se l'username non è settato (si è chiuso prima di mettere l'username), non deve mandare il messaggio di announcement
        try {
            this.socket.close();
            if(this.username != null){
                Message msgLeft = ServerAnnouncement.createLeftAnnouncement(username);
                sendBroadcast(msgLeft);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        App.server.getListeners().remove(this);
        System.out.println("Ho finito");
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
                        changeName(msg.getArgs().get(1));
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
            write(
                ServerAnnouncement
                .createServerAnnouncement(
                    e.getServerAnnouncement(),
                username)
            );

            if(e.getServerAnnouncement().equals(ServerAnnouncement.DISCONNECT)){
                System.out.println("DEVO CHIUDERE");
                this.allowedToRun = false;
            }
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
        ArrayList<String> usernames = App.server.getUsernames();
        // Generazione del messaggio ServerAnnouncement.listAnnouncement(lista di utenti
        // autorizzati)
        Message msg = ServerAnnouncement.createListAnnouncement(usernames, username);
        // send
        write(msg);
    }

    /**
     * Manda un messaggio al client
     * 
     * @param msg Il messaggio da inviare
     * @throws IOException Lanciata quando il messaggio non può essere mandato
     */
    public void write(Message msg) {
        /*
         * I have confirmed the reason. 
         * You are writing while the other end has already closed the connection. 
         * The fix is not to do that. As the other end isn't reading it, there isn't any point anyway. 
         * As I also said, if this is happening there is something wrong with your application protocol specification or implementation, 
         * most probably that you don't even have one.
         */
        String str = "";
        try {
            str = om.writeValueAsString(msg);
        } catch (JsonProcessingException e) {
            System.out.println(e.getStackTrace());
        }
        try {
            outputStream.writeBytes(str+ '\n');
        } catch (IOException e){
            allowedToRun = false;
            return;
        } 
        
         
        
    }

    public void send(Message msg) throws IOException {
        if(msg.getTo().equals(Username.everyone)){
            sendBroadcast(msg);
            return;
        }
        ArrayList<RequestListener> r = App.server.getListeners();
        for(RequestListener l : r){
            if(l.username.equals(msg.getTo())){
                l.write(msg);
                return;
            }
        }
    }

    public void sendBroadcast(Message msg) throws IOException {
        ArrayList<RequestListener> r = App.server.getListeners();
        for(RequestListener l : r){
                l.write(msg);
        }
    }

    public Message read() throws IOException{
        String read = inputStream.readLine();
        if (read == null){
            allowedToRun = false;
            return null;
        }
        return om.readValue(read, Message.class);
    }

    public boolean isAllowedToRun() {
        return allowedToRun;
    }

    public void setAllowedToRun(boolean allowedToRun) {
        this.allowedToRun = allowedToRun;
    }

    public String getUsername() {
        if (this.username == null){
            return "";
        }
        return username;
    }

    public void setUsername(String username) {
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
