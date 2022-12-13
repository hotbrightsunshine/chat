package it.meucci;

import it.meucci.textinterface.TextInterface;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static Client client;

    public static void main( String[] args ) throws IOException {
        System.out.println(args);
        //client = new Client(null, 7777);
    }

    public static void welcome(){
        System.out.println("");
    }
}
