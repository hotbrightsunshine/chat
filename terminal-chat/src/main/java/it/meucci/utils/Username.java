package it.meucci.utils;

/**
 * The class representing a user's username.
 * It is used to address Messages to their recipients.
 * In the server, each username is associated with a Socket.
 * It has two static methods that return an object with username "server" and one with username "everyone".
 * These two usernames are forbidden and the server cannot assign them.
 * Intuitively, 'server' is the username used to address a message to the server;
 * 'everyone' sends the message in broadcast.
 */
public abstract class Username {
    /**
     * As explained above, `everyone` addresses every user connected to the chat.
     */
    public final static String everyone = "everyone";
    /**
     * `server` addresses the server, and it is used in SERVER_ANN and COMMAND messages.
     */
    public final static String server = "server";
}
