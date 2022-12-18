package it.meucci;

import it.meucci.utils.Message;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper around a synchronized map to handle and store messages and usernames.
 * Messages are not shown then, but their support are still implemented for further updates.
 */
public class UserMessagesList {
    /**
     * The map
     */
    private final Map<String, ArrayList<Message>> messages;

    /**
     * Constructs a new synchronized map.
     */
    public UserMessagesList() {
        messages = Collections.synchronizedMap(new HashMap<>());
    }

    /**
     * @return The list of currently connected usernames.
     */
    public ArrayList<String> getUsernames() {
        return new ArrayList<>(messages.keySet());
    }

    /**
     * Adds a {@link it.meucci.utils.Message} to a conversation
     * @param message the message to be added
     */
    public void addMessage(Message message) {
        if (getUsernames().contains(message.getFrom())) {
            messages.get(message.getFrom()).add(message);
        } else {
            ArrayList<Message> temp = new ArrayList<>();
            temp.add(message);
            messages.put(message.getFrom(), temp);
        }
    }

    /**
     * Removes a username and its messages
     * @param username The just left user's username
     */
    public void removeUser(String username) {
        messages.remove(username);
    }

    /**
     * Adds a new username to the list of the connected ones
     * @param username The just joined user's username
     */
    public void addUser(String username) {
        if(username == null || username.equals("")) {
            return;
        }
        messages.put(username, new ArrayList<>());
    }


    /**
     * Called when a username change its name; i.e. when a server sends a USERNAME_CHANGED
     * {@link it.meucci.utils.ServerAnnouncement}.
     * @param oldUsername The old username
     * @param newUsername The new one
     */
    public void updateUser(String oldUsername, String newUsername) {
        ArrayList<Message> old_al = messages.get(oldUsername);
        messages.remove(oldUsername);
        messages.put(newUsername, old_al);
    }

    /*
    public boolean contains(String username) {
        return this.messages.keySet().contains(username);
    }

    public ArrayList<Message> getMessagesFrom(String username) {
        if(messages.containsKey(username)) {
            return messages.get(username);
        } else {
            return new ArrayList<>();
        }
    }
    */
}
