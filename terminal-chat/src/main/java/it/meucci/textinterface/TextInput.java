package it.meucci.textinterface;

import it.meucci.textinterface.commands.Command;
import it.meucci.textinterface.commands.CommandHandler;

import java.util.Scanner;

public class TextInput {
    private static  Scanner scanner;

    private static boolean canRead;
    private static String acquired;
    private static Runnable onRead = new Runnable() {
        @Override
        public void run() {
            CommandHandler.handle(Command.validate(acquired));
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
        //scanner.close();
    }
}
