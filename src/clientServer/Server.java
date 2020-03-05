package clientServer;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Ein Server ist ein vereinfachter ServerSocket, der zus&auml;tzliche
 * Funktionen hat.<br>
 * Es k&ouml;nnen beliebig viele Kontakte mit Clientverbindungen aufgebaut
 * werden.<br>
 * Der Dialog mit den Clients wird nebenl&auml;ufig realisiert.
 * 
 * @author Horst Hildebrecht
 * @version 1.0 vom 16.08.06
 */
public abstract class Server {

	// Objekte
	private ServerSocket hatServerSocket;
	private Liste hatVerbindungen;
	private ServerSchleife hatSchleife;

	// Attribute
	private int zPort;

	/**
	 * Verbindung des Servers mit einem Client.<br>
	 * Kann nebenl&auml;ufig die empfangenen Nachrichten bearbeiten.
	 * 
	 * @author Horst Hildebrecht
	 * @version 1.0
	 */
	private class ServerConnection extends Connection {
		// Objekte
		Server kenntServer;

		/*
		 * Die ServerVerbindung wurde inialisiert.
		 * 
		 * @param pSocket Socket, der die Verbindung beschreibt
		 * 
		 * @param pServer Server, den die ServerVerbindung kennen lernt
		 */
		protected ServerConnection(Socket pSocket, Server pServer) {
			this.erstelleVerbindung(pSocket);
			kenntServer = pServer;
		}

		/**
		 * Solange der Client Nachrichten sendete, wurden diese empfangen und an die
		 * Server weitergereicht.<br>
		 * Abgebrochene Verbindungen wurden erkannt.
		 */
		public void run() {
			String lNachricht;

			while (zVerbindungAktiv) {
				lNachricht = this.receive();
				if (lNachricht == null) {
					if (zVerbindungAktiv) {
						kenntServer.closeConnection(this.partnerAdresse(), this.partnerPort());
					}
				} else
					kenntServer.processMessage(this.partnerAdresse(), this.partnerPort(), lNachricht);
			}
		}

	}

	private class ServerSchleife extends Thread {

		private Server kenntServer;

		public ServerSchleife(Server pServer) {
			kenntServer = pServer;
		}

		public void run() {
			while (true) // ewige Schleife
			{
				try {
					Socket lClientSocket = kenntServer.hatServerSocket.accept(); // wartet
					ServerConnection lNeueSerververbindung = new ServerConnection(lClientSocket, kenntServer);
					// Der Client laeuft in einem eigenen Thread, damit mehrere Clients gleichzeitig
					// auf den Server zugreifen koennen.
					kenntServer.ergaenzeVerbindung(lNeueSerververbindung);
					lNeueSerververbindung.start();
				}

				catch (Exception pFehler) {
					System.out.println("Fehler beim Erwarten einer Verbindung in Server: " + pFehler);
				}
			}
		}
	}

	/**
	 * @author Hermann Brak, Horst Hildebrecht
	 * @version 1.0 vom 15.06.2006
	 */
	private class Liste {
		private Knoten hatBug, kenntAktuelles, hatHeck;

		private int zLaenge;

		private class Knoten {
			private Object kenntInhalt;
			private Knoten kenntVorgaenger, kenntNachfolger;

			public Knoten(Object pInhalt, Knoten pVorgaenger, Knoten pNachfolger) {
				kenntInhalt = pInhalt;
				kenntVorgaenger = pVorgaenger;
				kenntNachfolger = pNachfolger;
			}

			public void setzeInhalt(Object pInhalt) {
				kenntInhalt = pInhalt;
			}

			public Object inhalt() {
				return kenntInhalt;
			}

			public void setzeNachfolger(Knoten pNachfolger) {
				kenntNachfolger = pNachfolger;
			}

			public Knoten nachfolger() {
				return kenntNachfolger;
			}

			public void setzeVorgaenger(Knoten pVorgaenger) {
				kenntVorgaenger = pVorgaenger;
			}

			public Knoten vorgaenger() {
				return kenntVorgaenger;
			}
		}

