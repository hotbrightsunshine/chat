package it.fi.meucci;

//import it.fi.meucci.utils.ServerAnnouncement;
import it.fi.meucci.utils.ServerAnnouncement;

public class HandlerException extends Exception {

    ServerAnnouncement s;

    public HandlerException() {
    }

    public HandlerException(String message) {
        super(message);
    }
    
    public HandlerException(ServerAnnouncement s){
        super(String.valueOf(s));
        this.s = s;
    }
}
