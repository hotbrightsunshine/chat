package it.meucci.utils;

/**
 * Describes the message type in the JSON field
 */
public enum Type
{
    /**
     * A command: from client to server only.
     * It is used to apply changes or to request a disconnecting action.
     */
    COMMAND,
    /**
     * A message: from client to client.
     * It is used by clients to share information and text to one another.
     */
    MESSAGE,
    /**
     * A server announcement: from server to client only.
     * It is used by the server to send information or error messages to a client.
     */
    SERVER_ANN,
}
