package it.meucci.textinterface.commands;

import it.meucci.App;
import it.meucci.Client;
import it.meucci.textinterface.*;
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
            if (App.client == null){
                try{
                    Inet4Address addr = (Inet4Address) Inet4Address.getByName(args.get(0));
                    App.client = new Client(addr, 7777);
                    App.client.init();
                    while(!App.client.isReadyForTextInterface()){
                        // timer di timeout
                    }
                    TextInterface.mainpage = new MainMenu();
                    TextInterface.switchTo(TextInterface.mainpage);
                } catch (IOException e) {
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
            if(App.client.getSocket().isConnected()) {
                App.client.send(Message.createChangeNameCommand(App.client.getUsername(), args.get(0)));
            }
        } catch (IndexOutOfBoundsException e){
            TextInterface.setError(Errors.WRONG_ARGS);
        } catch (NullPointerException e){
            TextInterface.setError(Errors.NOT_CONNECTED_YET);
        }
    }
}
