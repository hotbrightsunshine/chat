package it.fi.meucci;

import java.io.IOException;

/**
 * The main class used to launch the entire application.
 */
public class App 
{
    /**
     * Port on which the server will be listening and writing
     */
    public static final int PORT = 7777;
    /**
     * The server object
     */
    public static Server server;

    /**
     * Starts the server
     * @param args command line arguments
     * @throws IOException The server is unable to be started
     */
    public static void main( String[] args ) throws IOException {
        App.server = new Server(PORT);
        server.accept();
    }
}
