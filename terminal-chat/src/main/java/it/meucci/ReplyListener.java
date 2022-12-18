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
 * Class used to read the socket's input stream. Handles any received message.
 */
public class ReplyListener implements Runnable {
    private BufferedReader input;
    private final ObjectMapper objectmapper = new ObjectMapper();

    /**
     * Creates a new reply listener. Uses `App.client` to access client's properties.
     */
    public ReplyListener() {
        try {
            input = new BufferedReader(new InputStreamReader(App.client.getSocket().getInputStream()));
        } catch (IOException e) {
            System.out.println("An error occurred while creating a Buffered Reader for App.client.getSocket()");
        }
    }

    /**
     * Inherited from Runnable interface. Describes the mechanism with which the client listens to messages.
     * In a loop:
     * - Reads from the socket's input stream
     * - Serializes the message
     * - Prints out the <i>humanized</i> message (cfr. {@link it.meucci.utils.Message}.humanize())
     * - Handles the message if there's need to.
     *      (e.g. for an error without parameters such as {@link it.meucci.utils.ServerAnnouncement}
     *      there is no need to handle it because printing its content to the user is more than enough)
     */
    public void run() {
        while(!App.client.getSocket().isClosed())
        {
            try {
                String read = input.readLine();
                Message m = objectmapper.readValue(read, Message.class);
                if(Message.humanize(m)!=null) System.out.println( Message.humanize(m));
                handle(m);
            } catch (Exception e) {
                break;
            }
        }
    }

    /**
     * Handles a received message.
     * It first checks whether it is a Message or a Server Announcement
     * and redirects the execution flow towards the proper method.
     * @param message The message to be handled.
     */
    public void handle(Message message)
    {
        switch (message.getType()) {
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

    /**
     * Adds the message to the client's UserMessagesList
     * @param message the messages to be handled.
     */
    private void handleMessage(Message message) {
        App.client.userMessagesList.addMessage(message);
    }

    /**
     * Handles -- if needed -- the just received Server Announcement.
     * Methods here are NOT to be printed because they are printed immediately after they are received.
     * @param message
     */
    private void handleServerAnn(Message message) {
        ServerAnnouncement sa = null;
        ArrayList<String> args = new ArrayList<>();
        try {
            sa = ServerAnnouncement.valueOf(message.getArgs().get(0).toUpperCase());
            args = message.getArgs();
            args.remove(0);
        } catch(Exception e) {}

        if(sa == null){
            return;
        }

        switch (sa) {
            case JOINED:
                App.client.userMessagesList.addUser(args.get(0));
                break;

            case LEFT:
                App.client.userMessagesList.removeUser(args.get(0));
                break;

            case LIST:
                App.client.userMessagesList.addUser(Username.everyone);
                for (String username : args) {
                    App.client.userMessagesList.addUser(username);
                }
                break;

            case USERNAME_CHANGED:
                App.client.userMessagesList.updateUser(args.get(0), args.get(1));
                break;

            case NAME_OK:
                App.client.changeUsername();
            
            default:
                break;
        }
    }
}
