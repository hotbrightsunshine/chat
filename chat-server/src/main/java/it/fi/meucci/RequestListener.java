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
        this.father = father;
    }

    /**
     * Implementazione del metodo RUN
     */
    @Override
    public void run() {
        // Sicuramente il client appena connesso non ha un username.
        // Manda la lista di client *ABILITATI* a parlare (sendList())
        // Manda un messaggio missing name 
            // ServerAnnouncement.needNameMessage()
            // Serializza il messaggio ^
            // send(messaggio serializzato in JSON)

        /*
        While non ha un nome (){}
         */

        /*
        SERVER ANNOUNCEMENT con JOINED e il nome del nuovo utente
         */

        // Inizio della procedura "ciclata"
        while(allowedToRun){ // oppure finché il socket non è chiuso
            // Ascolto dei messaggi sul canale
            // Deserializzazione del messaggio
            // Interpretazione del messaggio: spendisco il msg al metodo HANDLE
            
            // IMPORTANTISSIMO! 
            // Creo un nuovo Thread temporaneo per eseguire HANDLE
            // con new Thread(new Runnable(run(){ handle() }));
        }

        // Appena la socket è chiusa, manda un messaggio SERVER ANNOUNCEMENT con LEFT
    }

    /**
     * In base al messaggio ricevuto, ha un comportamento diverso. 
     * @param msg E' il messaggio appena ricevuto
     */
    public void handle(Message msg){
        /*
         * Il messaggio ha diversi tipi.
         * Se è un MESSAGGIO {
         *      Handler.handleMessage(msg);
         * } SE è UN COMANDO {
         *      Handler.handleCommand(msg);
         * } ALTRIMENTI {
         *      Handler.handle(msg);
         * }
         * 
         * // IMPORTANTE
         * Se questi messaggi ritornano delle eccezioni, è importante gestirle!
         */
        switch(msg.getType()){
            case COMMAND:
                try {
                    Handler.handleCommand(msg);
                } catch (HandlerException e) {

                }
                break;
            case MESSAGE:
                try {
                    Handler.handleMessage(msg);
                } catch (HandlerException e) {

                }
                break;
            default:
                try {
                    Handler.handle(msg);
                } catch (HandlerException e) {

                }
                break;
        }
    }

    /**
     * Manda al client un messaggio con dentro la lista degli utenti
     */
    public void sendList(){
        // Lista di utenti autorizzati: Server.getUsername
        // Generazione del messaggio ServerAnnouncement.listAnnouncement(lista di utenti autorizzati)
        // Serializzazione del messaggio
        // send 
    }

    /**
     * Manda un messaggio al client
     *  */ 
    private void send(){

    }

    public boolean isAllowedToRun() {
        return allowedToRun;
    }

    public void setAllowedToRun(boolean allowedToRun) {
        this.allowedToRun = allowedToRun;
    }

    public Server getFather() {
        return father;
    }

    public void setFather(Server father) {
        this.father = father;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
