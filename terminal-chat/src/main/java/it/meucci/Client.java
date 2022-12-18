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
    /**
     * The UserMessagesList object used to handle messages and users
     */
    public UserMessagesList userMessagesList;
    private String pendingUsername;

    /**
     * The client costructor.
     * @param address The IP address of the ServerSocket
     * @param port The port on which the ServerSocket listens to
     * @throws IOException When socket or DataOutputStream can't be created successfully.
     */
    public Client(Inet4Address address, int port) throws IOException {
        socket = new Socket(address, port);
        userMessagesList = new UserMessagesList();
        output = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * Initializes the ReplyListener and starts it.
     * Must be called once and after the successful creation of the client.
     */
    public void initListener() {
        new Thread(new ReplyListener()).start();
    }

    /**
     * Sends a message to the socket's ouput stream
     * @param message The message to be serialized and sent
     */
    public void send(Message message) {
        try {
            output.writeBytes(objectmapper.writeValueAsString(message) + '\n');
        } catch (Throwable e) {
            System.out.println("An error occurred while sending a message.");
        }
    }

    /**
     * Used to change user's username from the pending one.
     */
    public void changeUsername() {
        this.username = pendingUsername;
    }

    /**
     * Stops the client's socket
     * @throws IOException inherited from socket.close() -- thrown when it's impossible to stop the client.
     */
    public void stop() throws IOException {
        socket.close();
    }

    public Socket getSocket() {
        return socket;
    }

    /**
     * Gets the current username. If it's null, `""` is automatically returned.
     * @return
     */
    public String getUsername() {
        if(username == null) {
            return "";
        }
        return username;
    }

    public void setPendingUsername(String username){
        this.pendingUsername = username;
    }
}
