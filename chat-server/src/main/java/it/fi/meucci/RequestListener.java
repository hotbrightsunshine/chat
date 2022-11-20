package it.fi.meucci;

import it.fi.meucci.utils.Username;

import java.net.Socket;

public class RequestListener implements Runnable
{
    private Server father;
    private Username username;
    private Socket socket;
    private boolean allowed;

    public RequestListener(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
