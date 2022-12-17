package it.fi.meucci.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Messages that are exchanged between client and server.
 * They have a type, a recipient, and a sender.
 * Contain an optional field called "content", used for various purposes, depending on the type of message.
 * Has a static validate method that converts user input into a Message.
 */
public class Message
{
    private final Type type;
    private final String from;
    private final String to;
    private ArrayList<String> args;

    /**
     * Message Costructor
     * @param type The Message type {@link it.fi.meucci.utils.Type }
     * @param from The sender
     * @param to The addressee
     * @param args Arguments of the message. They follow a different syntax depending on the type of the message
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
