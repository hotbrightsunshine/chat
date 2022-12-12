package it.meucci;

import it.meucci.textinterface.MainMenu;
import it.meucci.textinterface.TextInterface;
import it.meucci.utils.Username;

import java.util.ArrayList;

public abstract class HandleMethods {
    static void left(String username)
    {
        App.client.userMessagesList.removeUser(username);
    }

    static void join(String username)
    {
        App.client.userMessagesList.addUser(username);
    }

    static void nameOk()
    {

    }

    static void nameNotOk()
    {

    }

    static void needName()
    {

    }

    static void list(ArrayList<String> usernames)
    {
        App.client.userMessagesList.addUser(Username.everyone);
        for (String username : usernames) {
            join(username);
        }
    }

    static void destNotOk()
    {

    }

    static void disconnect()
    {
        // TODO
    }

    static void usernameChanged(String oldUsername, String newUsername)
    {
        App.client.userMessagesList.updateUser(oldUsername, newUsername);
    }

    static void commandNotRecognized(){}
}
