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

/**
 * The class used to read from the socket's input stream. It handles received messages with proper Handler methods.
 */
public class RequestListener implements Runnable {
    private  String username;
    private final Socket socket;

    /**
     * Says whether this instance of Request Listener is allowed to run
     */
    public  boolean allowedToRun;

    private  final ObjectMapper om = new ObjectMapper();
    private  final DataOutputStream outputStream;
    private  final BufferedReader inputStream;

    /**
     * @param socket Socket to be handled
     */
    public RequestListener(Socket socket) throws IOException {
        this.socket = socket;
        allowedToRun = true;
        outputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Changes name if available
     * @param usr the new username
     * @throws IOException when it's unable to send errors to the socket's output stream
     */
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
     *  Thread.run() implementation.
     *  Starts the communication with a List message and a Need Name Message
     *  Catches a bunch of exceptions
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
     * Handles the received message
     * @param msg The message to be handled
     * @throws IOException when it's impossible to write to the socket's output stream
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
                username));

            if(e.getServerAnnouncement().equals(ServerAnnouncement.DISCONNECT)) {
                this.allowedToRun = false;
            }
        }
    }

    /**
     * Sends to the client a list of connected users
     * @throws JsonProcessingException When parsing fails
     * @throws IOException             When writing to the socket's output stream fails
     */
    public void sendList() throws JsonProcessingException, IOException {
        ArrayList<String> usernames = App.server.getUsernames();
        Message msg = ServerAnnouncement.createListAnnouncement(usernames, username);
        write(msg);
    }

    /**
     * Sends the message to the output stream
     * @param msg The message to be sent
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

    /**
     * Reads from the socket's input stream
     * @return The just read message
     * @throws IOException When it's impossible to read from the socket's input stream
     */
    public  Message read() throws IOException {
        String read = inputStream.readLine();
        if (read == null) {
            allowedToRun = false;
            return null;
        }
        return om.readValue(read, Message.class);
    }

    /**
     * @return the client's username
     */
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
