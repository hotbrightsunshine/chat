package it.meucci.textinterface.commands;

import it.meucci.App;
import it.meucci.Client;
import it.meucci.textinterface.*;
import it.meucci.textinterface.pages.ChatPage;
import it.meucci.textinterface.pages.DisconnectPage;
import it.meucci.textinterface.pages.HelpPage;
import it.meucci.textinterface.pages.MainMenu;
import it.meucci.textinterface.pages.WelcomePage;
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
            case NEXT:
                next();
                break;
            case PREV:
                prev();
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
            case CHATS:
                chats();
                break;
            case CHAT:
                chat(c.getArgs());
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
            TextInterface.setError(Errors.WRONG_ARGS);
            return;
        } else {
            if (App.client == null || App.client.getSocket() == null){
                try{
                    Inet4Address addr = (Inet4Address) Inet4Address.getByName(args.get(0));
                    App.client = new Client(addr, 7777);
                    App.client.init();
                    while(!App.client.isReadyForTextInterface()){
                        //Thread.sleep(100);
                        // timer di timeout
                    }
                    //System.out.println("dopo while");
                    TextInterface.mainpage = new MainMenu();
                    //System.out.println("dopo mainpage");
                    TextInterface.switchTo(TextInterface.mainpage);
                    //System.out.println("dopo switchTo");
                } catch (Throwable e) {
                    TextInterface.setError(Errors.UNABLE_TO_CONNECT);
                }
            }
            else if(App.client.getSocket().isConnected()){
                TextInterface.setError(Errors.ALREADY_CONNECTED);
            }

        }

    }

    /*
     * Metodo per spostarsi nella pagina HELP
     */
    private static void help(){
        TextInterface.switchTo(new HelpPage());
    }
     /*
     * Metodo per spostarsi nella pagina successiva
     */
    private static void next(){
        TextInterface.nextScreen();
    }
     /*
     * Metodo per spostarsi nella pagina precedente
     */
    private static void prev(){
        TextInterface.previousScreen();
    }
     /*
     * Metodo per spostarsi nella pagina di benvenuto
     */
    private static void welcome(){
        TextInterface.switchTo(new WelcomePage());
    }
    /**
     * Metodo per spostarsi nella pagina di disconnessione
     */
    private static void disconnect(){
        try {
            App.client.stop();
        } catch (Exception e){}
        TextInterface.switchTo(new DisconnectPage());
    }

    private static void chats(){
        if(App.client == null || !App.client.getSocket().isConnected()){
            TextInterface.setError(Errors.NOT_CONNECTED_YET);
        } else {
            TextInterface.switchTo(TextInterface.mainpage);
            TextInterface.refresh();
        }
    }

    private static void nick(ArrayList<String> args){
        try {
            if(args.size() == 1) {
                // TODO controllo subito se l'username che l'utente vuole mettere è server o everyone e lo fermo, impendendo di sprecare banda
                App.client.send(Message.createChangeNameCommand(App.client.getUsername(), args.get(0)));
                App.client.setPendingUsername(args.get(0));
                // Quando il pending username torna a "" allora è finito lo scambio e si può controllare.
                while(!App.client.getPendingUsername().equals("")){

                }
                TextInterface.refresh();
            } else {
                TextInterface.setError(Errors.WRONG_ARGS);
            }
        } catch (NullPointerException e){
            TextInterface.setError(Errors.NOT_CONNECTED_YET);
        }
    }

    public static void chat(ArrayList<String> args){
        String username = null;
        if(args.size() != 0){
            username = args.get(0);
        }
        
        if(App.client.userMessagesList.getUsernames().contains(username)){
            TextInterface.switchTo(new ChatPage(username));
        } else {
            TextInterface.setError(Errors.DEST_NOT_CORRECT);
        }
    }

    public static void send(){
        // TODO aggiunge il messaggio alla lista quando viene inviato altrimenti non lo visualizzo
        
    }
}
