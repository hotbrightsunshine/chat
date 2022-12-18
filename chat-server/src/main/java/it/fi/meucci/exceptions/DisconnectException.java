package it.fi.meucci.exceptions;

import it.fi.meucci.utils.ServerAnnouncement;

/**
 * Implementation of HandlerException for a DISCONNECT Server Announcement
 */
public class DisconnectException extends HandlerException {

    @Override
    public ServerAnnouncement getServerAnnouncement() {
        return ServerAnnouncement.DISCONNECT;
    }
    
}
