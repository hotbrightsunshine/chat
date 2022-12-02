package it.fi.meucci.exceptions;

import it.fi.meucci.utils.ServerAnnouncement;

public class DestNotCorrectException extends HandlerException {

    @Override
    public ServerAnnouncement getServerAnnouncement() {
        return ServerAnnouncement.DEST_NOT_CORRECT;
    }
    
}
