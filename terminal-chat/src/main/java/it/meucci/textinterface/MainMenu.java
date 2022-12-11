package it.meucci.textinterface;

import it.meucci.App;

import java.util.ArrayList;

public class MainMenu implements Page {
    public MainMenu(){

    }

    @Override
    public boolean isTextInputEnabled() {
        return true;
    }

    @Override
    public String getHeader() {
        return "Chat List";
    }

    @Override
    public ArrayList<String> getContent() {
        return App.client.getUsernames();
    }
}
