package src.clientServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Eine Connection ist ein Socket, der eine vereinfachte Ein- und Ausgabe
 * bietet.<br>
 * Die Verbindung ist nicht nebenl&auml;ufig realisiert.<br>
 * Da sie eine Unterklasse von Thread ist, k&ouml;nnen Unterklassen der
 * Verbindung nebenl&auml;ufig arbeiten.
 * 
 * @author Horst Hildebrecht
 * @version 1.0 vom 16.08.2006
 */
public class Connection extends Thread {

	private class PrintlnStream extends PrintStream {
		public PrintlnStream(OutputStream out, boolean autoflush) {
			super(out, autoflush);
		}

		public void println(String s) {
			this.print(s + "\r\n");
		}

		public void println() {
			this.print("\r\n");
		}
	}

	// Objektbeziehungen
	private Socket hatSocket; // Besser w�re eine Unterklasse. Wegen des noetigen super-Aufrufs in try nicht
								// moeglich!
	private BufferedReader hatEingabe;
	private PrintlnStream hatAusgabe;

	// Attribute
	protected boolean zVerbindungAktiv; // Nur f�r Unterklassen, die echte Threads sind (ServerVerbindung)
	private String zPartnerAdresse;
	private int zPartnerPort;

	protected Connection() {
	} // nur aus formalen Gruenden noetig

	/**
	 * Die Verbindung ist mit Ein- und Ausgabestreams initialisiert.
	 * 
	 * @param pSocket    Socket, der die Verbindung beschreibt
	 * @param pTestModus Wenn true, werden bei jeder Operation Meldungen auf der
	 *                   Konsole ausgegeben.
	 */
	protected Connection(Socket pSocket) {
		this.erstelleVerbindung(pSocket);
	}

	/**
	 * Die Verbindung ist mit Ein- und Ausgabestreams initialisiert.
	 * 
	 * @param pIPAdresse IP-Adresse bzw. Domain des Partners
	 * @param pPortNr    Portnummer des Sockets
	 */
	public Connection(String pIPAdresse, int pPortNr) {
		try {
			this.erstelleVerbindung(new Socket(pIPAdresse, pPortNr));
		}

		catch (Exception pFehler) {
			hatSocket = null;
			System.err.println("Fehler beim \u00D6ffnen von Socket: " + pFehler);
		}

	}

	/**
	 * Die Verbindung ist mit Ein- und Ausgabestreams initialisiert.
	 * 
	 * @param pSocket Socket, der die Verbindung beschreibt
	 */
	protected void erstelleVerbindung(Socket pSocket) {
		hatSocket = pSocket;
		zVerbindungAktiv = true;

		try {
			// Den Eingabe- und Ausgabestream holen
			hatEingabe = new BufferedReader(new InputStreamReader(hatSocket.getInputStream()));
			hatAusgabe = new PrintlnStream(hatSocket.getOutputStream(), true);
			zPartnerAdresse = this.verbindungsSocket().getInetAddress().toString();
			zPartnerAdresse = zPartnerAdresse.substring(zPartnerAdresse.indexOf('/') + 1);
			zPartnerPort = this.verbindungsSocket().getPort();

		}

		catch (Exception pFehler) {
			System.err.println("Fehler beim Erzeugen der Streams der Verbindung: " + pFehler);
		}
	}

	public String toString() {
		return "Verbindung mit Socket: " + hatSocket;
	}

	/**
	 * Ein Text wurde in den Ausgabestream geschrieben.
	 * 
	 * @param pNachricht Text, der geschrieben werden soll
	 */
	public void send(String pMessage) {
		try {
			hatAusgabe.println(pMessage);
		}

		catch (Exception pFehler) {
			System.err.println("Fehler beim Schreiben in der Verbindung: " + pFehler);
		}
	}

	/**
	 * Ein Text des Eingabestreams wurde geliefert.
	 */
	public String receive() {
		String lEingabe = null;

		try {
			lEingabe = hatEingabe.readLine();
		}

		catch (Exception pFehler) {
			if (zVerbindungAktiv)
				System.err.println("Fehler beim Lesen in der Verbindung: " + pFehler);
		}

		return lEingabe;
	}

	/**
	 * Die IP-Nummer des Partners wurde geliefert.
	 */
	protected String partnerAdresse() {
		return zPartnerAdresse;
	}

	/**
	 * Der Port des Partners wurde geliefert.
	 */
	protected int partnerPort() {
		return zPartnerPort;
	}

	/**
	 * Der Socket der Verbindung wurde geliefert.
	 */
	protected Socket verbindungsSocket() {
		return hatSocket;
	}

	/**
	 * Die Verbindung wurde mit Ein- und Ausgabestreams geschlossen.
	 */
	public void close() {
		zVerbindungAktiv = false;

		try {
			// Die Streams und den Socket schliessen
			hatEingabe.close();
			hatEingabe = null;
			hatAusgabe.close();
			hatAusgabe = null;
			hatSocket.close();
			hatSocket = null;
		}

		catch (Exception pFehler) {
			System.err.println("Fehler beim Schlie\u00DFen der Verbindung: " + pFehler);
		}
	}

}
