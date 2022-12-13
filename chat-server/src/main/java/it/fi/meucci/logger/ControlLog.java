package it.fi.meucci.logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ControlLog {
    private LogType type;

    public ControlLog(@JsonProperty("type") LogType type)
    {
        
    }

}
