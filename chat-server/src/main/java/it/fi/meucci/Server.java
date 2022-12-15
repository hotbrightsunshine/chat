package it.fi.meucci;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import it.fi.meucci.logger.Log;
import it.fi.meucci.logger.LogType;
import it.fi.meucci.utils.Message;
import it.fi.meucci.utils.Username;

public class Server
{

    private ArrayList <RequestListener> listeners = new ArrayList<>();
    private ArrayList <Thread> threads = new ArrayList<>();
    private static ServerSocket serverSocket;

    

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        Log.print(LogType.INFO, "Una nuova istanza del server è stata creata");
    }

    /**
     * Accetta le connessioni in input.
     * Ha un ciclo che accetta continuamente nuove connessioni, creando per loro dei Thread. 
     */
    public void accept() throws IOException {
        while(true){
            Log.print(LogType.INFO, "In attesa di una nuova connessione");
            Log.print(LogType.INFO, "Usernames: " + this.getUsernames());
            Socket s = serverSocket.accept();

            RequestListener r = new RequestListener(s);
            Thread t = new Thread(r);

            listeners.add(r);
            threads.add(t);

            t.start();
        }
    }

    /**
     * 
     * @return Ritorna una lista di username, cioè i client connessi che hanno un username validato
     */
    public ArrayList<String> getUsernames(){

        ArrayList<String> temp = new ArrayList<>();
        for (RequestListener r: listeners) {
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

    public void sendBroadcast(Message msg) throws IOException {
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
