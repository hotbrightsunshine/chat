package main.java.it.fi.meucci;

import java.util.logging.Handler;

import it.fi.meucci.utils.ServerAnnouncement;

public class HandlerException extends Exception {

    ServerAnnouncement s;

    public HandlerException() {
    }

    public HandlerException(String message) {
        super(message);
    }
    
    public HandlerException(ServerAnnouncement s){
        super(s);
        this.s = s;
    }
}
