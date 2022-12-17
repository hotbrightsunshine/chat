package it.meucci.utils;

import java.util.ArrayList;

public enum ServerAnnouncement {
    JOINED, // Un utente entra
    LEFT, // Un utente è uscito
    NAME_OK, // Il nome inserito va bene
    NAME_NOT_OK, // Il nome inserito non va bene
    NEED_NAME, // Il client non ha un nome
    LIST, // Lista dei partecipanti
    DEST_NOT_CORRECT, // Il destinatario del server non è corretto
    DISCONNECT,
    COMMAND_NOT_RECOGNIZED,
    USERNAME_CHANGED
    ;
}
