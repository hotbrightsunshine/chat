package it.fi.meucci.utils;

/**
 * I messaggi che vengono scambiati tra client e server.
 * Hanno un tipo, un destinatario, e un mittente.
 * Contengono un campo opzionale chiamato “content”, usato per vari scopi, dipendentemente dal tipo del messaggio.
 * Ha un metodo statico validate che converte l’user input in un Message.
 */
public class Message {
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
    private Message(Type type, Username from, Username to, String[] args) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.args = args;
    }

    /**
     * Valida una stringa e crea un messaggio.
     * @param str Il messaggio di input
     * @return Il messaggio valido oppure null.
     */
    public static Message validate(String str){
        // Unimplemented
        return null;
    }
}
