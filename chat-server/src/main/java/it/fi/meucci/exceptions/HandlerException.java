package it.fi.meucci.exceptions;

//import it.fi.meucci.utils.ServerAnnouncement;
import it.fi.meucci.utils.ServerAnnouncement;

/**
 * This class represents one of the fundamental mechanism with which the server work.
 * This is an abstract class, so their abstract methods must be implemented.
 * Abstract methods are already implemented by other classes in this same package,
 * and they all implement the method `getServerAnnouncement()` so that it can be printed and
 * can be easily accessed by the Handler.
 * This way, a HandlerException can be thrown, and only one needs to be handled.
 * For further information, cfr. handle() method in {@link it.fi.meucci.RequestListener}
 */
public abstract class HandlerException extends Exception {

    ServerAnnouncement s;

    public HandlerException() {
        super();
        s = getServerAnnouncement();
    }

    /**
     * This is the method that must be implemented by classes in this package.
     * It must return a type of server announcement, that will then be sent as a message to the client.
     * @return
     */
    public abstract ServerAnnouncement getServerAnnouncement();

    @Override
    public String toString() {
        return "HandlerException: " + getServerAnnouncement().toString() + " was issued.";
    }

    public void print() {
        System.out.println(this);
    }
}
