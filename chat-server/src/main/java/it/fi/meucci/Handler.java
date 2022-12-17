package it.fi.meucci;

import it.fi.meucci.exceptions.CommandNotRecognizedException;
import it.fi.meucci.exceptions.DestNotCorrectException;
import it.fi.meucci.exceptions.DisconnectException;
import it.fi.meucci.exceptions.HandlerException;
import it.fi.meucci.exceptions.NeedNameException;
import it.fi.meucci.utils.CommandType;
import it.fi.meucci.utils.Message;
import it.fi.meucci.utils.Username;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class contains all the methods needed to send and to manage messages.
 * They are called by Request Listener class and can't be instantiated.
 */
public abstract class Handler {

    /**
     * Handles a Message
     * @param msg The message to be handled
     * @throws NeedNameException When the message does not have a valid sender
     * @throws DestNotCorrectException When the message does not have a valid receiver
     * @throws IOException When it's unable to send the message on the socket's output stream
     */
    public static void handleMessage(Message msg)
            throws NeedNameException,
            DestNotCorrectException, IOException {

        String from = msg.getFrom();
        String to = msg.getTo();

        if (!App.server.isUserValid(from)) {
            throw new NeedNameException();
        } else if (!App.server.isUserValid(to)) {
            throw new DestNotCorrectException();
        }

        String newarg = "";
        for(String s : msg.getArgs()) {
            newarg += s + " ";
        }

        ArrayList<String> t = new ArrayList<>();
        t.add(newarg);
        msg.setArgs(t);

        if(msg.getTo().equals(Username.everyone))
            App.server.sendBroadcast(msg);
        else 
            App.server.send(msg);
    }

    /**
     * Handles a Command
     * @param msg The command to be handled
     * @throws NeedNameException When the name does not have a valid sender
     * @throws DestNotCorrectException When the name does not have a valid addressee
     * @throws DisconnectException When the user asked to disconnect
     * @throws CommandNotRecognizedException When the command sent by the user is not valid
     */
    public static void handleCommand(Message msg) 
    throws  NeedNameException, 
            DestNotCorrectException, 
            DisconnectException, 
            CommandNotRecognizedException {

        String to = msg.getTo();
        if(!to.equals(Username.server)) {
            throw new DestNotCorrectException();
        }

        if(msg.getArgs().get(0).equals(CommandType.DISCONNECT.toString())) {
            throw new DisconnectException();
        } else {
            throw new CommandNotRecognizedException();
        }
    }

    public static void handle(Message msg) throws HandlerException {
        throw new CommandNotRecognizedException();
    }
}
