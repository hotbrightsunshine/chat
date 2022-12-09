package it.meucci.utils;

/**
 *  La classe che rappresenta l’username di un utente.
 *  Serve per indirizzare i Messaggi ai propri destinatari.
 *  Nel server, ogni Username è associato ad un Socket.
 *  Ha due metodi statici che ritornano un oggetto con username “server” e uno con username “everyone”.
 *  Questi due nomi utenti sono proibiti e il server non può assegnarli.
 *  Intuitivamente, “server” è l’username che serve per indirizzare un messaggio al server;
 *  “everyone” manda il messaggio in broadcast.
 */
public abstract class Username {
    public final static String everyone = "everyone";
    public final static String server = "server";
}
