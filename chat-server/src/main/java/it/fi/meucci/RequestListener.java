package it.fi.meucci;

import it.fi.meucci.utils.Username;

import java.net.Socket;

public class RequestListener implements Runnable
{
    private Server father;
    private Username username;
    private Socket socket;
    private boolean allowed;

    @Override
    public void run() {

    }
}
