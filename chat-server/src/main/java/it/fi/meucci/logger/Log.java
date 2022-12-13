package it.fi.meucci.logger;


public class Log {
    public static void print(LogType type, String message)
    {
        System.out.println(type.toString() + ": " + message);      
    }
}
