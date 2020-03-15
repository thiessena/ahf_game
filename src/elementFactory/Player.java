package elementFactory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import graphicsDrawer.CorporateDesign;
import mainPackage.Labyrinth;
import mainPackage.directions;
import spriteLoader.SpriteLoader;

/**
 * Write a description of class Player here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player {
	private int x;
	private int y;
	private String clientIP;
	private int clientPort;
	private Color farbe;
	private double radius;
	private int[][] lab;
	private int clientScale = CorporateDesign.CLIENT_SCALE;
	private int labPlayerScale = CorporateDesign.PLAYER_SCALE;
	private int miniMapScale = CorporateDesign.MINI_MAP_SCALE;

	private String avatarName = "avatar";
	private int moodWidth = 96;
	private int moodHeight = 143;
	private int moodBuff = 0;

	private SpriteLoader sl;
	private ItemList il;
	private Labyrinth l;
	private HashMap<avatarMoods, BufferedImage> avatars;
	public ArrayList<int[]> positions = new ArrayList<int[]>();

    public directions direction = directions.PLAIN;

    String richtung;

    avatarMoods mood = avatarMoods.PLAIN_FRONT;

    public Player(int pX, int pY, String pClientIP, int pClientPort, 
            Color pFarbe, double pRadius, 
            int[][] pLab, 
            directions pDirection, avatarMoods pMood) {
		sl = SpriteLoader.getInstance("img/", avatarName, moodWidth, moodHeight, moodBuff);
		il = new ItemList();
		avatars = sl.getAvatars();

		int[] pos = {x, y};
		positions.add(pos);
		
		x = pX;
		y = pY;
		
		clientIP = pClientIP;
		clientPort = pClientPort;
		farbe = pFarbe;
		radius = pRadius;
        direction = pDirection;
        lab = pLab;
        mood = pMood;
	}
    
    public Player(int pX, int pY, String pClientIP, int pClientPort, 
            Color pFarbe, double pRadius, 
            int[][] pLab) {
		sl = SpriteLoader.getInstance("img/", avatarName, moodWidth, moodHeight, moodBuff);
		il = new ItemList();
		avatars = sl.getAvatars();

		int[] pos = {x, y};
		positions.add(pos);
		
		x = pX;
		y = pY;
		
		clientIP = pClientIP;
		clientPort = pClientPort;
		farbe = pFarbe;
		radius = pRadius;
        lab = pLab;
    }

	public Player() {
		sl = SpriteLoader.getInstance("img/", avatarName, moodWidth, moodHeight, moodBuff);
		il = new ItemList();
		avatars = sl.getAvatars();

		new Player(1, 1, "", 1000, new Color(255, 0, 0), 1, lab, direction, mood);
	}
	
	public void pickItem(Graphics g, Items pItem) {
		setLabValue(0);
		
	}
	
	public Items itemAtPlace() {
		return getLabValue() > 1 ? Items.fromID(getLabValue()) : null;
	}

	public boolean itemAtPlace(Items pItem) {
		int pos = getLabValue();
		
		if(pos == il.getItem(pItem)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setLabValue(int pNewValue) {
		setLabValue(pNewValue, x, y);
	}
	
	public void setLabValue(int pNewValue, int x, int y) {
		lab[y][x] = pNewValue;
	}
	
	public int getLabValue() {
		return getLabValue(x, y);
	}
	
	public int getLabValue(int x, int y) {
		return lab[y][x];
	}
	
	private String getStringPos(int[] pPosition) {
		return "( " + pPosition[0] + " | " + pPosition[1] + " )";
	}

	public void move(int i) {
		switch (i) {
		case 0:
			bewege(-1, 0);
			direction = directions.LEFT;
			if (mood == avatarMoods.RUNNING_LEFT_WEST) {
				mood = avatarMoods.RUNNING_RIGHT_WEST;
			} else if (mood == avatarMoods.RUNNING_RIGHT_WEST) {
				mood = avatarMoods.RUNNING_LEFT_WEST;
			} else {
				mood = avatarMoods.RUNNING_LEFT_WEST;
			}
			break;
		case 1:
			bewege(0, -1);
			direction = directions.UP;
			if (mood == avatarMoods.RUNNING_LEFT_BACK) {
				mood = avatarMoods.RUNNING_RIGHT_BACK;
			} else if (mood == avatarMoods.RUNNING_RIGHT_BACK) {
				mood = avatarMoods.RUNNING_LEFT_BACK;
			} else {
				mood = avatarMoods.RUNNING_LEFT_BACK;
			}
			break;
		case 2:
			bewege(1, 0);
			direction = directions.RIGHT;
			if (mood == avatarMoods.RUNNING_LEFT_EAST) {
				mood = avatarMoods.RUNNING_RIGHT_EAST;
			} else if (mood == avatarMoods.RUNNING_RIGHT_EAST) {
				mood = avatarMoods.RUNNING_LEFT_EAST;
			} else {
				mood = avatarMoods.RUNNING_LEFT_EAST;
			}
			break;
		case 3:
			bewege(0, 1);
			direction = directions.DOWN;
			if (mood == avatarMoods.RUNNING_LEFT_FRONT) {
				mood = avatarMoods.RUNNING_RIGHT_FRONT;
			} else if (mood == avatarMoods.RUNNING_RIGHT_FRONT) {
				mood = avatarMoods.RUNNING_LEFT_FRONT;
			} else {
				mood = avatarMoods.RUNNING_LEFT_FRONT;
			}
			break;
		default:
			direction = directions.PLAIN;
			break;
		}
		// ausgabe();
	}
	
	@Deprecated
	public void moveOld(int i) {
		switch (i) {
		case 0:
			bewege(-1, 0);
			direction = directions.LEFT;
			break;
		case 1:
			bewege(0, -1);
			direction = directions.UP;
			break;
		case 2:
			bewege(1, 0);
			direction = directions.RIGHT;
			break;
		case 3:
			bewege(0, 1);
			direction = directions.DOWN;
			break;
		default:
			direction = directions.PLAIN;
			break;
		}
		// ausgabe();
	}

	@Deprecated
	public void changeMood(Graphics g) {
		// directions direc = getDirection();
		int scl = clientScale;

		switch (direction) {
		case LEFT:
			g.drawImage(sl.drawScaledAvatar(avatarMoods.RUNNING_LEFT_WEST, scl, scl), x, y, null);
			break;
		case RIGHT:
			g.drawImage(sl.drawScaledAvatar(avatarMoods.RUNNING_LEFT_EAST, scl, scl), x, y, null);
			break;
		case UP:
			g.drawImage(sl.drawScaledAvatar(avatarMoods.RUNNING_LEFT_BACK, scl, scl), x, y, null);
			break;
		case DOWN:
			g.drawImage(sl.drawScaledAvatar(avatarMoods.RUNNING_LEFT_FRONT, scl, scl), x, y, null);
			break;
		default:
			g.drawImage(sl.drawScaledAvatar(avatarMoods.PLAIN_FRONT, scl, scl), x, y, null);
			break;
		}
	}

	public BufferedImage changeImgMood(Graphics g) {
		return changeImgMood(g, clientScale);
	}

	public BufferedImage changeImgMood(Graphics g, int pScale) {
		// directions direc = getDirection();
		switch (direction) {
		case LEFT:
			return sl.drawScaledAvatar(avatarMoods.RUNNING_LEFT_WEST, pScale, pScale);
		case RIGHT:
			return sl.drawScaledAvatar(avatarMoods.RUNNING_LEFT_EAST, pScale, pScale);
		case UP:
			return sl.drawScaledAvatar(avatarMoods.RUNNING_LEFT_BACK, pScale, pScale);
		case DOWN:
			return sl.drawScaledAvatar(avatarMoods.RUNNING_LEFT_FRONT, pScale, pScale);
		default:
			return sl.drawScaledAvatar(avatarMoods.PLAIN_FRONT, pScale, pScale);
		}
	}

	public void bewege(int pX, int pY) {
		if (x + pX > -1 && x + pX < lab[0].length && y + pY > -1 && y + pY < lab.length) {
			if (lab[y + pY][x + pX] != 1) {
				x = x + pX;
				y = y + pY;
			}
		}
	}

	/**
     *         // #1: y-Koordinate
    // #2: Rotanteil
    // #3: Grünanteil
    // #4: Blauanteil
    // #5: Radius
    // #6: ClientIP
    // #7: Direction
     */
    public String toString() {
        String pString = "";
        return pString += x + "," +                 // #0: x-Koordinate
        y + "," + 
        farbe.getRed() + "," + 
        farbe.getGreen() + "," + 
        farbe.getBlue() + "," +
        radius + "," + 
        clientIP + "," + 
        direction + "," + 
        mood;

        // #1: y-Koordinate
        // #2: Rotanteil
        // #3: Grünanteil
        // #4: Blauanteil
        // #5: Radius
        // #6: ClientIP
        // #7: Direction
        // #8: Mood
    }

	public void drawCurrentPlayer(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(new Color(0, 255, 255, 100));
		g2.fillOval(x * miniMapScale - (labPlayerScale / 3), y * miniMapScale - (labPlayerScale / 3), labPlayerScale, labPlayerScale);

		g2.setColor(new Color(0, 255, 255));
		g2.setStroke(new BasicStroke(3));
		g2.drawOval(x * miniMapScale - (labPlayerScale / 3), y * miniMapScale - (labPlayerScale / 3), labPlayerScale, labPlayerScale);

		BufferedImage img = changeImgMood(g, labPlayerScale);
		g2.drawImage(img, x * miniMapScale - img.getWidth() / 3, y * miniMapScale - img.getHeight() / 3, null);
	}

	public void draw(Graphics g) {
		if (clientIP == "") {
			g.setColor(new Color(150, 150, 150));
		} else {
			g.setColor(farbe);
		}
		BufferedImage img = changeImgMood(g, miniMapScale + miniMapScale / 2);

		g.drawImage(img, x * miniMapScale, y * miniMapScale - img.getHeight() / 4, null);
	}
	
	public void draw(Graphics g, avatarMoods pMood, int x, int y, int scl) {
		if (clientIP == "") {
			g.setColor(new Color(150, 150, 150));
		} else {
			g.setColor(farbe);
		}
		BufferedImage img = sl.drawScaledAvatar(pMood, scl, scl);

		g.drawImage(img, x, y, null);
	}

	public void drawClient(Graphics g, int x, int y) {
		if (clientIP == "") {
			g.setColor(new Color(150, 150, 150));
		} else {
			g.setColor(farbe);
		}
		// g.fillOval(x, y,scl,scl);
		// g.drawImage(sl.drawScaledAvatar(avatarMoods.PLAIN_FRONT, scl, scl), x, y,
		// null);
		BufferedImage img = changeImgMood(g);

		g.drawImage(img, x + 5, y, null);
	}
	
	// ------------Getter-----------//
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getClientIP() {
		return clientIP;
	}

	public int getClientPort() {
		return clientPort;
	}

	public Color getFarbe() {
		return farbe;
	}

	public double getRadius() {
		return radius;
	}

	public String getName() {
		return avatarName;
	}

    public int getMoodWidth() {
        return moodWidth;
    }

    public int getMoodHeight() {
        return moodHeight;
    }

    public int getMoodBuff() {
        return moodBuff;
    }

    public directions getDirection() {
        return direction;
    }

    public avatarMoods getMood(){
        return mood;
    }

	// ------------Setter-----------//
	public void setX(int pX) {
		x = pX;
	}

	public void setY(int pY) {
		y = pY;
	}

	public void setClientIP(String pClientIP) {
		clientIP = pClientIP;
	}

	public void setClientPort(int pClientPort) {
		clientPort = pClientPort;
	}

	public void setFarbe(Color pFarbe) {
		farbe = pFarbe;
	}

	public void setRadius(int pRadius) {
		radius = pRadius;
	}

	public void setName(String pName) {
		avatarName = pName;
	}

	public void setMoodWidth(int pMoodWidth) {
		moodWidth = pMoodWidth;
	}

	public void setMoodHeight(int pMoodHeight) {
		moodHeight = pMoodHeight;
	}

	public void setMoodBuff(int pMoodBuff) {
		moodBuff = pMoodBuff;
	}

	public void setDirection(directions pDirection) {
		direction = pDirection;
	}

	public void setIPPort(String pClientIP, int pClientPort) {
		clientIP = pClientIP;
		clientPort = pClientPort;
	}
}
