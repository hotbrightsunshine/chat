package it.meucci;

import it.meucci.textinterface.Errors;
import it.meucci.textinterface.TextInterface;
import it.meucci.utils.Username;

import java.util.ArrayList;

public abstract class HandleMethods 
{
    static void left(String username)
    {
        //rimuove un utente dalla lista
        App.client.userMessagesList.removeUser(username);
    }

    static void join(String username)
    {
        //aggiunge un utente alla lista
        App.client.userMessagesList.addUser(username);
    }

    static void nameOk()
    {
        App.client.changeUsername(App.client.getPendingUsername());
        App.client.setPendingUsername("");
    }

    static void nameNotOk()
    {
        TextInterface.setError(Errors.NAME_NOT_AVAILABLE);
        App.client.setPendingUsername("");
    }

    static void needName()
    {
        //errore needname
        TextInterface.setError(Errors.NEED_A_NAME);
        
    }

    static void list(ArrayList<String> usernames)
    {
        //restituisce la lista appena connesso
        App.client.userMessagesList.addUser(Username.everyone);
        for (String username : usernames) 
        {
            join(username);
        }
    }

    static void destNotOk()
    {
        TextInterface.setError(Errors.DEST_NOT_CORRECT);
    }

    static void disconnect()
    {
        // TO DO
    }

    static void usernameChanged(String oldUsername, String newUsername)
    {
        //cambia l'username dei client
        App.client.userMessagesList.updateUser(oldUsername, newUsername);
    }

    static void commandNotRecognized()
    {
        TextInterface.setError(Errors.COMMAND_NOT_RECOGNIZED);
    }
}
