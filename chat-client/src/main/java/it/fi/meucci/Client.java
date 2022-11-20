package it.fi.meucci;

import it.fi.meucci.utils.Message;
import it.fi.meucci.utils.Username;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;

/**
 * Rappresenta il Client.
 */
public class Client {
    private Username username;
    private Socket socket;
    private ReplyListener listener;

    public Client(Inet4Address address, int port) throws IOException {
        socket = new Socket(address, port);
    }

    public void send(Message message){

    }

    public void stop() throws IOException {
        socket.close();
        listener.close();
    }
}
