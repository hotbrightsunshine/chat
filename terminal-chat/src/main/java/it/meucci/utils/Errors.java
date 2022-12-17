package it.meucci.utils;

public enum Errors {
    COMMAND_NOT_RECOGNIZED,
    WRONG_ARGS;

    public static String humanizeError(Errors e) {
        switch(e) {
            case COMMAND_NOT_RECOGNIZED:
                return "[WARNING]The command is not recognized";
            case WRONG_ARGS:
                return "[WARNING]The command arguments are incorrect";
            default:
                break;}
                return null;
    }

}

