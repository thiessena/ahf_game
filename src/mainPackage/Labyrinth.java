package mainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import elementFactory.Player;
import graphicsDrawer.CorporateDesign;
import graphicsDrawer.LabyrinthDrawer;

/**
 * Write a description of class Labyrinth here.
 * 
 * @author Andreas Thiessen
 * @author Jonas Schweizer
 * @version 27.01.2020
 */
public class Labyrinth extends Fenster {
	int[][] mLabyrinth = 
			{ { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 0
			{ 1, 0, 0, 2, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 }, // 1
			{ 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1 }, // 2
			{ 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1 }, // 3
			{ 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1 }, // 4
			{ 1, 3, 1, 1, 1, 0, 1, 0, 0, 0, 4, 0, 0, 1, 0, 0, 0, 0, 0, 2, 1 }, // 5
			{ 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1 }, // 6
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1 }, // 7
			{ 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 5, 1, 0, 0, 0, 1, 1, 0, 1 }, // 8
			{ 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1 }, // 9
			{ 1, 0, 0, 0, 4, 0, 0, 0, 1, 0, 2, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1 }, // 10
			{ 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1 }, // 11
			{ 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1 }, // 12
			{ 1, 1, 1, 1, 0, 1, 0, 1, 1, 5, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1 }, // 13
			{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 3, 0, 0, 1 }, // 14
			{ 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1 }, // 15
			{ 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1 }, // 16
			{ 1, 0, 0, 0, 3, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1 }, // 17
			{ 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1 }, // 18
			{ 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1 }, // 19
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } // 20
	};

	int x;
	int y;

	int mScale = CorporateDesign.MINI_MAP_SCALE;
	private Player[] player;
	private LabyrinthDrawer mLabyrinthDrawer;
	
	BufferedImage imgLab = null;

	/**
	 * Constructor for objects of class Labyrinth
	 */
	public Labyrinth() {
		x = 1;
		y = 1;
		player = new Player[8];
		player[0] = new Player(1, 1, "", 1000, new Color(255, 0, 0), 1, mLabyrinth);
		player[1] = new Player(19, 1, "", 1000, new Color(0, 255, 0), 1, mLabyrinth);
		player[2] = new Player(19, 19, "", 1000, new Color(0, 0, 255), 1, mLabyrinth);
		player[3] = new Player(1, 19, "", 1000, new Color(0, 0, 0), 1, mLabyrinth);
		player[4] = new Player(1, 10, "", 1000, new Color(255, 155, 0), 1, mLabyrinth);
		player[5] = new Player(10, 19, "", 1000, new Color(0, 255, 155), 1, mLabyrinth);
		player[6] = new Player(19, 10, "", 1000, new Color(155, 255, 0), 1, mLabyrinth);
		player[7] = new Player(10, 1, "", 1000, new Color(0, 155, 255), 1, mLabyrinth);

		mLabyrinthDrawer = LabyrinthDrawer.getInstance();
		imgLab = mLabyrinthDrawer.getLabyrinth(mScale);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setTitle("Mini-Map");
		this.setSize((mLabyrinth[0].length + 1) * mScale, (mLabyrinth.length + 4) * mScale);
		this.setLocation((int) (screenSize.getWidth() * 0.25 - this.getWidth() * 0.5),
						 (int) (screenSize.getHeight() * 0.5 - this.getHeight() * 0.5));
		this.setResizable(false);
	}
	
	
	public void preload(){
		spriteLoader.DataReader mDataReader = spriteLoader.DataReader.getInstance("img/", "avatar");
		mDataReader.readFiles();
		LabyrinthDrawer.getInstance();
	}

	public void draw(Graphics g) {
		if(mLabyrinthDrawer != null) {
			imgLab = mLabyrinthDrawer.drawLabyrinth(mScale);
			g.drawImage(imgLab, 0, 0, null);
	
			if (mLabyrinth != null) {
				for (int i = 1; i < player.length; i++) {
					player[i].draw(g);
				}
				player[0].drawCurrentPlayer(g);
			}
		}else {
			
//			System.out.println("fehler");
		}
	}
	
	public int getValue(int[][] lab, int x, int y) {
		return lab[y][x];
	}	
	
	public void printLabyrinth(int[][] lab) {
//		for(int[] row : lab)
//	    	System.out.println(Arrays.toString(row));
		
		printLabyrinth(lab, true);
	}
	
	public void printLabyrinth(int[][] lab, boolean correct) {
		if(correct) {
			for (int y = 0; y < lab.length; y++) {
				for (int x = 0; x < lab[y].length; x++) {
					System.out.format("%d, ", lab[y][x]);
				}
				System.out.println();
			}
		} else {
			for (int y = 0; y < lab.length; y++) {
				for (int x = 0; x < lab[y].length; x++) {
					System.out.format("%d, ", lab[x][y]);
				}
				System.out.println();
			}
		}
		
		System.out.println("lab.length = " + lab.length);
		System.out.println("lab[0].length = " + lab[0].length + "\n");
	}

	public String toString() {
		String lString = "";
		for (int x = 0; x < mLabyrinth.length; x++) {
			for (int y = 0; y < mLabyrinth[x].length; y++) {
				lString += mLabyrinth[x][y];
				if (y != mLabyrinth.length - 1) {
					lString += ",";
				}
			}
			if (x != mLabyrinth.length - 1) {
				lString += "/";
			}
		}
		return "L:" + lString;
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
		System.out.println('\u000C');
		for (int z = 0; z < mLabyrinth.length; z++) {
			for (int sp = 0; sp < mLabyrinth[0].length; sp++) {
				System.out.print(mLabyrinth[z][sp]);
			}
			System.out.println();
		}
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
	
	public ArrayList<int[]> getPlayerPositions() {
		ArrayList<int[]> list = new ArrayList<int[]>();
		
		for (int i = 1; i < player.length; i++) {
			int[] pos = new int[2];
			pos[0] = player[i].getX();
			pos[1] = player[i].getY();
			list.add(pos);
		}
			
		return list;
	}

	public int[][] getLab() {
		return mLabyrinth;
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

	public void laden() {
		try {
			BufferedReader in = new BufferedReader(new FileReader("labyrinth.txt"));
			String zeile = null;
			int a = 0;
			while ((zeile = in.readLine()) != null) {
				a++;
			}
			in.close();

			in = new BufferedReader(new FileReader("labyrinth.txt"));
			String[] z = new String[a];
			for (int i = 0; i < a; i++) {
				z[i] = in.readLine();
			}
			arrToLab(z);
		} catch (IOException e) {
			System.out.println("Es ist ein Lesefehler aufgetreten");
		}
	}

	public void speichern() {
		String[] arr = labToArray();
		// try-Block, falls Fehler entstehen
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("labyrinth1.txt")));
			for (int i = 0; i < arr.length; i++) {
				out.println(arr[i]);
			}
		} catch (IOException e) {
			System.out.println("Es ist ein Schreibfehler aufgetreten.");
		}
	}

	public String[] labToArray() {
		String lString = "";
		String[] arrLab = new String[mLabyrinth.length];
		for (int x = 0; x < mLabyrinth.length; x++) {
			lString = "";
			for (int y = 0; y < mLabyrinth[x].length; y++) {
				lString += mLabyrinth[x][y];
				if (y != mLabyrinth.length - 1) {
					lString += ",";
				}
			}
			if (x != mLabyrinth.length - 1) {
				lString += "/";
			}
			arrLab[x] = lString;
		}
		return arrLab;
	}

	public void arrToLab(String[] arrString) {
		int[][] nLab = null;
		String lString = "";
		for (int x = 0; x < arrString.length; x++) {
			lString += arrString[x];
		}
		String[] zeilen = lString.split("/");
		nLab = new int[zeilen.length][];
		for (int i = 0; i < zeilen.length; i++) {
			String[] spalten = zeilen[i].split(",");
			nLab[i] = new int[spalten.length];
			for (int j = 0; j < spalten.length; j++) {
				nLab[i][j] = Integer.parseInt(spalten[j]);
			}
		}
		mLabyrinth = nLab;
	}
}
