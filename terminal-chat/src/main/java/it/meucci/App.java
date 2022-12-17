package it.meucci;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.Scanner;

import it.meucci.commands.Command;
import it.meucci.commands.CommandHandler;

/**
 * Hello world!
 *
 */
public class App 
{
    public static Client client;
    private static int port;
    private static Inet4Address addr;
    private static boolean allowed = true;
    private static Scanner scanner;

    public static void main( String[] args ) throws IOException {
        try {
            String ip = args[0];
            String port = args[1];
            
            App.addr = (Inet4Address) Inet4Address.getByName(ip);
            App.port = Integer.parseInt(port);
        } catch (Exception e) {
            System.out.println("The parameters are incorrect. Launch the program with ./_.jar <addr> <port>");
            return;
        }

        client = new Client(addr, port);
        client.initListener();
        scanner = new Scanner(System.in);

        System.out.println("Successfully connected. Type /help for a list of commands.");

        do {
            String read = scanner.nextLine();
            Command cmd = Command.validate(read);
            CommandHandler.handle(cmd);
        } while(allowed);

        System.out.println("You have been successfully disconnected.");
    }

    public static void stop() {
        allowed = false;
    }
}
