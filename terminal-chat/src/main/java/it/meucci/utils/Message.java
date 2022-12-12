package it.meucci.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.meucci.App;

import java.util.ArrayList;

import static it.meucci.utils.CommandType.CHANGE_NAME;

/**
 * I messaggi che vengono scambiati tra client e server.
 * Hanno un tipo, un destinatario, e un mittente.
 * Contengono un campo opzionale chiamato “content”, usato per vari scopi, dipendentemente dal tipo del messaggio.
 * Ha un metodo statico validate che converte l’user input in un Message.
 */
public class Message
{
    private Type type;
    private String from;
    private String to;
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

    @JsonIgnore
    public boolean isChangeNameMessageValid(){
        if(this.type != Type.COMMAND)
            return false;

        if(this.args.size() == 2){
            if(!this.args.get(0).equals(CHANGE_NAME.toString())){
                return false;
            }
        }
        
        return true;
    }
    // fare il metodo per validare il messaggio se ha i parametri sbagliati per il cambio nome

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getFrom() {
        if (this.from == null ) return "";
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        if (this.to == null ) return "";
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }

    public static Message createChangeNameCommand(String oldName, String newName){
        ArrayList<String> args = new ArrayList<>();
        args.add(CHANGE_NAME.toString());
        args.add(newName);
        Message m = new Message(Type.COMMAND, App.client.getUsername(), Username.server, args);
        //System.out.println(m);
        return m;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", args=" + args +
                '}';
    }
}
