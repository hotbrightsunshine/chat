package it.fi.meucci;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    // PORTA
    public static final int PORT = 7777;
    public static Server server;

    public static void main( String[] args ) throws IOException {
        // Crea un server
        // Lo avvia
        App.server = new Server(PORT);
        server.accept();
    }
}
