package it.meucci.textinterface;

public enum CommandType {
    HELP,
    WELCOME,
    DISCONNECT,
    PREV,
    NEXT,
    INVALID;

    public CommandType parse(String type){
        type = type.replace(" ", "");
        type = type.toUpperCase();
        switch(type){
            case "HELP":
                return HELP;
            case "WELCOME":
                return WELCOME;
            case "DISCONNECT":
                return DISCONNECT;
            case "PREV":
                return PREV;
            case "NEXT":
                return NEXT;
            default:
                return INVALID;
        }
    }
}
