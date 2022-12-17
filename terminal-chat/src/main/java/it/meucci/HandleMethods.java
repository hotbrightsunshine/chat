package it.meucci;

import it.meucci.utils.Errors;
import it.meucci.utils.Username;

import java.util.ArrayList;

public abstract class HandleMethods 
{
    /**
     * 
     * @param username
     */
    static void left(String username)
    {
        //rimuove un utente dalla lista
        App.client.userMessagesList.removeUser(username);
    }

    /**
     * 
     * @param username
     */
    static void join(String username)
    {
        //aggiunge un utente alla lista
        App.client.userMessagesList.addUser(username);
    }

    static void nameOk()
    {
        // TODO
        //System.out.println("ok");
    }

    static void nameNotOk()
    {
        // TODO
        //System.out.println(Errors.NAME_NOT_AVAILABLE);
    }

    /**
     * 
     * @param usernames
     */
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
        System.out.println(Errors.DEST_NOT_CORRECT);
    }

    static void disconnect()
    {

    }

    /**
     * 
     * @param oldUsername
     * @param newUsername
     */
    static void usernameChanged(String oldUsername, String newUsername)
    {
        //cambia l'username dei client
        App.client.userMessagesList.updateUser(oldUsername, newUsername);
    }

    static void commandNotRecognized()
    {
        System.out.println(Errors.COMMAND_NOT_RECOGNIZED);
    }
}
