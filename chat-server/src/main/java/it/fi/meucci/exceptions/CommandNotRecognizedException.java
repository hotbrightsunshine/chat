package it.fi.meucci.exceptions;

import it.fi.meucci.utils.ServerAnnouncement;

public class CommandNotRecognizedException extends HandlerException {

    @Override
    public ServerAnnouncement getServerAnnouncement() {
        return ServerAnnouncement.COMMAND_NOT_RECOGNIZED;
    }
    
}
