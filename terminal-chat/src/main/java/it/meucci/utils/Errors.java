package it.meucci.utils;

public enum Errors {
    COMMAND_NOT_RECOGNIZED,
    UNABLE_TO_CONNECT,
    WRONG_ARGS,
    ALREADY_CONNECTED,
    NOT_CONNECTED_YET,
    NAME_NOT_AVAILABLE,
    NEED_A_NAME,
    DEST_NOT_CORRECT;


    public static String humanizeError(Errors e) {
        switch(e) {
            case COMMAND_NOT_RECOGNIZED:
                return "[WARNING]The comand is not recognized";
            case WRONG_ARGS:
                return "[WARNING]The command arguments are incorrect";
            default:
                break;}
                return null;
    }

}

