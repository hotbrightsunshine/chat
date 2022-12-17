package it.fi.meucci.utils;

import java.util.ArrayList;
import java.util.Objects;

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
     * Sent as a confirmation to the `DISCONNECT` command {@link it.fi.meucci.utils.CommandType }
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
    ;

    /**
     * Creates a new LIST announcement
     * @param utenti the list of usernames currently connected
     * @param to The addressee
     * @return A LIST message
     */
    public static Message createListAnnouncement(ArrayList<String> utenti, String to) {
        ArrayList<String> strings= new ArrayList<>();
        strings.addAll(utenti);
        strings.remove("");
        strings.add(0, ServerAnnouncement.LIST.toString());
        return new Message(Type.SERVER_ANN, Username.server, to, strings);
    }

    /**
     * Creates a new Server announcement
     * @param t The Type of the announcement {@link it.fi.meucci.utils.ServerAnnouncement}
     * @param to The addressee
     * @return The newly created announcement
     */
    public static Message createServerAnnouncement(
        ServerAnnouncement t, 
        String to) {

        ArrayList<String> tmp = new ArrayList<>();
        tmp.add(t.toString());
        return new Message(Type.SERVER_ANN, Username.server, to, tmp);
    }

    /**
     * Creates a new JOINED message
     * @param joined the string of the new user's username
     * @return a JOINED message
     */
    public static Message createJoinedAnnouncement(String joined) {

        ArrayList<String> tmp = new ArrayList<>();
        tmp.add(ServerAnnouncement.JOINED.toString());
        tmp.add(joined);
        return new Message(Type.SERVER_ANN, Username.server, Username.everyone, tmp);
    }

    /**
     * Creates a LEFT message
     * @param left the left user's username
     * @return a LEFT message
     */
    public static Message createLeftAnnouncement(String left) {

        ArrayList<String> tmp = new ArrayList<>();
        tmp.add(ServerAnnouncement.LEFT.toString());
        tmp.add(left);
        return new Message(Type.SERVER_ANN, Username.server, Username.everyone, tmp);
    }

    /**
     *
     * @param before
     * @param after
     * @return
     */
    public static Message createUsernameChangedAnnouncement(
        String before,
        String after) {

        ArrayList<String> tmp = new ArrayList<>();
        tmp.add(ServerAnnouncement.USERNAME_CHANGED.toString());
        tmp.add(Objects.requireNonNullElse(before, ""));
        tmp.add(after);
        return new Message(Type.SERVER_ANN, Username.server, Username.everyone, tmp);
    }
}
