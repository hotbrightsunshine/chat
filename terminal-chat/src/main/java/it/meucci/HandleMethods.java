package it.meucci;

public abstract class HandleMethods {
    static boolean left(String username){
        return App.client.getUsernames().remove(username);
    }

    static boolean join(String username){
        return App.client.getUsernames().add(username);
    }
}
