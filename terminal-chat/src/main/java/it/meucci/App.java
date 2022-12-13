package it.meucci;

import java.io.IOException;
import java.net.Inet4Address;

/**
 * Hello world!
 *
 */
public class App 
{
    public static Client client;
    public static int port;
    public static Inet4Address addr;

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
    }

    public static void print(Object o){
        System.out.println(o.toString());
    }
}
