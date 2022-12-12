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

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public void init(){
        userMessagesList = new UserMessagesList();
        try {
            output = new DataOutputStream(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        listener = new ReplyListener();
        Thread t = new Thread(listener);
        t.start();
    }

    public void send(Message message){
        //Serializza il messaggio
        //invia al server il messaggio
        try {
            output.writeBytes(objectmapper.writeValueAsString(message) + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }

    public void changeUsername(String newUsername){
        System.out.println("User wants to change from " +
                username + " to " + newUsername);
    }

    public Message read() throws IOException{
        String str = input.readLine();
        return objectmapper.readValue(str, Message.class);
    }

       /**
    * - Il client termina la connessione con il server
    * @throws IOException
  */
    public void stop() throws IOException {
        socket.close();
    }

    public Socket getSocket(){
        return socket;
    }

    public String getUsername() {
        if(username == null){
            return "";
        }
        return username;
    }

    public boolean isReadyForTextInterface(){
        return this.socket.isConnected() && this.userMessagesList.contains(Username.everyone);
    }
}
