package it.fi.meucci;

import com.fasterxml.jackson.databind.ObjectMapper;

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
 * Questa classe contiene tutti i metodi necessari per l'inoltro e la gestione dei messaggi.
 * Sono chiamati dai Request Listener. NON può essere istanziata.
 */
public abstract class Handler {
    public static ObjectMapper om = new ObjectMapper();
    /**
     * Gestisce un messaggio, lo valida logicamente (se gli username sono corretti) e lo invia se è corretto.
     * @param msg Il messaggio da gestire
     * @throws HandlerException Può mandare una eccezione nel caso il messaggio non sia validabile
     */
    public static void handleMessage(Message msg)
            throws NeedNameException,
            DestNotCorrectException, IOException {
        /*
         * Se mittente e destinatario sono validi:
         *      estrae il destinatario. 
         *      App.server.send(msg)

         * Se il destinatario non è valido:
         *      Manda una eccezione ServerAnnouncement.DEST_NOT_CORRECT
         */
        /*
         * Se il mittente non è negli username:
         *      Manda una eccezione ServerAnnouncement.NEED_NAME
         */
        String from = msg.getFrom();
        String to = msg.getTo();

        // Se il mittente ha un username non valido oppure è vuoto:
        if (!App.server.isUserValid(from)){
            throw new NeedNameException();
        } else if (!App.server.isUserValid(to)){
            throw new DestNotCorrectException();
        }

        // Confermo che ci sia un solo argomento per il messaggio
        String newarg = "";
        for(String s : msg.getArgs()){
            newarg += s + " ";
        }

        // Modifico l'argomento del messaggio
        ArrayList<String> t = new ArrayList<>();
        t.add(newarg);
        msg.setArgs(t);

        // Mando il messaggio
        if(msg.getTo().equals(Username.everyone))
            App.server.sendBroadcast(msg);
        else 
            App.server.send(msg);
    }

    /**
     * Gestisce un comando, lo valida logicamente (se il mittente e gli argomenti sono corretti) e lo invia se è corretto.
     * @param msg il comando da elaborare
     * @throws HandlerException scatena una eccezione nel caso in cui il messaggio non sia validabile
     */
    public static void handleCommand(Message msg) 
    throws  NeedNameException, 
            DestNotCorrectException, 
            DisconnectException, 
            CommandNotRecognizedException {

        String to = msg.getTo();
        if(!to.equals(Username.server)){
            throw new DestNotCorrectException();
        }

        if(msg.getArgs().get(0).equals(CommandType.DISCONNECT.toString())){
            throw new DisconnectException();
        } else {
            throw new CommandNotRecognizedException();
        }
    }

    public static void handle(Message msg) throws HandlerException {
        throw new CommandNotRecognizedException();
    }
}
