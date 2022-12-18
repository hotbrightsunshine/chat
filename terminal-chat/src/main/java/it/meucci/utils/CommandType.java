package it.meucci.utils;

/**
 * This represents all the possible types of commands the server can handle
 */
public enum CommandType {
    /**
     * Requests the server to change the client's name
     */
    CHANGE_NAME,
    /**
     * Requests the server to disconnect the user
     */
    DISCONNECT
}
