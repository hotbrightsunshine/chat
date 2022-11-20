package it.fi.meucci;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import it.fi.meucci.utils.Username;

public class Server
{

    private ArrayList <RequestListener> listeners = new ArrayList<>();
    private static ServerSocket serverSocket;

    

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    /**
     * Accetta le connessioni in input.
     * Ha un ciclo che accetta continuamente nuove connessioni, creando per loro dei Thread. 
     */
    public void accept(){
        while(true){
            // Metodo accept del serversocket

            // Creazione del Thread e del RequestListener

            // Aggiungo il Request Listener alla lista di listener 

            // Faccio partire il Thread
        }
    }

    /**
     * 
     * @return Ritorna una lista di username, cioè i client connessi che hanno un username validato
     */
    public ArrayList<Username> getUsernames(){
        // Crea una lista temporanea vuota : ArrayList<Username>
        // Cicla tutta la lista dei connessi
        // Se il listener ha un username valido, viene aggiunto alla lista temporanea
        // Ritorna la lista temporanea
    }
}
