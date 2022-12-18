package it.meucci.commands;

/**
 * The type of command a user type.
 * These are all the available commands a user can type.
 */
public enum CommandType {
    /**
     * Displays the help menu
     */
    HELP,
    /**
     * Ask the server to disconnect
     */
    DISCONNECT,
    /**
     * Change the user's nickname
     */
    NICK,
    /**
     * Displays the list of connected users
     */
    WHO,
    /**
     * Displays the user's username
     */
    ME,
    /**
     * Sends a message to another user or to everyone
     */
    SEND,
    /**
     * The type of command describing an invalid one
     */
    INVALID;
}
