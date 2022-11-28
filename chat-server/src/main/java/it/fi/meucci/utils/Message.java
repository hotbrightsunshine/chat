package it.fi.meucci.utils;


import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.fi.meucci.exceptions.HandlerException;

/**
 * I messaggi che vengono scambiati tra client e server.
 * Hanno un tipo, un destinatario, e un mittente.
 * Contengono un campo opzionale chiamato “content”, usato per vari scopi, dipendentemente dal tipo del messaggio.
 * Ha un metodo statico validate che converte l’user input in un Message.
 */
public class Message
{
    private Type type;
    private Username from;
    private Username to;
    private String[] args;

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
        @JsonProperty("from") Username from,
        @JsonProperty("to") Username to,
        @JsonProperty("args") String[] args) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.args = args;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Username getFrom() {
        return from;
    }

    public void setFrom(Username from) {
        this.from = from;
    }

    public Username getTo() {
        return to;
    }

    public void setTo(Username to) {
        this.to = to;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
