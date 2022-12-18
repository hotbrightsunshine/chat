package it.fi.meucci.exceptions;

import it.fi.meucci.utils.ServerAnnouncement;

/**
 * Implementation of HandlerException for a COMMAND_NOT_RECOGNIZED Server Announcement
 */
public class CommandNotRecognizedException extends HandlerException {

    @Override
    public ServerAnnouncement getServerAnnouncement() {
        return ServerAnnouncement.COMMAND_NOT_RECOGNIZED;
    }
    
}
