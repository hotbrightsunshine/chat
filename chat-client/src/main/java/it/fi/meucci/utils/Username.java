package it.fi.meucci.utils;

public class Username {
    String username;
    private static Username everyone = new Username("everyone");
    private static Username server = new Username("server");
    public Username(String username){
        this.username = username;
    }

    public Username everyone(){
        return Username.everyone;
    }

    public Username server(){
        return Username.server;
    }
}
