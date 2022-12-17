package it.fi.meucci;
import it.fi.meucci.exceptions.HandlerException;
import it.fi.meucci.logger.Log;
import it.fi.meucci.logger.LogType;
import it.fi.meucci.utils.CommandType;
import it.fi.meucci.utils.Message;
import it.fi.meucci.utils.ServerAnnouncement;
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
    private  String username;

    // La connessione con il client
    private final Socket socket;

    // Dice se il thread può andare oppure no
    // è falso quando c'è un'eccezione, il socket si chiude etc.
    public  boolean allowedToRun;

    private  final ObjectMapper om = new ObjectMapper();
    private  final DataOutputStream outputStream;
    private  final BufferedReader inputStream;

    /**
     * Costruttore di RequestListener
     * 
     * @param socket Il socket
     */
    public RequestListener(Socket socket) throws IOException {
        this.socket = socket;
        allowedToRun = true;
        outputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void changeName(String usr) throws IOException {
        //L'username non deve contenere spazi e non può essere vuoto
        if(usr.trim().equals("")) {
            write(ServerAnnouncement
            .createServerAnnouncement(ServerAnnouncement.NAME_NOT_OK, username));
        } else if(App.server.isUserAvailable(usr)) {
            // Mando all'utente che il nome è ok
            write(ServerAnnouncement
            .createServerAnnouncement(ServerAnnouncement.NAME_OK, username));
            // dico a tutti gli altri che l'utente ha cambiato nome, solo se quello di prima non era "", altrimenti vuol dire che è entrato
            if(!getUsername().equals("")) {
                App.server.sendBroadcast(ServerAnnouncement
                .createUsernameChangedAnnouncement(username, usr));
            } else {
                App.server.sendBroadcast(ServerAnnouncement.createJoinedAnnouncement(usr));
            }
            username = usr;
        } else {
            write(ServerAnnouncement
            .createServerAnnouncement(ServerAnnouncement.NAME_NOT_OK, username));
        }
        Log.print(LogType.INFO, this + " Il nome utente dopo il Change Name è " + username);
    }

    /**
     * Implementazione del metodo Thread.run()
     */
    @Override
    public void run() {
        try {
            Log.print(LogType.INFO, this + ": Comunicazione iniziata");
            sendList();
            write(ServerAnnouncement.createServerAnnouncement(ServerAnnouncement.NEED_NAME, username));

            while (allowedToRun) {
                Message msg = read();
                if(msg != null) handle(msg);
                else break;
            }

            Log.print(LogType.INFO, this + ": Comunicazione in conclusione");
            this.socket.close();
            if(this.username != null) {
                Message msgLeft = ServerAnnouncement.createLeftAnnouncement(username);
                App.server.sendBroadcast(msgLeft);
            }

        } catch (JsonProcessingException e) {
            Log.print(LogType.ERROR, this + ": JsonProcessingException");
        } catch (IOException e) {
            Log.print(LogType.WARNING, this + ": IOException");
            allowedToRun = false;
        }   catch (Throwable e) {
            e.printStackTrace();
            Log.print(LogType.ERROR, this + ": Errore non riconosciuto");
        } finally {
            App.server.getListeners().remove(this);
            Log.print(LogType.INFO, this + ": Comunicazione conclusa");
        }
    }

    /**
     * In base al messaggio ricevuto, ha un comportamento diverso.
     * 
     * @param msg È il messaggio appena ricevuto
     */
    public void handle(Message msg) throws IOException {
        try {
            switch (msg.getType()) {
                case COMMAND:
                    Log.print(LogType.INFO, this + " : Gestione di un comando");
                    if(msg.getArgs().get(0).equals(CommandType.CHANGE_NAME.toString())) {
                        
                        changeName(msg.getArgs().get(1));
                    }
                    else {
                        Handler.handleCommand(msg);
                    }
                    
                break;
                case MESSAGE:
                    Log.print(LogType.INFO, this + ": Gestione di un messaggio");
                    Handler.handleMessage(msg);
                break;
                default:
                    Handler.handle(msg);
                break;
            }
        } catch (HandlerException e) {
            Log.print(LogType.INFO, this + ": Errore durante la gestione di un comando. Tipo: " + e.getServerAnnouncement().toString());
            e.print();
            write(
                ServerAnnouncement
                .createServerAnnouncement(
                    e.getServerAnnouncement(),
                username)
            );

            if(e.getServerAnnouncement().equals(ServerAnnouncement.DISCONNECT)) {
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
        ArrayList<String> usernames = App.server.getUsernames();
        Message msg = ServerAnnouncement.createListAnnouncement(usernames, username);
        write(msg);
    }

    /**
     * Manda un messaggio al client
     * 
     * @param msg Il messaggio da inviare
     */
    public  void write(Message msg) {
        String str = "";
        try {
            str = om.writeValueAsString(msg);
            outputStream.writeBytes(str+ '\n');
        } catch (JsonProcessingException e) {
            Log.print(LogType.ERROR, str);
        } catch (IOException e) {
            Log.print(LogType.WARNING, this + ": Chiusura forzata del thread durante la scrittura a causa di un errore IO");
            allowedToRun = false;
        } 
    }

    public  Message read() throws IOException {
        String read = inputStream.readLine();
        if (read == null) {
            allowedToRun = false;
            return null;
        }
        return om.readValue(read, Message.class);
    }

    public String getUsername() {
        if (username == null) {
            return "";
        }
        return username;
    }

    @Override
    public String toString() {
        return super.toString() + "; username: " + this.username;
    }
}
