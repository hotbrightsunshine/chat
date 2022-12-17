package it.meucci.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.meucci.App;
import java.util.ArrayList;

import static it.meucci.utils.CommandType.CHANGE_NAME;

/**
 * I messaggi che vengono scambiati tra client e server.
 * Hanno un tipo, un destinatario, e un mittente.
 * Contengono un campo opzionale chiamato “content”, usato per vari scopi, dipendentemente dal tipo del messaggio.
 * Ha un metodo statico validate che converte l’user input in un Message.
 */
public class Message
{
    private Type type;
    private String from;
    private String to;
    private ArrayList<String> args;

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

    public static Message createMessage(String to, String content) {
        ArrayList<String> args  = new ArrayList<>();
        args.add(content);
        return new Message(
            Type.MESSAGE, 
            App.client.getUsername(), 
            to, 
            args);
    }

    public Type getType() {
        return type;
    }

    public String getFrom() {
        if (this.from == null ) return "";
        return from;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

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

    private static String humanizeMessage(Message m) {
        return "<" + m.from + " to " + m.to + "> "+m.args.get(0);
    }

    private static String humanizeServerAnn(Message m) {
        switch(ServerAnnouncement.valueOf(m.getArgs().get(0))) {
            case COMMAND_NOT_RECOGNIZED:
                return "The command that you wrote is not recognized.";

            case DEST_NOT_CORRECT:
                return "The addressee that you wrote is incorrect";

            case DISCONNECT:
                return "Disconnecting...";

            case JOINED:
                return "`" + m.getArgs().get(1) + "` joined.";

            case LEFT:
                return "`" + m.getArgs().get(1) + "` left.";

            case LIST:
                ArrayList<String> usernames = App.client.userMessagesList.getUsernames();
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
