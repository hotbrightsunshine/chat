package it.fi.meucci.logger;


import java.util.Calendar;

public class Log {
    private static Calendar c = Calendar.getInstance();

    public static void print(LogType type, String message) {
        System.out.println(
          c.get(Calendar.HOUR_OF_DAY) 
        + ":" 
        + c.get(Calendar.MINUTE) 
        + "."
        + c.get(Calendar.SECOND)
        + " [<" + type.toString() + ">]" + " " + message);      
    }
}
