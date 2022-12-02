package it.fi.meucci.exceptions;

import it.fi.meucci.utils.ServerAnnouncement;

public class DisconnectException extends HandlerException {

    @Override
    public ServerAnnouncement getServerAnnouncement() {
        return ServerAnnouncement.DISCONNECT;
    }
    
}
