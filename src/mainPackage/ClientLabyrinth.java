package src.mainPackage;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import src.elementFactory.Items;
import src.elementFactory.Player;
import src.graphicsDrawer.CorporateDesign;
import src.graphicsDrawer.DialogDrawer;
import src.graphicsDrawer.LabyrinthDrawer;

/**
 * Hauptfenster des AHF Simulators.
 * <p>
 * Das ClientLabyrinth erbt von der Klasse Fenster und öffnet das Hauptfenster,
 * in welchem der aktuelle Spieler den AHFSimulator spielen kann.
 * 
 * @author Andreas Thiessen
 * @author Jonas Schweizer
 * @version 27.01.2020
 */
public class ClientLabyrinth extends Fenster {
	/**
	 * Array des Irrgartens
	 */
	private int[][] mLabyrinth;
	
	/**
	 * Bild des Irrgartens
	 */
	private BufferedImage mImageLab = null;
	
	/**
	 * Scalewert / Größeneinheit des ClientLabyrinths (<code>1 px = 1 scl</code>) 
	 * @see graphicsDrawer.CorporateDesign#CLIENT_SCALE
	 * @see #getScale()
	 * @see #setScale(int)
	 */
	private int mScale = CorporateDesign.CLIENT_SCALE;
	
	/**
	 * Wert des aktuellen Client Players
	 */
	private int aktPlayer = -1;
	
	/**
	 * Array aller Player
	 * @see elementFactory.Player
	 */
	private Player[] mPlayer;
	
	/**
	 * Member des EchoClients
	 * @see mainPackage.EchoClient
	 */
	private EchoClient mEchoClient;
	
	/**
	 * Member des LabyrinthDrawers
	 * @see graphicsDrawer.LabyrinthDrawer
	 */
	private LabyrinthDrawer mLabyrinthDrawer;
	
	/**
	 * Member des DialogDrawers
	 * @see graphicsDrawer.DialogDrawer
	 */
	private DialogDrawer mDialogDrawer;

	/**
	 * <code>x</code>-Koordinate des aktuellen Players, versetzt um die Hälfte der Fenstergröße.
	 */
	private int newX = 0;
	
	/**
	 * <code>y</code>-Koordinate des aktuellen Players, versetzt um die Hälfte der Fenstergröße.
	 */
	private int newY = 0;

	/**
	 * Breite des ClientLabyrinths (<code>80%</code> der Gesamtbreite)
	 */
	private int mLabWidth = 0;
	
	/**
	 * Höhe des ClientLabyrinths (<code>80%</code> der Gesamthöhe)
	 */
	private int mLabHeight = 0;
	
	/**
	 * Wahrheitswert, ob aktuell ein Dialogfenster geöffnet ist.
	 */
	private boolean isDialogActive = false;
	

	/**
	 * Konstruktor des ClientLabyrinths legt die Größe des Fensters fest.
	 * 
	 * @param pC Der EchoClient, von dem aus das Labyrinth und die Player gesendet werden
	 */
	public ClientLabyrinth(EchoClient pC) {
		mEchoClient = pC;

		this.setTitle("AHF Simulator");
		this.setSize(15 * mScale, 10 * mScale);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		mLabWidth = (int) (this.getWidth() - 0.2 * this.getWidth());
		mLabHeight = (int) (this.getHeight() - 0.2 * this.getHeight());

		mLabyrinthDrawer = LabyrinthDrawer.getInstance(mLabyrinth, mScale);
		mDialogDrawer = new DialogDrawer(this);
	}

