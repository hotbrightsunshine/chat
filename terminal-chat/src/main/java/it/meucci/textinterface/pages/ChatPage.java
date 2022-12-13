package it.meucci.textinterface.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import it.meucci.App;
import it.meucci.textinterface.TextInterface;
import it.meucci.utils.Message;

public class ChatPage implements Page{

    private String username;

    public ChatPage(String username){
        this.username = username;
    }

    @Override
    public boolean isTextInputEnabled() {
        return true;
    }

    @Override
    public String getHeader() {
        return "Chatting with " + username;
    }

    @Override
    public ArrayList<String> getContent() {

        if(App.client.userMessagesList.contains(username)){
            // TODO splitta l'array e Collections.,reverse()
            int chunk = TextInterface.LINES; // chunk size to divide

            ArrayList<Message> ar = new ArrayList<>();
            ar = App.client.userMessagesList.getMessagesFrom(username);

            ArrayList<ArrayList<Message>> arranged = chunk(ar, TextInterface.LINES);

            ArrayList<Message> joined = new ArrayList<>();
            for(ArrayList<Message> a : arranged){
                joined.addAll(a);
            }
        
            
            return new ArrayList<String>(joined.stream().map( x -> x.toString() ).collect(Collectors.toList()));

            // https://stackoverflow.com/questions/27857011/how-to-split-a-string-array-into-small-chunk-arrays-in-java
        } else {
            return new ArrayList<>();
        }
    }
    
    private <T> ArrayList<ArrayList<T>> chunk(ArrayList<T> original, int chunkSize){
        int k = 0; ArrayList<ArrayList<T>> toReturn = new ArrayList<>();
        for (T t : original) {
            ArrayList<T> temp = new ArrayList<>();
            temp.add(t);
            if(k+1 == chunkSize){
                k = 0;
                toReturn.add(temp);
            } else {
                k++;
            }
        }
        Collections.reverse(toReturn);
        return toReturn;
    }
}
