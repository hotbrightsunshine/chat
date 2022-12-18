package it.fi.meucci.exceptions;

import it.fi.meucci.utils.ServerAnnouncement;

/**
 * Implementation of HandlerException for a NEED_NAME Server Announcement
 */
public class NeedNameException extends HandlerException {

    public NeedNameException() {
        super();
    }

    @Override
    public ServerAnnouncement getServerAnnouncement() {
        return ServerAnnouncement.NEED_NAME;
    }
    
    
}
