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
            case WELCOME:
                welcome();
                break;
            case DISCONNECT:
                disconnect();
                break;
            case CONNECT:
                connect(c.getArgs());
                break;
            case WHO:
                who();
                break;
            case SEND:
                send();
                break;
            case NICK:
                nick(c.getArgs());
                break;
            default:
                break;

        }
    }

    /**
     * Metodo adibito alla connessione del Client
     * @param args
     */
    private static void connect(ArrayList<String> args){
        if(args.size() != 1){
            App.print(Errors.WRONG_ARGS);
            return;
        } else {
            if (App.client == null || App.client.getSocket() == null){
                try{
                    Inet4Address addr = (Inet4Address) Inet4Address.getByName(args.get(0));
                    App.client = new Client(addr, 7777);
                    while(!App.client.isReadyForTextInterface()){
                        //Thread.sleep(100);
                        // timer di timeout
                    }
                    //System.out.println("dopo while");
                    App.print("connesso");
                } catch (Throwable e) {
                   App.print(Errors.UNABLE_TO_CONNECT);
                }
            }
            else if(App.client.getSocket().isConnected()){
                App.print(Errors.ALREADY_CONNECTED);
            }

        }

    }

    /*
     * Metodo per spostarsi nella pagina HELP
     */
    private static void help(){
        App.print("help");
    }

     /*
     * Metodo per spostarsi nella pagina di benvenuto
     */
    private static void welcome(){
        App.print("ciao");
    }
    /**
     * Metodo per spostarsi nella pagina di disconnessione
     */
    private static void disconnect(){
        try {
            App.client.stop();
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
                // TODO controllo subito se l'username che l'utente vuole mettere è server o everyone e lo fermo, impendendo di sprecare banda
                App.client.send(Message.createChangeNameCommand(App.client.getUsername(), args.get(0)));
            } else {
                App.print(Errors.WRONG_ARGS);
            }
        } catch (NullPointerException e){
            App.print(Errors.NOT_CONNECTED_YET);
        }
    }

    public static void send(){
        // TODO aggiunge il messaggio alla lista quando viene inviato altrimenti non lo visualizzo
        
    }
}