		public Liste() {
			hatBug = new Knoten(null, null, null);
			hatHeck = new Knoten(null, hatBug, null);
			kenntAktuelles = hatBug;
			hatBug.setzeNachfolger(hatHeck);
			zLaenge = 0;
		}

		public boolean istLeer() {
			return hatBug.nachfolger() == hatHeck;
		}

		public boolean istDahinter() {
			return kenntAktuelles == hatHeck;
		}

		public void vor() {
			if (!this.istDahinter())
				kenntAktuelles = kenntAktuelles.nachfolger();
		}

		public void zumAnfang() {
			kenntAktuelles = hatBug.nachfolger();
		}

		public Object aktuelles() {
			return kenntAktuelles.inhalt();
		}

		public void haengeAn(Object pInhalt) {
			Knoten lNeuknoten = new Knoten(pInhalt, hatHeck.vorgaenger(), hatHeck);
			hatHeck.vorgaenger().setzeNachfolger(lNeuknoten);
			hatHeck.setzeVorgaenger(lNeuknoten);
			zLaenge++;
		}

		public void entferneAktuelles() {
			if (!(this.istDahinter())) {
				this.verketteKnoten(kenntAktuelles.vorgaenger(), kenntAktuelles.nachfolger());
				kenntAktuelles = kenntAktuelles.nachfolger();
				zLaenge--;
			}
		}

		private void verketteKnoten(Knoten pLinksknoten, Knoten pRechtsknoten) {
			pLinksknoten.setzeNachfolger(pRechtsknoten);
			pRechtsknoten.setzeVorgaenger(pLinksknoten);
		}

	}

	/**
	 * Der Server ist initialisiert.
	 * 
	 * @param pPortNr Portnummer des Sockets
	 */
	public Server(int pPortNr) {
		try {
			// Socket oeffnen
			hatServerSocket = new ServerSocket(pPortNr);
			zPort = pPortNr;
			hatVerbindungen = new Liste();
			hatSchleife = new ServerSchleife(this);
			// System.out.println("Starte die Schleife");
			hatSchleife.start();
		}

		catch (Exception pFehler) {
			System.out.println("Fehler beim \u00D6ffnen der Server: " + pFehler);
		}
	}

	public String toString() {
		return "Server von ServerSocket: " + hatServerSocket;
	}

	private void ergaenzeVerbindung(ServerConnection pVerbindung) {
		hatVerbindungen.haengeAn(pVerbindung);
		this.processNewConnection(pVerbindung.partnerAdresse(), pVerbindung.partnerPort());
	}

	/**
	 * Liefert die Serververbindung der angegebenen IP mit dem angegebenen Port,
	 * null falls nicht vorhanden.
	 * 
	 * @param pClientIP   IP-Nummer des Clients der gesuchten Verbindung
	 * @param pClientPort Port-Nummer des Clients der gesuchten Verbindung
	 */
	private ServerConnection SerververbindungVonIPUndPort(String pClientIP, int pClientPort) {
		ServerConnection lSerververbindung;

		hatVerbindungen.zumAnfang();

		while (!hatVerbindungen.istDahinter()) {
			lSerververbindung = (ServerConnection) hatVerbindungen.aktuelles();
			if (lSerververbindung.partnerAdresse().equals(pClientIP) && lSerververbindung.partnerPort() == pClientPort)
				return lSerververbindung;
			hatVerbindungen.vor();
		}

		return null; // IP nicht gefunden
	}

	/**
	 * Eine Nachricht wurde an einen Client geschickt.
	 * 
	 * @param pClientIP   IP-Nummer des Empf&auml;ngers
	 * @param pClientPort Port-Nummer des Empf&auml;ngers
	 * @param pMessage    die verschickte Nachricht
	 */
	public void send(String pClientIP, int pClientPort, String pMessage) {
		ServerConnection lSerververbindung = this.SerververbindungVonIPUndPort(pClientIP, pClientPort);
		if (lSerververbindung != null)
			lSerververbindung.send(pMessage);
		else
			System.err
					.println("Fehler beim Senden: IP " + pClientIP + " mit Port " + pClientPort + " nicht vorhanden.");
	}

