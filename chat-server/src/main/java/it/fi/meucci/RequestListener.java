package it.fi.meucci;

import it.fi.meucci.exceptions.CommandNotRecognizedException;
import it.fi.meucci.exceptions.DestNotCorrectException;
import it.fi.meucci.exceptions.DisconnectException;
import it.fi.meucci.exceptions.HandlerException;
import it.fi.meucci.exceptions.NameNotOkException;
import it.fi.meucci.exceptions.NeedNameException;
import it.fi.meucci.utils.Message;
import it.fi.meucci.utils.ServerAnnouncement;
import it.fi.meucci.utils.Username;

import java.io.DataOutputStream;
import java.io.IOException;
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
    }

    /**
     * Implementazione del metodo Thread.run()
     */
    @Override
    public void run() {
        // Sicuramente il client appena connesso non ha un username.
        // Manda la lista di client *ABILITATI* a parlare (sendList())
        // Manda un messaggio missing name
        // ServerAnnouncement.needNameMessage()
        // Serializza il messaggio ^
        // send(messaggio serializzato in JSON)

        /*
         * While non ha un nome (){}
         */

        /*
         * SERVER ANNOUNCEMENT con JOINED e il nome del nuovo utente
         */

        // Inizio della procedura "ciclata"
        while (allowedToRun) { // oppure finché il socket non è chiuso
            // Ascolto dei messaggi sul canale
            // Deserializzazione del messaggio
            // Interpretazione del messaggio: spendisco il msg al metodo HANDLE

            // IMPORTANTISSIMO!
            // Creo un nuovo Thread temporaneo per eseguire HANDLE
            // con new Thread(new Runnable(run(){ handle() }));
        }

        // Appena la socket è chiusa, manda un messaggio SERVER ANNOUNCEMENT con LEFT
    }

    /**
     * In base al messaggio ricevuto, ha un comportamento diverso.
     * 
     * @param msg È il messaggio appena ricevuto
     * @throws IOException
     */
    public void handle(Message msg) throws IOException {
        /*
         * Il messaggio ha diversi tipi.
         * Se è un MESSAGGIO {
         * Handler.handleMessage(msg);
         * } SE è UN COMANDO {
         * Handler.handleCommand(msg);
         * } ALTRIMENTI {
         * Handler.handle(msg);
         * }
         * 
         * // IMPORTANTE
         * Se questi messaggi ritornano delle eccezioni, è importante gestirle!
         */

        try {
            switch (msg.getType()) {
                case COMMAND:
                    Handler.handleCommand(msg);
                    break;
                case MESSAGE:
                    Handler.handleMessage(msg);
                    break;
                default:
                    Handler.handle(msg);
                    break;
    
            }
        } catch (HandlerException e) {
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
        String str = om.writeValueAsString(msg);
        outputStream.writeBytes(str);
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
}
