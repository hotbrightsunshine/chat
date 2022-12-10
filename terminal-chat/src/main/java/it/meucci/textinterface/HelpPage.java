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
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        strings.add("5");
        strings.add("6");
        strings.add("7");
        strings.add("8");
        strings.add("9");
        strings.add("10");
        strings.add("11");
        strings.add("12");
        return strings;
    }
}
