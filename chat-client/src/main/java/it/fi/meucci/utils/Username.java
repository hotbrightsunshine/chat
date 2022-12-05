package it.fi.meucci.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private String username;
    private static Username everyone = new Username("everyone");
    private static Username server = new Username("server");
    public Username(
        @JsonProperty("from") String username){
        this.username = username;
    }

    /**
     * @return L'username "everyone"
     */
    public static Username everyone(){
        return Username.everyone;
    }

    /**
     * @return L'username "server"
     */
    public static Username server(){
        return Username.server;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Username)) {
            return false;
        } else {
            return username.equals(((Username) obj).username);
        }
    }

    @Override
    public String toString() {
        if(this.username == null) {
            return "";
        }
        return username;
    }
}
