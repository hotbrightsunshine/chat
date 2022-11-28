package it.fi.meucci;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.fi.meucci.exceptions.HandlerException;
import it.fi.meucci.utils.CommandType;
import it.fi.meucci.utils.Message;
import it.fi.meucci.utils.ServerAnnouncement;
import it.fi.meucci.utils.Username;

import java.io.DataOutputStream;
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
    public static void handleMessage(Message msg) throws HandlerException {
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
        Username from = msg.getFrom();
        Username to = msg.getTo();
        // Se il mittente ha un username non valido oppure è vuoto:
        if (!App.server.isUserValid(from)){
            throw new HandlerException(ServerAnnouncement.NEED_NAME);
        } else if (!App.server.isUserValid(to)){
            throw new HandlerException(ServerAnnouncement.DEST_NOT_CORRECT);
        }

        // Confermo che ci sia un solo argomento per il messaggio
        String newarg = "";
        for(String s : msg.getArgs()){
            newarg += s + " ";
        }
        // Modifico l'argomento del messaggio
        msg.setArgs(new String[]{newarg});
        // Mando il messaggio
        App.server.send(msg);
    }

    /**
     * Gestisce un comando, lo valida logicamente (se il mittente e gli argomenti sono corretti) e lo invia se è corretto.
     * @param msg il comando da elaborare
     * @throws HandlerException scatena una eccezione nel caso in cui il messaggio non sia validabile
     */
    public static void handleCommand(Message msg) throws HandlerException {
        /*
         * Se il mittente è valido:
         *      Allora va bene. 
         *      GOTO CONTROLLO Argomenti
         * Se non è valido:
         *      Male, manda una eccezione.
         *
         * CONTROLLO argomenti
         * // Ogni comando ha argomenti diversi. Comandi come /stop non li hanno, ma comandi come
         * // /nick si, e ne hanno uno.
         * Bisogna fare uno switch sul tipo di comando, che viene estratto dal primissimo parametro!
         * I comandi possono essere:
         * CommandType.CHANGE_NAME
         * CommandType.DISCONNECT
         * Se non sono questi
         */
        Username from = msg.getFrom();
        Username to = msg.getTo();
        if(!App.server.isUserValid(from)){
            throw new HandlerException(ServerAnnouncement.NEED_NAME);
        } else if(!App.server.isUserValid(to)){
            throw new HandlerException(ServerAnnouncement.DEST_NOT_CORRECT);
        }

        if(msg.getArgs()[0].equals(CommandType.DISCONNECT.toString())){
            throw new HandlerException(ServerAnnouncement.DISCONNECT);
        } else {
            throw new HandlerException(ServerAnnouncement.COMMAND_NOT_RECOGNIZED);
        }

    }

    public static void handle(Message msg) throws HandlerException {
        throw new HandlerException(ServerAnnouncement.COMMAND_NOT_RECOGNIZED);
    }
}
