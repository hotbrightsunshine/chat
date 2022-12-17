package it.meucci.commands;

import it.meucci.App;
import it.meucci.utils.Errors;
import it.meucci.utils.Message;
import it.meucci.utils.Username;

import java.util.ArrayList;

public class CommandHandler {

    public static void handle(Command c) {
        switch(c.getType()) {
            case HELP:
                help();
                break;
            case DISCONNECT:
                disconnect();
                break;
            case WHO:
                who();
                break;
            case SEND:
                send(c.getArgs());
                break;
            case NICK:
                nick(c.getArgs());
                break;
            case ME:
                me();
                break;
            case INVALID:
                invalid();
                break;
            default:
                break;
        }
    }

    /*
     * Metodo per spostarsi nella pagina HELP
     */
    private static void help() {
        System.out.println("List of some useful commands :)");
        System.out.println("- /disconnect to disconnect");
        System.out.println("- /who print the list of users currently connected");
        System.out.println("- /nick <your name> to change your nickname");
        System.out.println("- /me to print your nickname");
        System.out.println("- @<username> to send a message to `username` you want");
        System.out.println("- @everyone to send a message to everybody");
    }

    private static void invalid() {
        System.out.println("The command you typed is not valid. ");
    }

    /**
     * Metodo per spostarsi nella pagina di disconnessione
     */
    private static void disconnect() {
        try {
            App.client.stop();
            App.stop();
        } catch (Exception e) {}
    }

    private static void who() {
        if(App.client == null || !App.client.getSocket().isConnected()) {
            System.out.println(Errors.NOT_CONNECTED_YET);
        } else {
            ArrayList<String> usernames_clone = (ArrayList<String>) App.client.userMessagesList.getUsernames().clone();
            usernames_clone.remove(Username.everyone);
            if (usernames_clone.size() == 0) {
                System.out.println("No one else is connected to the server.");
            } else {
                System.out.println("Users currently connected: ");
                for(String s : usernames_clone) {
                    System.out.println("- " + s);
                }
            }
        }
    }

    private static void me() {
        System.out.println("Your nickname is `" + App.client.getUsername() + "`.");
    }

    private static void nick(ArrayList<String> args) {
        try {
            if(args.size() == 1) {
                App.client.send(Message.createChangeNameCommand(App.client.getUsername(), args.get(0)));
                App.client.changeUsername(args.get(0));
            } else {
                System.out.println(Errors.humanizeError(Errors.WRONG_ARGS));
            }
        } catch (NullPointerException e) {
            System.out.println(Errors.NOT_CONNECTED_YET);
        }
    }

    public static void send(ArrayList<String> args) {
        String dest = args.get(0);
        args.remove(0);

        String content = "";
        for (String string : args) {
            content += string + " ";
        }
        Message msg = Message.createMessage(dest, content);
        App.client.send(msg);
        // System.out.println(Message.humanize(msg));
    }
  
}
