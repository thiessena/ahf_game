package mainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import graphicsDrawer.LabyrinthDrawer;

/**
 * Write a description of class Labyrinth here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class MiniLab extends Fenster {
	int[][] lab;

	int x;
	int y;

	int scl = 10;
	private Player[] player;
	private LabyrinthDrawer dl;
	private LabyrinthClass l;
	
	BufferedImage imgLab = null;

	/**
	 * Constructor for objects of class Labyrinth
	 */
	public MiniLab() {
		x = 1;
		y = 1;
		player = new Player[8];
		player[0] = new Player(1, 1, "", 1000, new Color(255, 0, 0), 1, lab);
		player[1] = new Player(19, 1, "", 1000, new Color(0, 255, 0), 1, lab);
		player[2] = new Player(19, 19, "", 1000, new Color(0, 0, 255), 1, lab);
		player[3] = new Player(1, 19, "", 1000, new Color(0, 0, 0), 1, lab);
		player[4] = new Player(1, 10, "", 1000, new Color(255, 155, 0), 1, lab);
		player[5] = new Player(10, 19, "", 1000, new Color(0, 255, 155), 1, lab);
		player[6] = new Player(19, 10, "", 1000, new Color(155, 255, 0), 1, lab);
		player[7] = new Player(10, 1, "", 1000, new Color(0, 155, 255), 1, lab);
		
		l = new LabyrinthClass();
		lab = l.getLab();
				
		dl = new LabyrinthDrawer();
		imgLab = dl.drawLabyrinth(lab, scl);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((lab[0].length + 1) * scl, (lab.length + 4) * scl);
		this.setLocation((int) (screenSize.getWidth() * 0.25 - this.getWidth() * 0.5),
						 (int) (screenSize.getHeight() * 0.5 - this.getHeight() * 0.5));
		this.setResizable(false);
	}

	public void draw(Graphics g) {
		g.drawImage(imgLab, 0, 0, null);

		if (lab != null) {
			for (int i = 1; i < player.length; i++) {
				player[i].draw(g);
			}
			player[0].drawCurrentPlayer(g);
		}
	}

	public String playerToString() {
		String pString = "P:";
		for (int i = 0; i < player.length - 1; i++) {
			pString += player[i].toString() + "/";
		}
		pString += player[player.length - 1].toString();
		return pString;
	}

	public void keyPressed(KeyEvent e) {
		// System.out.println(e.getKeyCode());
		switch (e.getKeyCode()) {
		case 37:
			player[0].move(0);
			break;
		case 38:
			player[0].move(1);
			break;
		case 39:
			player[0].move(2);
			break;
		case 40:
			player[0].move(3);
			break;
		}
	}

	public void ausgabe() {
		System.out.println('\u000C'); // Konsole leeren
		l.printLabyrinth(lab);
		System.out.println("x:" + x + " y:" + y);
	}

	public Player getPlayer(String pIP, int pPort) {
		Player pPlayer = null;
		for (int i = 0; i < player.length; i++) {
			if (player[i].getClientIP() == pIP && player[i].getClientPort() == pPort) {
				pPlayer = player[i];
			}
		}
		return pPlayer;
	}

	public int getPlayerPos(String pIP, int pPort) {
		int pPlayer = -1;
		for (int i = 0; i < player.length; i++) {
			if (player[i].getClientIP() == pIP && player[i].getClientPort() == pPort) {
				pPlayer = i;
			}
		}
		return pPlayer;
	}

	public int[][] getLab() {
		return lab;
	}
	
	public String toString(int[][] lab) {
		String lString = "";
		
		for (int y = 0; y < lab.length; y++) {
			for (int x = 0; x < lab[y].length; x++) {
				lString += lab[y][x];
				
				if (x != lab.length - 1) {
					lString += ",";
				}
			}
			
			if (y != lab.length - 1) {
				lString += "/";
			}
		}
		return "L:" + lString;
	}

	public void neuerUser(String pIP, int pPort) {
		boolean empty = false;
		for (int i = 0; i < player.length; i++) {
			if (player[i].getClientIP().equals("")) {
				player[i].setIPPort(pIP, pPort);
				break;
			}
		}
	}

	
}
