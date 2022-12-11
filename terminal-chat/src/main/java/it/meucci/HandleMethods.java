package it.meucci;

public abstract class HandleMethods {

    private static String stampa;

    static boolean left(String username)
    {
        return App.client.getUsernames().remove(username);
    }

    static boolean join(String username)
    {
        return App.client.getUsernames().add(username);
    }

    static void name(String username)
    {
        if (App.client.getUsernames().equals(username))
        {
            System.out.println("username gia presente");
        }
        else
        {
            App.client.getUsernames().add(username);
        }
    }

    static void needName(String username)
    {
        if (App.client.getUsernames().equals(null)) 
        {
            System.out.println("inserire username");
        }
    }

    static String list()
    {
        for (int i = 0; i < App.client.getUsernames().size(); i++)
        {
            stampa = App.client.getUsernames().get(i);
        }
        
        return stampa;
    }

    static void desNotOk()
    {

    }

    static void disconnect()
    {
        App.client.getUsernames();
    }

    static void usernameChanged(String newUsername)
    {
        
        
    }
}
