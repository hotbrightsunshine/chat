package it.meucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.meucci.utils.Message;
import it.meucci.utils.ServerAnnouncement;

/**
 * -Classe deputata alla lettura dell'output del socket
 * -Gestisce ogni messaggio inviato
 */
public class ReplyListener implements Runnable {
    private BufferedReader input;
    private ObjectMapper objectmapper = new ObjectMapper();

    public ReplyListener(){
        try {
            input = new BufferedReader(new InputStreamReader(App.client.getSocket().getInputStream()));
        } catch (IOException e) {}
    }

    public void run(){
        while(!App.client.getSocket().isClosed())
        {
            try {
                String read = input.readLine();
                System.out.println(read);
                //System.out.println("ReplyListener run read" + read);
                Message m = objectmapper.readValue(read, Message.class);
                handle(m);
            } catch (Exception e) {
                break;
            }
        }
    }
/** 
 * -Riceve messaggi dal server e li gestisce
 * -Controlla il tipo di messaggio e in base al messaggio si comporta in modo diverso
 * @param message
*/
    public void handle(Message message)
    {

        //controllo il tipo di messaggio inviato se è un messaggio per l'utente o se è un SERVER_ANN
        //ulteriore controllo sul tipo di SERVER_ANN
        //stampa il messaggio
        switch (message.getType()){
            case MESSAGE:
                handleMessage(message);
                break;
            case SERVER_ANN:
                handleServerAnn(message);
                break;
            default:
                break;
        }
    }

    private void handleMessage(Message message){
        App.client.userMessagesList.addMessage(message);
        // refresh page if that's what textinterface is serving
    }

    /**
     * 
     * @param message
     */
    private void handleServerAnn(Message message){

        ServerAnnouncement sa = null;
        ArrayList<String> args = new ArrayList<>();
        try {
            sa = ServerAnnouncement.valueOf(message.getArgs().get(0).toString().toUpperCase());
            args = message.getArgs();
            args.remove(0);
        } catch(Exception e){}
        switch (sa){
            case JOINED:
                HandleMethods.join(args.get(0));
                break;
            case LEFT:
                HandleMethods.left(args.get(0));
                break;
            case NAME_OK:
                HandleMethods.nameOk();
                break;
            case NAME_NOT_OK:
                HandleMethods.nameNotOk();
                break;
            case NEED_NAME:
                HandleMethods.needName();
                break;
            case LIST:
                HandleMethods.list(args);
                break;
            case DEST_NOT_CORRECT:
                HandleMethods.destNotOk();
                break;
            case DISCONNECT:
                HandleMethods.disconnect();
                break;
            case COMMAND_NOT_RECOGNIZED:
                HandleMethods.commandNotRecognized();
                break;
            case USERNAME_CHANGED:
                HandleMethods.usernameChanged(args.get(0), args.get(1));
                break;
        }
    }
}
