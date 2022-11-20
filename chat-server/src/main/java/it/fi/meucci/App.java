package it.fi.meucci;

import java.net.Inet4Address;

/**
 * Hello world!
 *
 */
public class App 
{
    // PORTA
    public static final int PORT = 7777;

    /**
     * Fa partire il server
     * @param args Command Line Arguments
     */
    public static void main( String[] args )
    {
        // Crea un server
        // Lo avvia
        Server s = new Server(PORT);
    }
}
