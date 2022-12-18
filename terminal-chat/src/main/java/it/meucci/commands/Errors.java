package it.meucci.commands;

/**
 * There errors are the ones the program issue when the user type an error wrongly.
 */
public enum Errors {
    /**
     * The error is not recognized amoung {@link CommandType}
     */
    COMMAND_NOT_RECOGNIZED,
    /**
     * The error is recognized but the right arguments were not provided
     */
    WRONG_ARGS;

    /**
     * As per Messages, errors also needs to be displayed correctly.
     * For the same reasons as messages, a toString implementation was not used
     * because we wanted to reserve it for debug purpuses.
     * @param e The error to be displayed
     * @return A ready to be displayed string
     */
    public static String humanizeError(Errors e) {
        switch(e) {
            case COMMAND_NOT_RECOGNIZED:
                return "[WARNING] The command is not recognized.";
            case WRONG_ARGS:
                return "[WARNING] The command arguments are incorrect.";
            default:
                return "[WARNING] An error occurred.";
        }
    }

}

