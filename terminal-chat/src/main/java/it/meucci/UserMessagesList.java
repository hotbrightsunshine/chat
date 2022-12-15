package it.meucci;

import it.meucci.utils.Message;
import it.meucci.utils.Username;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserMessagesList {
    private Map<String, ArrayList<Message>> messages;

    public UserMessagesList(){
        messages = Collections.synchronizedMap(new HashMap<>());
    }

    public ArrayList<String> getUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        for(String s : messages.keySet()){
            usernames.add(s);
        }
        return usernames;
    }

    public void addMessage(Message message){
        if (getUsernames().contains(message.getFrom())){
            messages.get(message.getFrom()).add(message);
        } else {
            ArrayList<Message> temp = new ArrayList<>();
            temp.add(message);
            messages.put(message.getFrom(), temp);
        }
    }
    public void removeUser(String username){
        messages.remove(username);
    }

    public void addUser(String username){
        if(username == null || username.equals("")){
            return;
        }
        messages.put(username, new ArrayList<>());
    }

    public void updateUser(String oldUsername, String newUsername){
        ArrayList<Message> old_al = messages.get(oldUsername);
        messages.remove(oldUsername);
        messages.put(newUsername, old_al);
    }

    public boolean contains(String username){
        return this.messages.keySet().contains(username);
    }

    public ArrayList<Message> getMessagesFrom(String username){
        if(messages.containsKey(username)){
            return messages.get(username);
        } else {
            return new ArrayList<>();
        }
    }
}
