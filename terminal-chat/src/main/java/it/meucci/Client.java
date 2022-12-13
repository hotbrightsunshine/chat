package it.meucci;

import it.meucci.utils.Message;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.meucci.utils.ServerAnnouncement;
import it.meucci.utils.Username;

/**
* Rappresenta il Client.
* - Si connette al server
* - Ha un ciclo che consente la lettura continua da tastiera (verrà sostituito e modificato all'implementazione di JavaFX)
* - Valida i messaggi scritti dall'utente (String -> Pojo)
* - Parsa il Pojo Message in una stringa JSON
* - Spedisce il messaggio validato JSON sul canale di Output del Socket
* - Ha un metodo per arrestare l'esecuzione
 */

public class Client {
    private String username;
    private String pendingUsername;
    private Socket socket;
    private ReplyListener listener;
    private DataOutputStream output;
    private BufferedReader input;
    ObjectMapper objectmapper = new ObjectMapper();
    public UserMessagesList userMessagesList;

     /**
  * - Costruttore che avvia in automatico la connessione.
  * - Deve inizializzare le *stream* del socket
  * @throws IOException
  */
    public Client(Inet4Address address, int port) throws IOException {
        socket = new Socket(address, port);
    }

    public void init() throws IOException {
        userMessagesList = new UserMessagesList();
            output = new DataOutputStream(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void send(Message message){
        try {
            output.writeBytes(objectmapper.writeValueAsString(message) + '\n');
        } catch (JsonProcessingException e) {
            // TODO e.printStackTrace();
        } catch (IOException e){
            //TODO
        }
    }

    public void changeUsername(String newUsername){
        this.username = newUsername;
    }

    public Message read() throws IOException{
        String str = input.readLine();
        return objectmapper.readValue(str, Message.class);
    }

    public void stop() throws IOException {
        socket.close();
    }

    public synchronized Socket getSocket(){
        return socket;
    }

    public synchronized String getUsername() {
        if(username == null){
            return "";
        }
        return username;
    }

    public synchronized boolean isReadyForTextInterface(){
        //System.out.println(this.socket.isConnected() + " " + this.userMessagesList.contains(Username.everyone));
        return this.socket.isConnected() && this.userMessagesList.contains(Username.everyone);
    }

    public synchronized String getPendingUsername() {
        return pendingUsername;
    }

    public synchronized void setPendingUsername(String pendingUsername) {
        this.pendingUsername = pendingUsername;
    }
}
