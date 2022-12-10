package it.meucci.textinterface;

import java.util.ArrayList;

public interface Page {
    boolean isTextInputEnabled();
    String getHeader();
    ArrayList<String> getContent();
}
