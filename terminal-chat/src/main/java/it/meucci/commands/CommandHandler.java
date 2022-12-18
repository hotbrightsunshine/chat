package it.meucci.commands;

import it.meucci.App;
import it.meucci.utils.Message;
import it.meucci.utils.Username;

import java.util.ArrayList;

/**
 * CommandHandler describes how, in response to a user's input command, the program should behave.
 */
public class CommandHandler {
    /**
     * A function that calls the proper method for a command.
     * For instance, the HELP command calls a method help() that displays the help menu.
     * @param c
     */
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


    /**
     * Displays the help page with a list of commands.
     */
    private static void help() {
        System.out.println("List of supported commands: ");
        System.out.println("- /disconnect to disconnect");
        System.out.println("- /who print the list of users currently connected");
        System.out.println("- /nick <your name> to change your nickname");
        System.out.println("- /me to print your nickname");
        System.out.println("- @<username> to send a message to `username` you want");
        System.out.println("- @everyone to send a message to everybody");
    }

    /**
     * Displays an error message when the command is invalid.
     */
    private static void invalid() {
        System.out.println(Errors.humanizeError(Errors.COMMAND_NOT_RECOGNIZED));
    }

    /**
     * Disconnects the client and stops the app
     */
    private static void disconnect() {
        try {
            App.client.stop();
            App.stop();
        } catch (Exception e) {}
    }

    /**
     * Displays the list of user's username currently connected
     */
    private static void who() {
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

    /**
     * Displays the client's username
     */
    private static void me() {
        if(App.client.getUsername() == null || App.client.getUsername().equals("")) {
            System.out.println("You don't have an username set yet. Set it with /nick <username>.");
        } else 
            System.out.println("Your nickname is `" + App.client.getUsername() + "`.");
    }

    /**
     * Creates a ChangeName command (cfr. {@link it.meucci.commands.CommandType} and
     * set the pending username to the username the user wants.
     * @param args
     */
    private static void nick(ArrayList<String> args) {
        if(args.size() == 1) {
            App.client.send(Message.createChangeNameCommand(args.get(0)));
            App.client.setPendingUsername(args.get(0));
        } else {
            System.out.println(Errors.humanizeError(Errors.WRONG_ARGS));
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
