package it.meucci.textinterface;

import org.w3c.dom.Text;

import java.util.Scanner;

public class TextInput {
    private static  Scanner scanner;

    private static boolean canRead;
    private static String acquired;
    private static Runnable onRead = new Runnable() {
        @Override
        public void run() {
            if(acquired.equals("/disconnect")){
                disableInput();
                TextInterface.switchTo(new DisconnectPage());
            } else if (acquired.equals("/help")) {
                disableInput();
                TextInterface.switchTo(new HelpPage());
            } else if (acquired.equals("/next")) {
                disableInput();
                TextInterface.nextScreen();
            } else if (acquired.equals("/prev")) {
                disableInput();
                TextInterface.previousScreen();
            }
        }
    };

    public static void inputCycle(){
        enableInput();
        while(canRead){
            acquired = scanner.nextLine();
            onRead.run();
        }
        disableInput();
    }

    private static void enableInput(){
        canRead = true;
        scanner = new Scanner(System.in);
    }

    private static void disableInput(){
        canRead = false;
        //scanner.close();
    }

    public static void stop(){
        canRead = false;
    }
}
