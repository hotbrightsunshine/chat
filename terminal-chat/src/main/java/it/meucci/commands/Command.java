package it.meucci.commands;

import java.util.ArrayList;

public class Command {

    CommandType type;
    ArrayList<String> args;

    private Command(CommandType t, ArrayList<String> args) {
        type = t;
        this.args = args;
    }

    /**
     * Metodo adibito alla convalida di un comando
     * @param command
     * @return Comando validato
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
