package it.fi.meucci.utils;

import java.util.ArrayList;

public enum ServerAnnouncement {
    JOINED, // Un utente entra
    LEFT, // Un utente è uscito
    NAME_OK, // Il nome inserito va bene
    NAME_NOT_OK, // Il nome inserito non va bene
    NEED_NAME, // Il client non ha un nome
    LIST, // Lista dei messaggi
    DEST_NOT_CORRECT // Il destinatario del server non è corretto
    ;

    /**
     * Crea un messaggio di tipo LIST
     * @param utenti la lista degli utenti connessi del server
     * @return Un messaggio di tipo LIST
     */
    public static Message listAnnouncement(ArrayList<Username> utenti, Username to){

    }

    /**
     * Crea un messaggio con un solo argomento (il tipo)
     * @return un messaggio di tipo NEED_NAME
     */
    public static Message createServerAnnouncement(Username to, ServerAnnouncement t){
        String[] tmp = {t.toString()};
        return new Message(Type.SERVER_ANN, Username.server(), to, tmp);
    }
}
