package it.fi.meucci;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
    private ArrayList <RequestListener> connected = new ArrayList<>();
    private static ServerSocket serverSocket;

    public void Comunica()
    {
        try
        {
            serverSocket = new ServerSocket(7777);

            for(;;)
            {
                System.out.println("server waiting for connection");
                Socket socket = serverSocket.accept();
                System.out.println("server socket" + socket);
                RequestListener serverThread = new RequestListener(socket);
                Thread thread = new Thread(serverThread);
                connected.add(serverThread);
                thread.start();
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
