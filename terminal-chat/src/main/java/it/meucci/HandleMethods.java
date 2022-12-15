package it.meucci;

import it.meucci.utils.Errors;
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
        App.print("ok");
    }

    static void nameNotOk()
    {
        App.print("no ok");
        App.print(Errors.NAME_NOT_AVAILABLE);
    }

    static void needName()
    {
        //errore needname
        App.print(Errors.NEED_A_NAME);
        
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
        App.print(Errors.DEST_NOT_CORRECT);
    }

    static void disconnect()
    {

    }

    static void usernameChanged(String oldUsername, String newUsername)
    {
        //cambia l'username dei client
        App.client.userMessagesList.updateUser(oldUsername, newUsername);
    }

    static void commandNotRecognized()
    {
        App.print(Errors.COMMAND_NOT_RECOGNIZED);
    }
}
