package it.fi.meucci;

import it.fi.meucci.utils.Message;
import it.fi.meucci.utils.Username;

import java.net.Inet4Address;
import java.net.Socket;

/**
 * Rappresenta il Client.
 */
public class Client {
    private Username username;
    private Socket socket;
    private ReplyListener listener;

    public Socket connect(Inet4Address address, int port){
        return null;
    }

    public void send(Message message){

    }

    public void start(){

    }

    public void stop(){

    }
}
