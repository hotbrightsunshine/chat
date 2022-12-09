package it.meucci;

import it.meucci.lantern.GraphicalInterface;
import it.meucci.utils.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static Client client;
    public static HashMap<String, ArrayList<Message>> messages;
    public static void main( String[] args ) throws IOException, InterruptedException {
        messages = new HashMap<>();
        GraphicalInterface.start();

    }

}
