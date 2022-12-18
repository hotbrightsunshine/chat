package it.meucci.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.meucci.App;
import java.util.ArrayList;

import static it.meucci.utils.CommandType.CHANGE_NAME;

/**
 * Messages that are exchanged between client and server.
 * They have a type, a recipient, and a sender.
 * Contain an optional field called "content", used for various purposes, depending on the type of message.
 * Has a static validate method that converts user input into a Message.
 */
public class Message
{
    private Type type;
    private String from;
    private String to;
    private ArrayList<String> args;


    /**
     * Message Costructor
     * @param type The Message type {@link it.meucci.utils.Type }
     * @param from The sender
     * @param to The addressee
     * @param args Arguments of the message. They follow a different syntax depending on the type of the message
     */
    public Message(
        @JsonProperty("type") Type type, 
        @JsonProperty("from") String from,
        @JsonProperty("to") String to,
        @JsonProperty("args") ArrayList<String> args) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.args = args;
    }

    /**
     * Creates a message of type MESSAGE
     * @param addressee the addressee
     * @param content the content to be sent
     * @return the composed message.
     */
    public static Message createMessage(String addressee, String content) {
        ArrayList<String> args  = new ArrayList<>();
        args.add(content);
        Message m = new Message(
            Type.MESSAGE, 
            App.client.getUsername(), 
            addressee, 
            args);
        return m;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }

    /**
     * Creates a message to change name
     * @param newName the new name the user wants to use
     * @return the composed message
     */
    public static Message createChangeNameCommand(String newName) {
        ArrayList<String> args = new ArrayList<>();
        args.add(CHANGE_NAME.toString());
        args.add(newName);
        Message m = new Message(Type.COMMAND, App.client.getUsername(), Username.server, args);
        //System.out.println(m);
        return m;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", args=" + args +
                '}';
    }

    /**
     * Humanizes the message in order to be correctly displayed.
     * Like handling methods, this is a wrapper to call humanize just once and avoid code duplication.
     * toString() could have be used, but we didn't want to mess with it since we wanted to reserve it for debug purposes.
     * @param m The message to be humanized
     * @return A string ready to be printed
     */
    public static String humanize(Message m) {
        switch(m.getType()) {
            case COMMAND:
                break;
            case MESSAGE:
            return humanizeMessage(m);
            case SERVER_ANN:
            return humanizeServerAnn(m);
            default:
            break;
            
        }
        return null;
    }

    /**
     * Humanizes a MESSAGE
     * @param m the Message to be humanized
     * @return the string ready to be printed
     */
    private static String humanizeMessage(Message m) {
        return "<" + m.from + " to " + m.to + "> "+m.args.get(0);
    }

    /**
     * Humanizes a SERVER_ANN.
     * Contains a big switch case to handle different type of {@link it.meucci.utils.ServerAnnouncement}
     * @param m the Server Announcement to be humanized
     * @return the string ready to be printed
     */
    private static String humanizeServerAnn(Message m) {
        switch(ServerAnnouncement.valueOf(m.getArgs().get(0))) {
            case COMMAND_NOT_RECOGNIZED:
                return "The command that you wrote was not recognized by the server.";

            case DEST_NOT_CORRECT:
                return "The addressee that you wrote is incorrect";

            case DISCONNECT:
                return "Disconnecting...";

            case JOINED:
                return "`" + m.getArgs().get(1) + "` joined.";

            case LEFT:
                return "`" + m.getArgs().get(1) + "` left.";

            case LIST:
                ArrayList<String> args = (ArrayList<String>) m.getArgs().clone();
                args.remove(0);
                ArrayList<String> usernames = args;
                if(usernames.size() == 0) {
                    return "No one else is connected to the server.";
                } else  {
                    return "People connected ATM: " + usernames.toString().replace('[', ' ').replace(']', ' ');
                }
            
            case NAME_NOT_OK:
                return "The name you choose is not available.";

            case NAME_OK:
                return "Your name has been successfully set.";

            case NEED_NAME:
                return "Your nickname is not set. Please provide one typing /nick <name>.";

            case USERNAME_CHANGED:
                return "`" + m.getArgs().get(1) + "` changed its username to `" + m.getArgs().get(2) + "`";

            default:
                break;
        }
        return null;
    }
}
