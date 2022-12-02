package it.fi.meucci;

import it.fi.meucci.utils.Message;
import it.fi.meucci.utils.Username;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    private Username username;
    private Socket socket;
    private ReplyListener listener;
    private BufferedReader keyboard;
    private DataOutputStream output;
    private BufferedReader input;
    ObjectMapper objectmapper = new ObjectMapper();

     /**
  * - Costruttore che avvia in automatico la connessione.
  * - Deve inizializzare le *stream* del socket
  * @throws IOException
  */
    public Client(Inet4Address address, int port) throws IOException {
        socket = new Socket(address, port);
        try {
            output = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
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

       /**
    * - Il client termina la connessione con il server
    * @throws IOException
  */
    public void stop() throws IOException {
        socket.close();
        listener.close();
    }

   
  
 

    
}
