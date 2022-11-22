package it.fi.meucci;

import it.fi.meucci.utils.Message;

/**
 * -Classe deputata alla lettura dell'output del socket
 * -Gestisce ogni messaggio inviato
 */
public class ReplyListener implements Runnable {
    private Client father;

    public ReplyListener(Client father){
        this.father = father;
    }

    public void run(){
       //while client.stop == false fai handle
       // sennò richiami metodo close()
    }
/** 
 * -Riceve messaggi dal server e li gestisce
 * -Controlla il tipo di messaggio ed in base al messaggio si comporta in modo diverso
*/
    public void handle(Message message)
    {
        //controllo il tipo di messaggio inviato se è un messaggio per l'utente o se è un SERVER_ANN
        //ulteriore controllo sul tipo di SERVER_ANN
        //stampa il messaggio
    }
/**
 * Chiude la lettura dell'output del socket
 */
    public void close(){
        //chiusura socket
    }
}
