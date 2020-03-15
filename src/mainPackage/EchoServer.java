package mainPackage;
import clientServer.Server;

/**
 * Sendet den Irrgarten und die Orte der Player an den EchoClient.
 * 
 * @author Daniel Garmann
 * @version 21.03.2009
 * @see mainPackage.EchoClient
 */

public class EchoServer extends Server {
    
	/**
	 * Member des Labyrinths
	 * @see mainPackage.Labyrinth 
	 */
	private Labyrinth mLabyrinth;

    /**
     * Initialisiert die Klasse Labyrinth als globale Member-Variable
     * 
     * @see clientServer.Server
     */
    public EchoServer(int p) {
        super(p);
        mLabyrinth = new Labyrinth();
    }

    /**
     * Der angemeldete Client bekommt die Meldung, dass er angenommen wurde.
     * Diese Methode der Server-Klasse wird hiermit überschrieben.
     */
    public void processNewConnection(String pClientIP, int pClientPort){
        this.send(pClientIP, pClientPort, "EA: EA Sports, It's in the game!");
        mLabyrinth.neuerUser(pClientIP, pClientPort);
    }

    /**
     * Der angemeldete Client bekommt die gesendete Meldung zurückgeschickt.
     * Diese Methode der Server-Klasse wird hiermit überschrieben.
     */
    public void processMessage(String pClientIP, int pClientPort, String pMessage){
        String[] nachricht = pMessage.split(":");
        switch(nachricht[0]){
            case "S":
                 this.send(pClientIP, pClientPort, mLabyrinth.toString());
                 this.send(pClientIP, pClientPort, mLabyrinth.playerToString());
                 break;
            case "WHO":
                 this.send(pClientIP, pClientPort, "YOU:" + mLabyrinth.getPlayerPos(pClientIP, pClientPort));
                 break;
            default:
             int z = Integer.parseInt(pMessage);
             Player p = mLabyrinth.getPlayer(pClientIP, pClientPort);
             if(p != null){
                 p.move(z);   
             }
             this.sendToAll(mLabyrinth.playerToString());
        }
    }

    /**
     * Die Verbindung wird beendet und aus der Liste der Clients gestrichen.
     * Diese Methode der Server-Klasse wird hiermit überschrieben.
     */
    public void processCloseConnection(String pClientIP, int pClientPort){
        this.closeConnection(pClientIP, pClientPort);
    }
}
