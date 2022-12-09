package it.meucci.utils;

public enum CommandType {
    CHANGE_NAME,
    DISCONNECT;

    public static CommandType fromString(String str){
        if(str.toUpperCase().equals("CHANGE_NAME")){
            return CHANGE_NAME;
        }
        else

        if(str.toUpperCase().equals("DISCONNECT")){
            return DISCONNECT;
        } else
            return null;
    }
}
