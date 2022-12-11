package it.meucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.meucci.textinterface.DisconnectPage;
import it.meucci.textinterface.TextInterface;
import it.meucci.utils.Message;
import it.meucci.utils.ServerAnnouncement;
import it.meucci.utils.Type;

/**
 * -Classe deputata alla lettura dell'output del socket
 * -Gestisce ogni messaggio inviato
 */
public class ReplyListener implements Runnable {
    private BufferedReader keyboard;
    private DataOutputStream output;
    private BufferedReader input;
    private ObjectMapper objectmapper = new ObjectMapper();


    public ReplyListener(){
        try {
            input = new BufferedReader(new InputStreamReader(App.client.getSocket().getInputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run(){
       //while client.stop == false fai handle
       // sennò richiami metodo close()
       while(!App.client.getSocket().isClosed())
       {
            try {
                String read = input.readLine();
                Message m = objectmapper.readValue(read, Message.class);
                handle(m);
            } catch (Exception e) {
                TextInterface.switchTo(new DisconnectPage());
                break;
            }
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
        switch (message.getType()){
            case MESSAGE:
                break;
            case SERVER_ANN:
                break;
            default:
                break;
        }
    }

    private void handleMessage(Message message){

    }

    private void handleServerAnn(Message message){
        switch(ServerAnnouncement.valueOf(message.getArgs().get(0).toUpperCase())){

        }
    }
/**
 * Chiude la lettura dell'output del socket
 * @throws IOException
 */
    public void close() throws IOException{
        //chiusura socket
        App.client.stop();
    }
}
