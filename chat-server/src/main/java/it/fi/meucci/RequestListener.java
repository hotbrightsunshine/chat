package it.fi.meucci;

import it.fi.meucci.utils.Message;
import it.fi.meucci.utils.Username;

import java.net.Socket;

public class RequestListener implements Runnable
{
    // PADRE
    private Server father;

    // L'username (Può essere nullable)
    private Username username;

    // La connessione con il client
    private Socket socket;

    // Dice se il thread può andare oppure no
    private boolean allowedToRun;

    /**
     * Costruttore di RequestListener
     * @param socket Il socket
     * @param father Il server madre
     */
    public RequestListener(Server father, Socket socket)
    {
        this.socket = socket;
        allowedToRun = true;
    }

    /**
     * 
     */
    @Override
    public void run() {
        // Sicuramente il client appena connesso non ha un username.
        // Manda la lista di client *ABILITATI* a parlare (sendList())
        // Manda un messaggio missing name 
            // ServerAnnouncement.needNameMessage()
            // Serializza il messaggio ^
            // send(messaggio serializzato in JSON)

        
        // Inizio della procedura "ciclata"
        while(allowedToRun){
            // Ascolto dei messaggi sul canale
            // Deserializzazione del messaggio
            // Interpretazione del messaggio: spendisco il msg al metodo HANDLE
            
        }
    }

    /**
     * In base al messaggio ricevuto, ha un comportamento diverso. 
     * @param msg E' il messaggio appena ricevuto
     */
    public void handle(Message msg){

    }

    /**
     * Manda al client un messaggio con dentro la lista degli utenti
     */
    public void sendList(){
        // Lista di utenti autorizzati: Server.getUsername
        // Generazione del messaggio ServerAnnouncement.listMessage(lista di utenti autorizzati)
        // Serializzazione del messaggio
        // send 
    }

    /**
     * Manda un messaggio al client
     *  */ 
    public void send(){

    }

    public boolean isAllowedToRun() {
        return allowedToRun;
    }

    public void setAllowedToRun(boolean allowedToRun) {
        this.allowedToRun = allowedToRun;
    }

    
}
