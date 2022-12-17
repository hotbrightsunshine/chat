package it.meucci;

import it.meucci.utils.Message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents client. Has some methods to write/send message from/to the Socket's input and output streams. 
 */
public class Client {
    private String username;
    private final Socket socket;
    private final DataOutputStream output;
    ObjectMapper objectmapper = new ObjectMapper();
    public UserMessagesList userMessagesList;

    public Client(Inet4Address address, int port) throws IOException {
        socket = new Socket(address, port);
        userMessagesList = new UserMessagesList();
        output = new DataOutputStream(socket.getOutputStream());
    }

    public void initListener() {
        new Thread(new ReplyListener()).start();
    }

    public void send(Message message) {
        try {
            output.writeBytes(objectmapper.writeValueAsString(message) + '\n');
        } catch (Throwable e) {
            System.out.println("An error occurred while sending a message.");
        }
    }


    public void changeUsername(String newUsername) {
        this.username = newUsername;
    }

    public void stop() throws IOException {
        socket.close();
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        if(username == null) {
            return "";
        }
        return username;
    }
}
