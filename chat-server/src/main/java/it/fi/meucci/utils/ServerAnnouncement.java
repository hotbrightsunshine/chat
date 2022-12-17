package it.fi.meucci.utils;

import java.util.ArrayList;

public enum ServerAnnouncement {
    JOINED, // Un utente entra
    LEFT, // Un utente è uscito
    NAME_OK, // Il nome inserito va bene
    NAME_NOT_OK, // Il nome inserito non va bene
    NEED_NAME, // Il client non ha un nome
    LIST, // Lista dei partecipanti
    DEST_NOT_CORRECT, // Il destinatario del server non è corretto
    DISCONNECT,
    COMMAND_NOT_RECOGNIZED,
    USERNAME_CHANGED
    ;

    /**
     * Crea un messaggio di tipo LIST
     * @param utenti la lista degli utenti connessi del server
     * @return Un messaggio di tipo LIST
     */
    public static Message createListAnnouncement(ArrayList<String> utenti, String to){
        ArrayList<String> strings= new ArrayList<>();
        for(String u : utenti){
            strings.add(u);
        }
        strings.remove("");
        strings.add(0, ServerAnnouncement.LIST.toString());
        return new Message(Type.SERVER_ANN, Username.server, to, strings);
    }

    /**
     * Crea un messaggio con un solo argomento (il tipo)
     * @param t Type of the ServerAnnouncement
     * @param to A chi è direzionato il messaggio
     * @return un messaggio di tipo NEED_NAME
     */
    public static Message createServerAnnouncement(
        ServerAnnouncement t, 
        String to){

        ArrayList<String> tmp = new ArrayList<>();
        tmp.add(t.toString());
        return new Message(Type.SERVER_ANN, Username.server, to, tmp);
    }

    public static Message createJoinedAnnouncement(String joined) {

        ArrayList<String> tmp = new ArrayList<>();
        tmp.add(ServerAnnouncement.JOINED.toString());
        tmp.add(joined.toString());
        return new Message(Type.SERVER_ANN, Username.server, Username.everyone, tmp);
    }

    public static Message createLeftAnnouncement(String left) {

        ArrayList<String> tmp = new ArrayList<>();
        tmp.add(ServerAnnouncement.LEFT.toString());
        tmp.add(left.toString());
        return new Message(Type.SERVER_ANN, Username.server, Username.everyone, tmp);
    }

    public static Message createUsernameChangedAnnouncement(
        String before,
        String after) {

        ArrayList<String> tmp = new ArrayList<>();
        tmp.add(ServerAnnouncement.USERNAME_CHANGED.toString());
        if(before == null){
            tmp.add("");
        } else
            tmp.add(before.toString());
        tmp.add(after.toString());
        return new Message(Type.SERVER_ANN, Username.server, Username.everyone, tmp);
    }
}
