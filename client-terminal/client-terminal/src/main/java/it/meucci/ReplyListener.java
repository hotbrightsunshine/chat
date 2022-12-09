package it.meucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.meucci.lantern.GraphicalInterface;
import it.meucci.utils.Message;
import it.meucci.utils.Type;

/**
 * -Classe deputata alla lettura dell'output del socket
 * -Gestisce ogni messaggio inviato
 */
public class ReplyListener implements Runnable {
    private Client father;
    private BufferedReader keyboard;
    private DataOutputStream output;
    private BufferedReader input;
    private Socket client_socket;
    private ObjectMapper objectmapper = new ObjectMapper();


    public ReplyListener(Client father){
        this.client_socket = father.getSocket();
        this.father = father;
        try {
            input = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run(){
       //while client.stop == false fai handle
       // sennò richiami metodo close()
       while(!client_socket.isClosed())
       {
            try {
                String read = input.readLine();
                Message m = objectmapper.readValue(read, Message.class);
                handle(m);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
       }
       try {
            close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
/** 
 * -Riceve messaggi dal server e li gestisce
 * -Controlla il tipo di messaggio e in base al messaggio si comporta in modo diverso
*/
    public void handle(Message message)
    {
        //controllo il tipo di messaggio inviato se è un messaggio per l'utente o se è un SERVER_ANN
        //ulteriore controllo sul tipo di SERVER_ANN
        //stampa il messaggio
        System.out.println(message);
        //ONLY FOR TESTING; TODO
        if(message.getType().equals(Type.SERVER_ANN) && message.getArgs().get(0).equals("NEED_NAME")){
            GraphicalInterface.chooseNick();
        }
    }
/**
 * Chiude la lettura dell'output del socket
 * @throws IOException
 */
    public void close() throws IOException{
        //chiusura socket
        father.stop();
    }
}