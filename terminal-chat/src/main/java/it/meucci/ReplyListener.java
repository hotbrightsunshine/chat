package it.meucci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.meucci.utils.Message;
import it.meucci.utils.ServerAnnouncement;
import it.meucci.utils.Username;

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
                Message m = objectmapper.readValue(read, Message.class);
                if(Message.humanize(m)!=null)
                {
                    System.out.println( Message.humanize(m));
                    
                }
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
        // Methods here are NOT to be printed because they are printed immediately after
        ServerAnnouncement sa = null;
        ArrayList<String> args = new ArrayList<>();
        try {
            sa = ServerAnnouncement.valueOf(message.getArgs().get(0).toString().toUpperCase());
            args = message.getArgs();
            args.remove(0);
        } catch(Exception e){}
        switch (sa){
            case JOINED:
                App.client.userMessagesList.addUser(args.get(0));
                break;

            case LEFT:
                App.client.userMessagesList.removeUser(args.get(0));
                break;

            case LIST:
                ArrayList<String> usernames = args;
                App.client.userMessagesList.addUser(Username.everyone);
                for (String username : usernames) {
                    App.client.userMessagesList.addUser(username);
                }
                break;

            case USERNAME_CHANGED:
                App.client.userMessagesList.updateUser(args.get(0), args.get(1));
                break;
            
            default:
                break;
        }
    }
}
