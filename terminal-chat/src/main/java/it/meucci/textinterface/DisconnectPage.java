package it.meucci.textinterface;

import java.util.ArrayList;

public class DisconnectPage implements Page {

    @Override
    public boolean isTextInputEnabled() {
        return false;
    }

    @Override
    public String getHeader() {
        return "You have disconnected.";
    }

    @Override
    public ArrayList<String> getContent() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("We hope you had a great time in here.");
        strings.add("If you did, please consider having a look at our GitHub repository.");
        strings.add("https://github.com/hotbrightsunshine/chat");
        return strings;
    }
}
