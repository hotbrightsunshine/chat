package it.meucci.textinterface;

import it.meucci.App;
import it.meucci.Client;
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

    private static void connect(ArrayList<String> args){
        if(args.size() != 1){
            TextInterface.setError(Errors.WRONG_ARGS);
            return;
        } else {
            if(App.client.getSocket().isConnected()){
                TextInterface.setError(Errors.ALREADY_CONNECTED);
            }
            try{
                Inet4Address addr = (Inet4Address) Inet4Address.getByName(args.get(0));
                App.client = new Client(addr, 7777);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static void help(){
        TextInterface.switchTo(new HelpPage());
    }
    private static void next(){
        TextInterface.nextScreen();
    }
    private static void prev(){
        TextInterface.previousScreen();
    }
    private static void welcome(){
        TextInterface.switchTo(new WelcomePage());
    }
    private static void disconnect(){
        TextInterface.switchTo(new DisconnectPage());
    }
}