package it.fi.meucci.utils;

public class Message {
    private Type type;
    private Username from;
    private Username to;
    private String[] args;

    private Message(Type type, Username from, Username to, String[] args) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.args = args;
    }

    public Message validate(String str){
        // Unimplemented
        return null;
    }
}
