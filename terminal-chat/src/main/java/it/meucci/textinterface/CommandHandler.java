package it.meucci.textinterface;

import it.meucci.App;
import it.meucci.Client;
import org.w3c.dom.Text;

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
                    //TextInterface.switchTo(TextInterface.);
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
        TextInterface.switchTo(new DisconnectPage());
    }
}
