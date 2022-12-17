package it.fi.meucci.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * I messaggi che vengono scambiati tra client e server.
 * Hanno un tipo, un destinatario, e un mittente.
 * Contengono un campo opzionale chiamato “content”, usato per vari scopi, dipendentemente dal tipo del messaggio.
 * Ha un metodo statico validate che converte l’user input in un Message.
 */
public class Message
{
    private final Type type;
    private final String from;
    private final String to;
    private ArrayList<String> args;

    /**
     * Costruttore per il messaggio.
     * È privato perché l'utente può costruire il messaggio solo con validate().
     * @param type Il tipo del messaggio {@link it.fi.meucci.utils.Type }
     * @param from L'username {@link it.fi.meucci.utils.Message} di provenienza
     * @param to L'username {@link it.fi.meucci.utils.Message} di destinazione
     * @param args La lista di argomenti del comando o del messaggio
     * @since 1.0
     */
    public Message(
        @JsonProperty("type") Type type, 
        @JsonProperty("from") String from,
        @JsonProperty("to") String to,
        @JsonProperty("args") ArrayList<String> args) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.args = args;
    }

    public Type getType() {
        return type;
    }


    public String getFrom() {
        if (this.from == null ) return "";
        return from;
    }

    public String getTo() {
        if (this.to == null ) return "";
        return to;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }
}
