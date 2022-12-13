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
    public static int port;
    public static Inet4Address addr;
    public static boolean allowed = true;
    public static Scanner scanner;

    public static void main( String[] args ) throws IOException {
        try {
            String ip = args[0];
            String port = args[1];
            
            App.addr = (Inet4Address) Inet4Address.getByName(ip);
            App.port = Integer.parseInt(port);
        } catch (Exception e){
            System.out.println("I parametri non sono corretti. Lanciare il programma con ./_.jar <addr> <port>");
        }

        client = new Client(addr, port);
        scanner = new Scanner(System.in);
        
        do {
            String read = scanner.nextLine();
            Command cmd = Command.validate(read);
            CommandHandler.handle(cmd);
        } while(allowed);
    }

    public static void print(Object o){
        System.out.println(o.toString());
    }
}
