package it.meucci.textinterface;

import java.util.ArrayList;

public class HelpPage implements Page{
    @Override
    public boolean isTextInputEnabled() {
        return true;
    }

    @Override
    public String getHeader() {
        return "Help Menu";
    }

    @Override
    public ArrayList<String> getContent() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("/connect <address>; connect to a chat server");
        strings.add("/disconnect; disconnect and quit");
        strings.add("/help; shows this help page");
        strings.add("/prev; goes to the previous page");
        strings.add("/next; goes to the next page");
        strings.add("/connect <address>; connect to a chat server");
        strings.add("/disconnect; disconnect and quit");
        strings.add("/help; shows this help page");
        strings.add("/prev; goes to the previous page");
        strings.add("/next; goes to the next page");
        strings.add("/connect <address>; connect to a chat server");
        strings.add("/disconnect; disconnect and quit");
        strings.add("/help; shows this help page");
        strings.add("/prev; goes to the previous page");
        strings.add("/next; goes to the next page");
        return strings;
    }
}