	/**
	 * Eine Nachricht wurde an alle verbundenen Clients geschickt.
	 * 
	 * @param pMessage die verschickte Nachricht
	 */
	public void sendToAll(String pMessage) {
		ServerConnection lSerververbindung;

		hatVerbindungen.zumAnfang();

		while (!hatVerbindungen.istDahinter()) {
			lSerververbindung = (ServerConnection) hatVerbindungen.aktuelles();
			lSerververbindung.send(pMessage);
			hatVerbindungen.vor();
		}
	}

	/**
	 * Die Verbindung mit der angegebenen IP und dem angegebenen Port wurde
	 * geschlossen.<br>
	 * 
	 * @param pClientIP   IP-Nummer des Clients der zu beendenden Verbindung
	 * @param pClientPort Port-Nummer des Clients der zu beendenden Verbindung
	 */
	public void closeConnection(String pClientIP, int pClientPort) {

		ServerConnection lSerververbindung = this.SerververbindungVonIPUndPort(pClientIP, pClientPort);
		if (lSerververbindung != null) {
			this.loescheVerbindung(lSerververbindung);
			lSerververbindung.close();
			System.out.println("Verbindung geschlossen");
			this.processClosedConnection(pClientIP, pClientPort);
		} else
			System.err.println("Fehler beim Schlie\u00DFen der Verbindung: IP " + pClientIP + " mit Port " + pClientPort
					+ " nicht vorhanden.");

	}

	/**
	 * Eine Verbindung wurde aus der Empf&auml;ngerliste gel&ouml;scht.
	 * 
	 * @param pVerbindung die zu l&ouml;schende Verbindung
	 */
	private void loescheVerbindung(ServerConnection pVerbindung) {
		hatVerbindungen.zumAnfang();

		while (!hatVerbindungen.istDahinter()) {
			ServerConnection lClient = (ServerConnection) hatVerbindungen.aktuelles();
			if (lClient == pVerbindung)
				hatVerbindungen.entferneAktuelles();
			hatVerbindungen.vor();
		}
	}

	/**
	 * Ein neuer Client hat sich angemeldet.<br>
	 * Diese leere Methode kann in einer Unterklasse realisiert werden
	 * (Begr&uuml;&szlig;ung).
	 * 
	 * @param pClientIP   IP-Nummer des Clients, der neu angemeldet ist
	 * @param pClientPort Port-Nummer des Clients, der neu angemeldet ist
	 */
	public void processNewConnection(String pClientIP, int pClientPort) {
	}

	/**
	 * Eine Nachricht von einem Client wurde bearbeitet.<br>
	 * Diese leere Methode sollte in Unterklassen &uuml;berschrieben werden.
	 * 
	 * @param pClientIP   IP-Nummer des Clients, der die Nachricht geschickt hat
	 * @param pClientPort Port-Nummer des Clients, der die Nachricht geschickt hat
	 * @param pMessage    Die empfangene Nachricht, die bearbeitet werden soll
	 */
	public void processMessage(String pClientIP, int pClientPort, String pMessage) {
	}

	/**
	 * Die Verbindung mit einem Client wurde beendet oder verloren.<br>
	 * Diese leere Methode kann in einer Unterklasse realisiert werden.
	 * 
	 * @param pClientIP   IP-Nummer des Clients, mit dem die Verbindung beendet
	 *                    wurde
	 * @param pClientPort Port-Nummer des Clients, mit dem die Verbindung beendet
	 *                    wurde
	 */
	public void processClosedConnection(String pClientIP, int pClientPort) {
	}

	/**
	 * Der Server wurde geschlossen.
	 */
	public void close() {
		try {
			hatServerSocket.close();
			hatServerSocket = null;
		}

		catch (Exception pFehler) {
			System.err.println("Fehler beim Schlie\u00DFen des Servers: " + pFehler);
		}

	}

}