	/**
	 * Aktualisiert den Irrgarten und bewegt diesen mit dem aktuellen Player mit.
	 * Die Methode prüft außerdem, ob der aktuelle Player auf einem Item steht
	 * und handelt dementsprechend.
	 */
	public void draw(Graphics g) {
		if (mPlayer != null && mLabyrinth != null && mImageLab != null) {
			
			newX = -mPlayer[0].getX() * mScale + mLabWidth / 2;
			newY = -mPlayer[0].getY() * mScale + mLabHeight / 2;

			g.drawImage(mImageLab, newX, newY, null);

			drawPlayer(g, mPlayer);
			
			if (isDialogActive && getItemAtPlace() != null) {
				mDialogDrawer.drawItemDialog(g, Items.toString(getItemAtPlace()), "Ein Schlüssel am Platz!");
			}
			

//			if (getAktPlayer().itemAtPlace(items.KEY)) {
////				String text = "Öffne im A-Gebäude den Safe und sichere die geheimen Unterlagen!";
////				mDialogDrawer.drawItemDialog(g, "Schlüssel", text);
////				removeItem();
//				
//			} else if (getAktPlayer().itemAtPlace(items.BUCKET)) {
//				String text = "Begib dich ins N-Gebäude und putze alle Toiletten!";
////				mDialogDrawer.drawItemDialog(g, "Putzeimer", text);
//				removeItem();
//				
//			} else if (getAktPlayer().itemAtPlace(items.BRUSH)) {
//				String text = "Suche dir einen zweiten Mitspieler und bemalt zusammen im Kunstraum die weißen Wände!";
//				mDialogDrawer.drawItemDialog(g, "Pinsel", text);
//				
//			} else if (getAktPlayer().itemAtPlace(items.CALCULATOR)) {
//				String text = "Berechne die folgende Aufgabe: \n5 * 3 - 6 / 2";
//				mDialogDrawer.drawChatDialog(g, "Hey! Was soll ich jetzt tun?", text);
//			}
		}
	}
	
	/**
	 * Entfernt das Item, auf dem der aktuelle Player steht.
	 * <p>Der Irrgarten {@link mLabyrinth} wird an dieser Stelle, wo der aktuelle Player gerade steht, 
	 * auf 0 zurückgesetzt und das Labyrinth wird neu gezeichnet.
	 */
	private void removeItem() {
		mLabyrinth[getAktPlayer().getY()][getAktPlayer().getX()] = 0;
		mImageLab = mLabyrinthDrawer.drawLabyrinth(mLabyrinth, mScale);
	}
	
	private Items getItemAtPlace() {
		return getAktPlayer().itemAtPlace();
	}

	/**
	 * Malt alle Player des Arrays <code>player</code> in das ClientLabyrinth.
	 * <p>
	 * Die Positionen der Player werden automatisch aktualisiert.
	 * 
	 * @param g      Graphics, auf dem die Player gezeichnet werden sollen
	 * @param player Array von Playern, die zu zeichnen sind
	 */
	private void drawPlayer(Graphics g, Player[] player) {
		for (int i = 0; i < player.length; i++) {
			if (i == 0) {
				player[i].drawClient(g, (player[i].getX() - player[0].getX()) * mScale + mLabWidth / 2,
										(player[i].getY() - player[0].getY()) * mScale + mLabHeight / 2);
			} else {
				player[i].drawClient(g, (player[i].getX() - player[0].getX()) * mScale + mLabWidth / 2,
										(player[i].getY() - player[0].getY()) * mScale + mLabHeight / 2);
			}
		}
	}

	/**
	 * Gibt die Größeneinheit des ClientLabyrinths zurück
	 * 
	 * @return Scalewert des ClientLabyrinths
	 * @see #setScale(int)
	 */
	public int getScale() {
		return mScale;
	}

	/**
	 * Setzt den Scalewert des ClientLabyrinths (<code>1 px = 1 scl</code>)
	 * 
	 * @param scl Neuer Scalewert des ClientLabyrinths
	 * @see #getScale()
	 */
	public void setScale(int scl) {
		this.mScale = scl;
	}

	/**
	 * Setzt den zu durchlaufenen Irrgarten
	 * 
	 * @param pLab Multidimensionaler Array des Irrgartens
	 */
	public void setLabyrinth(int[][] pLab) {
		mLabyrinth = pLab;

//		printLabyrinth(lab, true);
	}

	/**
	 * Setzt das vorgerenderte Bild des Irrgartens, um die Performance zu verbessern.
	 * <p>
	 * So muss nicht jedes Mal bei einem Refresh der draw-Methode das Labyrinth neu gezeichnet werden.
	 * 
	 * @param pImageLab BufferedImage, welches das aktuelle Bild des Irrgartens enthält
	 */
	public void setImageLab(BufferedImage pImageLab) {
		mImageLab = pImageLab;
	}

