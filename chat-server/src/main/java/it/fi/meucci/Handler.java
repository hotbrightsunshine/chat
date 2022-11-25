package it.fi.meucci;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.fi.meucci.utils.Message;
import it.fi.meucci.utils.ServerAnnouncement;

import java.io.DataOutputStream;
import java.util.ArrayList;

/**
 * Questa classe contiene tutti i metodi necessari per l'inoltro e la gestione dei messaggi.
 * Sono chiamati dai Request Listener
 */
public class Handler {
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
         * Se il mittente non è negli username:
         *      Manda una eccezione ServerAnnouncement.NEED_NAME
         * Se il destinatario non è valido:
         *      Manda una eccezione ServerAnnouncement.DEST_NOT_CORRECT
         */

        throw new HandlerException(ServerAnnouncement.DEST_NOT_CORRECT);
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
         * // /nick si, e ne hanno 1.
         * Bisogna fare uno switch sul tipo di comando, che viene estratto dal primissimo parametro!
         * I comandi possono essere:
         * CommandType.CHANGE_NAME
         * CommandType.DISCONNECT
         * Se non sono questi
         */
    }

    public static void handle(Message msg) throws HandlerException {

    }


    public static void send(Message msg, DataOutputStream stream){
        /*
         * Serializzare MSG e spararlo nella stream. PUNTO
         *
         */
    }

    public static void sendBroadcast(Message msg, ArrayList<DataOutputStream> streams){
        // for di send()
    }
}
