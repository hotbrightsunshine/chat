package it.fi.meucci.exceptions;

import it.fi.meucci.utils.ServerAnnouncement;

/**
 * Implementation of HandlerException for a DEST_NOT_CORRECT Server Announcement
 */
public class DestNotCorrectException extends HandlerException {

    @Override
    public ServerAnnouncement getServerAnnouncement() {
        return ServerAnnouncement.DEST_NOT_CORRECT;
    }
    
}
