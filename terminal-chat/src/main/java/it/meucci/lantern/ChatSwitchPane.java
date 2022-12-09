package it.meucci.lantern;

import com.googlecode.lanterna.gui2.*;

public class ChatSwitchPane {
    private static Panel root;
    public static void initialize(){
        root = new Panel();
        root.setLayoutManager(new GridLayout(1));
    }

    public static Panel getRoot(){
        return root;
    }
}
