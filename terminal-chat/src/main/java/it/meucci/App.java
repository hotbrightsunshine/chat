package it.meucci;

import it.meucci.textinterface.TextInterface;
import it.meucci.utils.Message;
import org.w3c.dom.Text;

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
    public static void main( String[] args ) throws IOException {
        messages = new HashMap<>();
        TextInterface.start();
    }

}
