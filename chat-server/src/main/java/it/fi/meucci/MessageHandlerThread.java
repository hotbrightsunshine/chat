package it.fi.meucci;

import it.fi.meucci.utils.Message;

public abstract class MessageHandlerThread implements Runnable{
    private Message msg;

    public MessageHandlerThread(Message msg)
    {
        this.msg = msg;
    }

    public Message getMessage()
    {
        return msg;
    }
    
    @Override
    public abstract void run();
}
