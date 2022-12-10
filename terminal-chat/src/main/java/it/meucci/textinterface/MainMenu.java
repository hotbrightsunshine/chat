package it.meucci.textinterface;

import java.util.ArrayList;

public class MainMenu implements Page{
    // deve essere un attributo static dell'interfaccia!
    @Override
    public boolean isTextInputEnabled() {
        return false;
    }

    @Override
    public String getHeader() {
        return null;
    }

    @Override
    public ArrayList<String> getContent() {
        return null;
    }
}
