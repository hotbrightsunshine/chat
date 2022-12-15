package it.meucci.commands;

import it.meucci.App;
import it.meucci.Client;
import it.meucci.utils.Errors;
import it.meucci.utils.Message;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.ArrayList;

public class CommandHandler {

    public static void handle(Command c){
        switch(c.getType()){
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
            default:
                break;

        }
    }

    /*
     * Metodo per spostarsi nella pagina HELP
     */
    private static void help(){
        App.print("help");
    }

    /**
     * Metodo per spostarsi nella pagina di disconnessione
     */
    private static void disconnect(){
        try {
            App.client.stop();
            App.stop();
        } catch (Exception e){}
        App.print("disconnesso");
    }

    private static void who(){
        if(App.client == null || !App.client.getSocket().isConnected()){
            App.print(Errors.NOT_CONNECTED_YET);
        } else {
            App.print("lista utenti");
        }
    }

    private static void nick(ArrayList<String> args){
        try {
            if(args.size() == 1) {
                App.print("Va bene");
                App.client.send(Message.createChangeNameCommand(App.client.getUsername(), args.get(0)));
            } else {
                App.print(Errors.WRONG_ARGS);
            }
        } catch (NullPointerException e){
            App.print(Errors.NOT_CONNECTED_YET);
        }
    }

    public static void send(ArrayList<String> args){
        App.client.send(Message.createMessage(args.get(0), args.get(1)));
    }
}
