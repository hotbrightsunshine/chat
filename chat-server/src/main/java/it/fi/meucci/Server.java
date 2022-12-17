package it.fi.meucci;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import it.fi.meucci.logger.Log;
import it.fi.meucci.logger.LogType;
import it.fi.meucci.utils.Message;
import it.fi.meucci.utils.Username;

/**
 * This class represents the server and contains some static methods used by other classes.
 */
public class Server
{

    private final ArrayList <RequestListener> listeners = new ArrayList<>();
    private ArrayList <Thread> threads = new ArrayList<>();
    private static ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        Log.print(LogType.INFO, "Una nuova istanza del server è stata creata");
    }

    /**
     * Accepts incoming connections, giving them a dedicated thread.
     */
    public void accept() throws IOException {
        while(true) {
            Log.print(LogType.INFO, "In attesa di una nuova connessione");
            Log.print(LogType.INFO, "Connessi: " + this.getUsernames());
            Socket s = serverSocket.accept();

            RequestListener r = new RequestListener(s);
            Thread t = new Thread(r);

            listeners.add(r);
            threads.add(t);

            t.start();
        }
    }

    /**
     * Cycles through the threads and checks if they have a valid username.
     * @return a list of valid usernames the user can chat with
     */
    public ArrayList<String> getUsernames() {

        ArrayList<String> temp = new ArrayList<>();
        for (RequestListener r: listeners) {
            if (r.getUsername() != null || !r.getUsername().equals("")) {
                temp.add(r.getUsername());
            }
        }

        return temp;
    }

    /**
     * Used by other RequestListeners to send messages to one another.
     * It sends it also to the sender.
     * @param msg The message to be sent
     */
    public void send(Message msg) {
        for(RequestListener r : listeners) {
            if (r.getUsername().equals(msg.getTo())) {
                r.write(msg);
            } else if (r.getUsername().equals(msg.getFrom())) {
                r.write(msg);
            }
        }
    }

    /**
     * Used by other RequestListeners to send messages to everyone.
     * It sends it also to the sender.
     * @param msg The message to be sent
     */
    public void sendBroadcast(Message msg) throws IOException {
        for(RequestListener r : listeners) {
            r.write(msg);
        }
    }

    /**
     * @return returns a list of listeners
     */
    public ArrayList<RequestListener> getListeners() {
        return listeners;
    }

    /**
     * Checks whether a username is valid or not.
     * @param username the username to be checked
     * @return true or false whether a username is valid or not.
     */
    public boolean isUserValid(String username) {
        if(username == null || username.equals("")) {
            return false;
        } else if(username.equals(Username.everyone)) {
            return true;
        }
        return getUsernames().contains(username);
    }

    /**
     * Checks whether a username is available or not.
     * Basically an ArrayList.contains() wrapper for better readability
     * @param username the username to be checked
     * @return true or false whether a username is available or not.
     */
    public boolean isUserAvailable(String username) {
        return !isUserValid(username);
    }
}
