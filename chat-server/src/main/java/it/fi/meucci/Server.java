package it.fi.meucci;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import it.fi.meucci.utils.Message;
import it.fi.meucci.utils.Username;

public class Server
{

    private ArrayList <RequestListener> listeners = new ArrayList<>();
    private ArrayList <Thread> threads = new ArrayList<>();
    private static ServerSocket serverSocket;

    

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    /**
     * Accetta le connessioni in input.
     * Ha un ciclo che accetta continuamente nuove connessioni, creando per loro dei Thread. 
     */
    public void accept() throws IOException {
        while(true){
            // Metodo accept del serversocket
            Socket s = serverSocket.accept();

            // Creazione del Thread e del RequestListener
            RequestListener r = new RequestListener(s);
            Thread t = new Thread(r);

            // Aggiungo il Request Listener alla lista di listener 
            listeners.add(r);
            threads.add(t);

            // Faccio partire il Thread
            t.start();
        }
    }

    /**
     * 
     * @return Ritorna una lista di username, cioè i client connessi che hanno un username validato
     */
    public ArrayList<Username> getUsernames(){
        ArrayList<Username> temp = new ArrayList<>();
        for (RequestListener r:
             listeners) {
            if (r.getUsername() != null) {
                temp.add(r.getUsername());
            }
        }
        return temp;
    }

    public void send(Message msg){
        
    }

    public boolean isUserValid(Username username){
        if(username.getUsername().equals("")){
            return false;
        }
        return getUsernames().contains(username);
    }

    public boolean isUserAvailable(Username username){
        return !isUserValid(username);
    }
}
