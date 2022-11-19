package it.fi.meucci.utils;

/**
 *  La classe che rappresenta l’username di un utente.
 *  Serve per indirizzare i Messaggi ai propri destinatari.
 *  Nel server, ogni Username è associato ad un Socket.
 *  Ha due metodi statici che ritornano un oggetto con username “server” e uno con username “everyone”.
 *  Questi due nomi utenti sono proibiti e il server non può assegnarli.
 *  Intuitivamente, “server” è l’username che serve per indirizzare un messaggio al server;
 *  “everyone” manda il messaggio in broadcast.
 */
public class Username {
    String username;
    private static Username everyone = new Username("everyone");
    private static Username server = new Username("server");
    public Username(String username){
        this.username = username;
    }

    /**
     * @return L'username "everyone"
     */
    public Username everyone(){
        return Username.everyone;
    }

    /**
     * @return L'username "server"
     */
    public Username server(){
        return Username.server;
    }
}
