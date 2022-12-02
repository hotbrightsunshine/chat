package it.fi.meucci.exceptions;

//import it.fi.meucci.utils.ServerAnnouncement;
import it.fi.meucci.utils.ServerAnnouncement;

public abstract class HandlerException extends Exception {

    ServerAnnouncement s;

    public HandlerException() {
        super();
        s = getServerAnnouncement();
    }
    
    public HandlerException(ServerAnnouncement s){
        super();
        this.s = s;
    }

    public abstract ServerAnnouncement getServerAnnouncement();

    @Override
    public String toString() {
        return "HandlerException: " + getServerAnnouncement().toString() + " was issued.";
    }

    public void print(){
        System.out.println(this.toString());
    }
}
