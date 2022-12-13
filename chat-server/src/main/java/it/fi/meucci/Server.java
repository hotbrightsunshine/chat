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
            System.out.println("In attesa di una nuova connessione...");
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
    public ArrayList<String> getUsernames(){

        ArrayList<String> temp = new ArrayList<>();
        for (RequestListener r:
             listeners) {
            if (r.getUsername() != null || !r.getUsername().equals("")) {
                temp.add(r.getUsername());
            }
        }
        return temp;
    }

    public void send(Message msg) throws IOException {
        for(RequestListener r : listeners){
            if (r.getUsername().equals(msg.getTo())){
                r.write(msg);
            }
        }
    }

    public void broadcast(Message msg) throws IOException {
        for(RequestListener r : listeners){
            if(r.getUsername().equals(msg.getFrom())){
                continue;
            } else {
                r.write(msg);
            }
        }
    }

    public ArrayList<RequestListener> getListeners(){
        return listeners;
    }

    public boolean isUserValid(String username){
        if(username == null || username.equals("")){
            return false;
        } else if(username.equals(Username.everyone)){
            return true;
        }
        return getUsernames().contains(username);
    }

    public boolean isUserAvailable(String username){
        return !isUserValid(username);
    }
}
