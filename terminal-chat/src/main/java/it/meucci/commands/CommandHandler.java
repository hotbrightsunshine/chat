package it.meucci.commands;

import it.meucci.App;
import it.meucci.utils.Errors;
import it.meucci.utils.Message;



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
            case ME:
                me();
                break;
            default:
                break;

        }
    }

    /*
     * Metodo per spostarsi nella pagina HELP
     */
    public static void help(){
        System.out.println("Here are some useful commands :)");
        System.out.println("- /disconnect  for disconnecting");
        System.out.println("- /who  print the list of users connected");
        System.out.println("- /nick <your name> for change your nickname");
        System.out.println("- /me print your nickname");
        System.out.println("- @<username> for send message to who you want");
        System.out.println("- @everyone to send your message to everybody that are connected");
    }


    /**
     * Metodo per spostarsi nella pagina di disconnessione
     */
    private static void disconnect(){
        try {
            App.client.stop();
            App.stop();
        } catch (Exception e){}
    }

    private static void who(){
        if(App.client == null || !App.client.getSocket().isConnected()){
            System.out.println(Errors.NOT_CONNECTED_YET);
        } else {
            System.out.println("Here is the list of users:");
            System.out.println(App.client.userMessagesList.getUsernames());
        }
    }
    private static void me()
    {
        System.out.println("Your nickname is: " + App.client.getUsername());
    }

    private static void nick(ArrayList<String> args){
        try {
            if(args.size() == 1) {
                App.client.send(Message.createChangeNameCommand(App.client.getUsername(), args.get(0)));
                App.client.changeUsername(args.get(0));
            } else {
                System.out.println(Errors.WRONG_ARGS);
            }
        } catch (NullPointerException e){
            System.out.println(Errors.NOT_CONNECTED_YET);
        }
    }

    public static void send(ArrayList<String> args){
        String dest = args.get(0);
        args.remove(0);

        String content = "";
        for (String string : args) {
            content += string + " ";
        }
        App.client.send(Message.createMessage(dest, content));
    }
}
