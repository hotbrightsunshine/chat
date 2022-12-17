package it.fi.meucci.exceptions;

import it.fi.meucci.utils.ServerAnnouncement;

public class NameNotOkException extends HandlerException{
    
    public NameNotOkException() {
        super();
    }

    @Override
    public ServerAnnouncement getServerAnnouncement() {
        return ServerAnnouncement.NAME_NOT_OK;
    }
    
}
