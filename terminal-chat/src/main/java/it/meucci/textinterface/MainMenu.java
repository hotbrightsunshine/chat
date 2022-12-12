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
        ArrayList<String> content = new ArrayList<>();
        content.add("You have successfully connected. To start talking,");
        content.add("hit /chat <username>. Chats do not refresh automatically: ");
        content.add("to check if someone has entered, hit /chats again.");
        for (String username : App.client.userMessagesList.getUsernames()){
            content.add(" - " + username);
        }
        return content;
    }
}
