package it.fi.meucci.logger;


import java.util.Calendar;

/**
 * A small class used to standardize system output.
 */
public class Log {
    /**
     * The calendar used to print the current time
     */
    private static final Calendar c = Calendar.getInstance();

    /**
     * Prints a message to the standard output
     * @param type The type {@link it.fi.meucci.logger.LogType}
     * @param message The message to be printed
     */
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
