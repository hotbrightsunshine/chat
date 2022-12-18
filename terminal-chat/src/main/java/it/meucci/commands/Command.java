package it.meucci.commands;

import java.util.ArrayList;

/**
 * This class represents the command a user can type from their keyboard. It has a type and a list of arguments.
 * Like messages, commands must be handled properly in order to show the right output and/or to do the proper action.
 * These commands are NOT the ones used in server -> client communication.
 * These are boilerplate code used to distinguish USER commands in an easier manner.
 */
public class Command {

    CommandType type;
    ArrayList<String> args;

    /**
     * Command constructor. It is private because the developer must not have access to it. The command must be
     * valided with a proper method called validate().
     * @param t The Command {@link it.meucci.commands.CommandType}
     * @param args the arguments
     */
    private Command(CommandType t, ArrayList<String> args) {
        type = t;
        this.args = args;
    }

    /**
     * Creates a command object from a String (usually, the user input).
     * This is also a wrapper method to avoid code duplication.
     * It calls a message validation  method or a command one, depending on the type of string the user has input.
     * It does not return null. Instead, it will provide a message of type INVALID.
     *
     * A message, following syntax `@username content` is automatically resolved into a command of type SEND.
     * Cfr. {@link it.meucci.commands.CommandType}.
     * @param command The string input by the user
     * @return A validated command.
     */
    public static Command validate(String command) {
        if(command.length() == 0) {   
            return new Command(CommandType.INVALID, null);

        } else if(command.startsWith("/send")) {
            System.out.println("Command /send <username> <content...> is currently not supported. Use @<username> <content...> instead.");
            return new Command(CommandType.INVALID, null);

        } else if(command.charAt(0) == '/') {
            return validateCommand(command);

        } else if (command.charAt(0) == '@') {
            return validateMessage(command);

        } else {
            return new Command(CommandType.INVALID, null);
        }
    }

    /**
     * The Command validation method. It resolves a Command from a String.
     * @param command The string input by the user
     * @return A validated command
     */
    private static Command validateCommand(String command) {
        command = command.replace("/", "");
        String[] split = command.split(" ");
        ArrayList<String> arraylist = new ArrayList<>();

        if (split.length > 1) {
            for(int i = 1; i < split.length; ++i) {
                arraylist.add(split[i]);
            }
        }
        
        try{
            return new Command(CommandType.valueOf(CommandType.class, split[0].toUpperCase()), arraylist);
        } catch (Exception e) {
            System.out.println(Errors.COMMAND_NOT_RECOGNIZED);
            return new Command(CommandType.INVALID, null);
        }
    }

    /**
     * Validates a Command of type SEND. Cfr. {@link it.meucci.commands.CommandType}.
     * @param command
     * @return
     */
    private static Command validateMessage(String command) {
        command = command.replace("@", "");
        String[] words = command.split(" ", 2);
        ArrayList<String> args = new ArrayList<>();

        try{
            args.add(words[0]);
            args.add(words[1]);   
            return new Command(CommandType.SEND, args);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Errors.WRONG_ARGS);
            return new Command(CommandType.INVALID, null);

        } catch (Exception e) {
            System.out.println(Errors.COMMAND_NOT_RECOGNIZED);
            return new Command(CommandType.INVALID, null);
        }
    }

    @Override
    public String toString() {
        return "Command{" +
                "type=" + type +
                ", args=" + args +
                '}';
    }

    public CommandType getType() {
        return type;
    }

    public ArrayList<String> getArgs() {
        return args;
    }
}
