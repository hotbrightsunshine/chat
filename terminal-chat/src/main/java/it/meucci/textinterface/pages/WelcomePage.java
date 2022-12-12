package it.meucci.textinterface.pages;

import java.util.ArrayList;

public class WelcomePage implements Page {
    @Override
    public boolean isTextInputEnabled() {
        return true;
    }

    @Override
    public String getHeader() {
        return "Welcome!";
    }

    @Override
    public ArrayList<String> getContent() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("This client allows you to communicate with other");
        strings.add("users that are connected to the same client as yours.");
        strings.add("To start, please type /connect <address>.");
        strings.add("To get a list of available commands, please type /help.");
        return strings;
    }

}
