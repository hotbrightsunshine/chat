package it.fi.meucci;

import java.io.IOException;

/**
 * The main class used to launch the entire application.
 */
public class App 
{
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
        try {
            int port = Integer.parseInt(args[0]);
            App.server = new Server(port);
        } catch (Exception e) {
            System.out.println("It was impossible to start the server." +
                    "Please consider adding a port number to the command line arguments.");
            return;
        }
        server.accept();
    }
}
