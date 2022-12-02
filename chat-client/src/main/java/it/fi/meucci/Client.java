package it.fi.meucci;

import it.fi.meucci.utils.Message;
import it.fi.meucci.utils.Username;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.Scanner;

/**
 * Rappresenta il Client.
 - Si connette al server
 - Ha un ciclo che consente la lettura continua da tastiera (verrà sostituito e modificato all'implementazione di JavaFX)
 - Valida i messaggi scritti dall'utente (String -> Pojo)
 - Parsa il Pojo Message in una stringa JSON
 - Spedisce il messaggio validato JSON sul canale di Output del Socket
 - Ha un metodo per arrestare l'esecuzione
 */

public class Client {
    private Username username;
    private Socket socket;
    private ReplyListener listener;

     /**
  * - Costruttore che avvia in automatico la connessione.
  * - Deve inizializzare le *stream* del socket
  */
    public Client(Inet4Address address, int port) throws IOException {
        socket = new Socket(address, port);
        //inizializzazione attributi (streamReader ecc..)
    }

    public void send(Message message){
        //Serializza il messaggio
        //invia al server il messaggio
    }

       /**
    * - Il client termina la connessione con il server
  */
    public void stop() throws IOException {
        socket.close();
        listener.close();
    }

    /**
     * - Viene letto ciò che è stato scritto dall'utente
     * - Avviene poi la validazione del messaggio
     * - In caso il messaggio sia valido richiama il metodo send
     * - Altrimenti stampa un messaggio di errore
     */
    public void keyboardlistener(Scanner s)
    {
        for(;;)
        {
            //LETTURA DA TASTIERA
            //SALVATAGGIO IN STRINGA DELLA LETTURA
            //RICHIAMA IL METODO VALIDATE che si trova in message.java
            // SE VALIDATE MESSAGE != NULL VALIDO quindi richiamo il metodo send()
            // SENNO' STAMPO ERRORE
        }
    }

    
}
