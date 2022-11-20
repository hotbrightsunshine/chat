package it.fi.meucci.utils;

public enum ServerAnnouncement {
    JOINED, // Un utente entra
    LEFT, // Un utente è uscito
    NAME_OK, // Il nome inserito va bene
    NAME_NOT_OK, // Il nome inserito non va bene
    NEED_NAME, // Il client non ha un nome
    LIST // Lista dei messaggi
    ;

    /**
     * Crea un messaggio di tipo LIST
     * @param utenti la lista degli utenti connessi del server
     * @return Un messaggio di tipo LIST
     */
    public static Message listMessage(ArrayList<Username> utenti){

    }

    /**
     * 
     * @return un messaggio di tipo NEED_NAME
     */
    public static Message needNameMessage(){

    }
}
