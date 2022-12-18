package it.meucci.utils;

/**
 * Represents the types of Server Announcement a message of type SERVER_ANN can have.
 * Cfr. {@link it.meucci.utils.Type}.
 */
public enum ServerAnnouncement {
    /**
     * Sent when a new user joins the server
     */
    JOINED,

    /**
     * Sent when a user lefts the server
     */
    LEFT,

    /**
     * Sent when the user requesting a new username has inserted a valid one.
     */
    NAME_OK,

    /**
     * Sent when the user requesting a new username has inserted a not valid one.
     */
    NAME_NOT_OK,

    /**
     * Sent when the user attempting to send a message doesn't have a valid username set.
     */
    NEED_NAME,

    /**
     * Sent when the server sends a list of connected usernames
     */
    LIST,

    /**
     * Sent when a message doesn't have a valid addressee
     */
    DEST_NOT_CORRECT,

    /**
     * Sent as a confirmation to the `DISCONNECT` command {@link it.meucci.utils.CommandType}
     */
    DISCONNECT,

    /**
     * Sent when the command issued by the user is not valid nor recognized
     */
    COMMAND_NOT_RECOGNIZED,

    /**
     * Sent when the username of a user successfully changed
     */
    USERNAME_CHANGED
}