	/**
	 * Setzt die Player, die im Labyrinth mitspielen.
	 * 
	 * @param pPlayer Liste von Playern, die mitspielen sollen
	 */
	public void setPlayers(Player[] pPlayer) {
		mPlayer = pPlayer;
	}

	/**
	 * Gibt den Irrgarten zurück
	 * 
	 * @return Multidimensionaler Array mit dem Irrgarten
	 */
	public int[][] getLabyrinth() {
		return mLabyrinth;
	}

	/**
	 * Setzt den Player mit dem Wert <code> i </code> als aktuellen Player, mit dem Spieler spielt.
	 * 
	 * @param i Stelle des zu setzenden aktuellen Players im Array <code>player</code>
	 */
	public void setAktPlayer(int i) {
		aktPlayer = i;
	}

	/**
	 * Gibt den aktuellen Player zurück, mit dem der Client spielt.
	 * 
	 * @return aktueller Player, wenn vorhanden. 
	 * @return null, wenn {@link #aktPlayer} kleiner als 0 ist.
	 */
	public Player getAktPlayer() {
		if (aktPlayer >= 0)
			return mPlayer[aktPlayer];
		else
			return null;
	}
	
	/**
	 * Führt Aktionen aus, wenn ein Player auf einem Item steht und, wenn bestimmte Tasten gedrückt wurden.
	 * <p>
	 * Die Methode wird in {@link #keyPressed(KeyEvent)} aufgerufen und erhält von dort auch das {@link KeyEvent}.
	 * <p>
	 * Tastenbelegung und entsprechende Aktionen:
	 * <blockquote><table border=1 cellpadding=4 cellspacing=0 summary="Tastaturbelegung und entsprechende Aktionen:">
	 * <col width="55">
	 * <tr>
	 * 	  <th align="center">Taste</th>
	 * 	  <th align="center">Aktion</th>
	 * </tr>
	 * <tr>
	 * 	  <td align="center"><code>E</code></td>
	 *    <td>Element bzw. Item einsammeln</td>
	 * </tr>
	 * <tr>
	 *    <td align="center"><code>M</code></td>
	 *    <td>MiniMap anzeigen / ausblenden<br></td>
	 * </tr>
	 * <tr>
	 *    <td align="center"><code>V</code></td>
	 *    <td>Inventar bzw. eingesammelte Items anzeigen / ausblenden</td>
	 * </tr>
	 * <tr>
	 *    <td align="center"><code>SPACE</code><br></td>
	 *    <td>Dialog bzw. Text skippen und weiterschalten</td>
	 * </tr>
	 * <tr>
	 *    <td align="center"><code>ESC</code></td>
	 *    <td>Dialog schließen</td>
	 * </tr>
	 * </table>
	 * </blockquote>
	 * 
	 * @param e KeyEvent, welches die Tastenanschläge übergibt. 
	 * 			Wird in der Methode {@link #keyPressed(KeyEvent)} aufgerufen.
	 * 
	 * @see #keyPressed(KeyEvent)
	 */
	private void interact(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			isDialogActive = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			removeItem();
			isDialogActive = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			isDialogActive = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			isDialogActive = false;
		}
	}

	/**
	 * Prüft, welche Taste gedrückt wurde und sendet eine entsprechende Meldung an
	 * den EchoServer. 
	 * <p>
	 * Bezieht sich hauptsächlich auf die Bewegung des aktuellen Players.
	 * 
	 * @param e KeyEvent, welches die Tastenanschläge übergibt. 
	 * 
	 * @see mainPackage.EchoServer
	 * @see mainPackage.EchoClient
	 */
	public void keyPressed(KeyEvent e) {
		// System.out.println(e.getKeyCode());
		Player p = getAktPlayer();
		interact(e);
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			mEchoClient.send("0");
			isDialogActive = false;
			break;
		case KeyEvent.VK_UP:
			mEchoClient.send("1");
			isDialogActive = false;
			break;
		case KeyEvent.VK_RIGHT:
			mEchoClient.send("2");
			isDialogActive = false;
			break;
		case KeyEvent.VK_DOWN:
			mEchoClient.send("3");
			isDialogActive = false;
			break;
		}
	}
}
