package it.meucci.textinterface;

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
            default:
                break;

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
