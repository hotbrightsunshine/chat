package it.fi.meucci.utils;

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
    public static Message listMessage(ArrayList<Username> utenti){

    }

    /**
     * Crea un messaggio di tipo NEED_NAME
     * @return un messaggio di tipo NEED_NAME
     */
    public static Message needNameMessage(){

    }

    /**
     * Crea un messaggio di tipo DEST_NOT_CORRECT
     * @return un messaggio di tipo DEST_NOT_CORRECT
     */
    public static Message destNotCorrectMessage(){
        
    }
}
