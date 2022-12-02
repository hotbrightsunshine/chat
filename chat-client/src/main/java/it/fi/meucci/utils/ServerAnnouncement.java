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
    public static Message createListAnnouncement(ArrayList<Username> utenti, Username to){
        ArrayList<String> strings= new ArrayList<>();
        for(Username u : utenti){
            strings.add(u.getUsername());
        }
        strings.add(0, ServerAnnouncement.LIST.toString());
        String[] tmp = (String[]) strings.toArray();
        return new Message(Type.SERVER_ANN, Username.server(), to, tmp);
    }

    /**
     * Crea un messaggio con un solo argomento (il tipo)
     * @param t Type of the ServerAnnouncement
     * @param to A chi è direzionato il messaggio
     * @return un messaggio di tipo NEED_NAME
     */
    public static Message createServerAnnouncement(
        ServerAnnouncement t, 
        Username to){
        String[] tmp = {t.toString()};
        return new Message(Type.SERVER_ANN, Username.server(), to, tmp);
    }

    public static Message createJoinedAnnouncement(
        Username joined) {
        String[] tmp = { ServerAnnouncement.JOINED.toString(), joined.toString()};
        return new Message(Type.SERVER_ANN, Username.server(), Username.everyone(), tmp);
    }

    public static Message createLeftAnnouncement(
        Username left) {
        String[] tmp = {ServerAnnouncement.LEFT.toString(), left.toString()};
        return new Message(Type.SERVER_ANN, Username.server(), Username.everyone(), tmp);
    }

    public static Message createUsernameChangedAnnouncement(
        Username before,
        Username after) {
        String[] tmp = {
            ServerAnnouncement.USERNAME_CHANGED.toString(),
            before.toString(), 
            after.toString()};
        return new Message(Type.SERVER_ANN, Username.server(), Username.everyone(), tmp);
    }
}
