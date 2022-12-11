package it.meucci.textinterface;

import java.util.ArrayList;

public class DisconnectPage implements Page {

    /**
     * Metodo che adibito alla disconnessione settando il valore a false
     * @return valore falso per la disconnessione
     */
    @Override
    public boolean isTextInputEnabled() {
        return false;
    }

    /**
     * Metodo che stampa una stringa per confermare la disconnesione
     * @return titolo 
     */
    @Override
    public String getHeader() {
        return "You have (been) disconnected.";
    }


    /**
     * Metodo che stampa del testo una volta eseguita la disconnessione
     * @return argomento di disconnessione
    */
    @Override
    public ArrayList<String> getContent() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("We hope you had a great time in here.");
        strings.add("If you did, please consider having a look at our GitHub repository.");
        strings.add("https://github.com/hotbrightsunshine/chat");
        return strings;
    }
}
