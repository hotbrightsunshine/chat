package it.meucci.textinterface.commands;

import it.meucci.textinterface.Errors;
import it.meucci.textinterface.TextInterface;

import java.util.ArrayList;

public class Command {
    CommandType type;
    ArrayList<String> args;

    private Command(CommandType t, ArrayList<String> args){
        type = t;
        this.args = args;
    }

    /**
     * Metodo adibito alla convalida di un comando
     * @param command
     * @return Comando validato
     */
    public static Command validate(String command){
        command = command.replace("/", "");
        String split[] = command.split(" ");
        ArrayList<String> arraylist = new ArrayList<>();
        if (split.length > 1){
            for(int i = 1; i < split.length; ++i){
                arraylist.add(split[i]);
            }
        }
        try{
            return new Command(CommandType.valueOf(CommandType.class, split[0].toUpperCase()), arraylist);
        } catch (Exception e){
            TextInterface.setError(Errors.COMMAND_NOT_RECOGNIZED);
            return new Command(CommandType.INVALID, arraylist);
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
