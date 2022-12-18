package it.meucci;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.Scanner;

import it.meucci.commands.Command;
import it.meucci.commands.CommandHandler;

/**
 * The main class that runs the client process.
 */
public class App 
{
    /**
     * The Client object
     */
    public static Client client;
    /**
     * The port of the server's ServerSocket
     */
    private static int port;
    /**
     * The IP address to which the client has to connect
     */
    private static Inet4Address addr;
    /**
     * A flag that stand whether the client should continue to run or not.
     */
    private static boolean allowed = true;

    /**
     * The main method that runs client's methods
     * @param args Command line arguments. It must be run with ./_.jar IP PORT
     * @throws IOException
     */

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

        try {
            client = new Client(addr, port);
            client.initListener();
        } catch (Exception e) {
            System.out.println("Connection to " + addr + " was refused. ");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Successfully connected. Type /help for a list of commands.");

        do {
            String read = scanner.nextLine();
            Command cmd = Command.validate(read);
            CommandHandler.handle(cmd);
        } while(allowed);

        System.out.println("You have been successfully disconnected.");
    }

    /**
     * Used to stop the client
     */
    public static void stop() {
        allowed = false;
    }
}
