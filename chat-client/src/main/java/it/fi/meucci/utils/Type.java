package it.fi.meucci.utils;

/**
 * È una enumerazione che contiene i possibili tipi che un messaggio può avere
 */
public enum Type {
    /**
     * È un comando, e deve necessariamente essere rivolto al server
     */
    COMMAND,
    /**
     * È il messaggio per un utente
     */
    MESSAGE,
    /**
     * È un messaggio dal server per gli utenti;
     * che comunica l’uscita o l’entrata di un client all’interno
     * della chat oppure un messaggio di errore ad esempio.
     */
    SERVER_ANN
}
