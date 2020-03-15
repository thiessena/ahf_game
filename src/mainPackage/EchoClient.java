package mainPackage;

import java.awt.Color;

import javax.swing.JTextField;

import clientServer.Client;
import elementFactory.Player;
import graphicsDrawer.LabyrinthDrawer;

/**
 * Aktualisiert die Elemente im ClientLabyrinth.
 * <p>
 * Der EchoClient übergibt das Labyrinth als Array an das ClientLabyrinth 
 * und sendet die Tastenanschläge vom ClientLabyrinth an den EchoServer. 
 * 
 * @author Daniel Garmann
 * @version 21.3.2009
 * @see mainPackage.EchoServer
 */

public class EchoClient extends Client {
	/**
	 * Nachricht, die vom EchoServer übergeben wurde.
	 */
	JTextField mNachricht;
	
	/**
	 * Member des ClientLabyrinths
	 * @see mainPackage.ClientLabyrinth
	 */
	ClientLabyrinth mClientLabyrinth;
	
	/**
	 * Member des LabyrinthDrawers
	 * @see graphicsDrawer.LabyrinthDrawer
	 */
	LabyrinthDrawer mLabyrinthDrawer;

	/**
	 * Initalisiert das ClientLabyrinth und den LabyrinthDrawer. 
	 * 
	 * @see clientServer.Client
	 * @see mainPackage.ClientLabyrinth
	 * @see graphicsDrawer.LabyrinthDrawer
	 */
	public EchoClient(String ip, int p, JTextField jtf) {
		super(ip, p);
		mNachricht = jtf;
		mClientLabyrinth = new ClientLabyrinth(this);
		mLabyrinthDrawer = LabyrinthDrawer.getInstance(null, mClientLabyrinth.getScale());
		send("WHO:");
		send("S:");
	}

	/**
	 * Die Nachricht wird verarbeitet und an die entsprechenden Methoden und Objekte gesendet.
	 * Diese Methode der Server-Klasse wird hiermit überschrieben. 
	 */
	public void processMessage(String pMessage) {
		mNachricht.setText(pMessage);
		String[] nachricht = pMessage.split(":");
		
		switch (nachricht[0]) {
		case "EA":
			mNachricht.setText(nachricht[1]);
			break;
		case "YOU":
			mClientLabyrinth.setAktPlayer(Integer.parseInt(nachricht[1]));
		case "L":
			updateLabyrinth(nachricht[1]);
			break;
		case "P":
			updatePlayer(nachricht[1]);
			break;
		}
		// System.out.println(pMessage);
	}

	/**
	 * Übergibt das Irrgarten aus der Nachricht an das ClientLabyrinth als Array.
	 * Der Irrgarten entstammt der Klasse Labyrinth und wird dort initalisiert. 
	 * Der EchoServer sendet den Irrgarten als Nachricht an den EchoClient.
	 * 
	 * @param pNachricht Zu analysierende Nachricht
	 * @see mainPackage.EchoServer
	 * @see mainPackage.EchoServer#processMessage(String, int, String)
	 */
	public void updateLabyrinth(String pNachricht) {
		String[] zeilen = pNachricht.split("/");
		int[][] nLab = new int[zeilen.length][];
		
		for (int y = 0; y < zeilen.length; y++) {
			String[] spalten = zeilen[y].split(",");
			
			nLab[y] = new int[spalten.length];
			
			for (int x = 0; x < spalten.length; x++) {
				nLab[y][x] = Integer.parseInt(spalten[x]);
			}
		}

		mLabyrinthDrawer.setLabyrinthImage(nLab, mClientLabyrinth.getScale());
		mClientLabyrinth.setLabyrinth(nLab);
		mClientLabyrinth.setImageLab(mLabyrinthDrawer.drawLabyrinth(nLab, mClientLabyrinth.getScale()));
	}

	/**
	 * Erhält die Player vom EchoServer und verändert dementsprechend die Attribute und Positionen der Player.
	 * 
	 * @param pNachricht Zu analysierende Nachricht
	 */
	public void updatePlayer(String pNachricht) {
		String[] p = pNachricht.split("/");
		Player[] nPlayer = new Player[p.length];
		
		for (int i = 0; i < p.length; i++) {
			String[] att = p[i].split(",");
			
			int x = Integer.parseInt(att[0]);
			int y = Integer.parseInt(att[1]);
			
			int r = Integer.parseInt(att[2]);
			int g = Integer.parseInt(att[3]);
			int b = Integer.parseInt(att[4]);
			
			double radius = Double.parseDouble(att[5]);
			String ip = att.length == 7 ? att[2] : "";
			
			nPlayer[i] = new Player(x, y, ip, 0, new Color(r, g, b), radius, mClientLabyrinth.getLabyrinth());
		}
		mClientLabyrinth.setPlayers(nPlayer);
	}
}