package it.fi.meucci.logger;

/**
 * This enum describes the type of the log.
 * It can be a WARNING, an INFO, or an ERROR.
 */
public enum LogType {
    /**
     * A Warning is a moderately serious problem
     */
    WARNING,
    /**
     * An information; it's used to inform the user about something.
     */
    INFO,
    /**
     * An error probably occurred and must be fixed.
     */
    ERROR,
}
