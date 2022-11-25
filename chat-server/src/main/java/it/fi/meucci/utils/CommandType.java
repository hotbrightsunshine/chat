package it.fi.meucci.utils;

public enum CommandType {
    CHANGE_NAME,
    DISCONNECT;

    public CommandType fromString(String str){
        if(str.equals("CHANGE_NAME")){
            return CHANGE_NAME;
        }
        else if(str.equals("DISCONNECT")){
            return DISCONNECT;
        } else
            return null;
    }
}
