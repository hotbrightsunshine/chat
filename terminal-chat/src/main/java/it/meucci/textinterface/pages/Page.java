package it.meucci.textinterface.pages;

import java.util.ArrayList;

public interface Page {
    boolean isTextInputEnabled();
    String getHeader();
    ArrayList<String> getContent();
}
