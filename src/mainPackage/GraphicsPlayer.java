package mainPackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import elementFactory.ItemList;
import elementFactory.Items;
import elementFactory.avatarMoods;
import graphicsDrawer.CorporateDesign;
import spriteLoader.SpriteLoader;

/**
 * Erbt vom {@link mainPackage.Player} die grundlegenden Parameter(Position, IP, Port, etc).
 * Enth�lt alle n�tigen Methoden zum Zeichnen des Players, in den Labyrinthen.
 * @author Simon Penner
 * @author Jonas Schweizer
 * @version 02.03.2020
 */
public class GraphicsPlayer extends Player
{
    SpriteLoader sl;
    HashMap<avatarMoods, BufferedImage> avatars;

    protected int clientScale = CorporateDesign.CLIENT_SCALE;
    protected int labPlayerScale = CorporateDesign.PLAYER_SCALE;
    protected int miniMapScale = CorporateDesign.MINI_MAP_SCALE;

    private ItemList il;
    private Labyrinth l;

    public directions direction = directions.PLAIN;

    avatarMoods mood = avatarMoods.PLAIN_FRONT;
    public GraphicsPlayer(int pX, int pY, String pClientIP, int pClientPort, 
    Color pFarbe, double pRadius, 
    int[][] pLab, 
    directions pDirection, avatarMoods pMood) {
        super(pX, pY, pClientIP, pClientPort, pFarbe, pRadius, pLab);

        sl = SpriteLoader.getInstance();
        avatars = sl.getAvatars();

        x = pX;
        y = pY;
        clientIP = pClientIP;
        clientPort = pClientPort;
        farbe = pFarbe;
        radius = pRadius;
        lab = pLab;

        direction = pDirection;       
        mood = pMood;
    }

    // ------------Getter-----------//
    public int getLabValue() {
        return getLabValue(x, y);
    }

    public int getLabValue(int x, int y) {
        return lab[y][x];
    }

    private String getStringPos(int[] pPosition) {
        return "( " + pPosition[0] + " | " + pPosition[1] + " )";
    }

    // ------------Setter-----------//
    public void setLabValue(int pNewValue) {
        setLabValue(pNewValue, x, y);
    }

    public void setLabValue(int pNewValue, int x, int y) {
        lab[y][x] = pNewValue;
    }

    public void setDirection(directions pDirection) {
        direction = pDirection;
    }

    public directions getDirection() {
        return direction;
    }

    public avatarMoods getMood(){
        return mood;
    }

    // ------------Methoden-----------//
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

    public void move(int i) {
        switch (i) {
            case 0: bewege(-1, 0);
            direction = directions.LEFT;
            if(mood == avatarMoods.RUNNING_LEFT_WEST){
                mood = avatarMoods.RUNNING_RIGHT_WEST;
            }
            else if(mood == avatarMoods.RUNNING_RIGHT_WEST){
                mood = avatarMoods.RUNNING_LEFT_WEST;
            }
            else{
                mood = avatarMoods.RUNNING_LEFT_WEST;
            }
            break;
            case 1:
            bewege(0, -1);
            direction = directions.UP;
            if(mood == avatarMoods.RUNNING_LEFT_BACK){
                mood = avatarMoods.RUNNING_RIGHT_BACK;
            }
            else if(mood == avatarMoods.RUNNING_RIGHT_BACK){
                mood = avatarMoods.RUNNING_LEFT_BACK;
            }
            else{
                mood = avatarMoods.RUNNING_LEFT_BACK;
            }
            break;
            case 2:
            bewege(1, 0);
            direction = directions.RIGHT;
            if(mood == avatarMoods.RUNNING_LEFT_EAST){
                mood = avatarMoods.RUNNING_RIGHT_EAST;
            }
            else if(mood == avatarMoods.RUNNING_RIGHT_EAST){
                mood = avatarMoods.RUNNING_LEFT_EAST;
            }
            else{
                mood = avatarMoods.RUNNING_LEFT_EAST;
            }
            break;
            case 3:
            bewege(0, 1);
            direction = directions.DOWN;
            if(mood == avatarMoods.RUNNING_LEFT_FRONT){
                mood = avatarMoods.RUNNING_RIGHT_FRONT;
            }
            else if(mood == avatarMoods.RUNNING_RIGHT_FRONT){
                mood = avatarMoods.RUNNING_LEFT_FRONT;
            }
            else{
                mood = avatarMoods.RUNNING_LEFT_FRONT;
            }
            break;
            default:
            direction = directions.PLAIN;
            break;
        }
        // ausgabe();
    }

    public BufferedImage changeImgMood() {
        return changeImgMood(clientScale);
    }

    public BufferedImage changeImgMood(int pScale) {
        // directions direc = getDirection();
        switch (direction) {
            case LEFT:
            return sl.drawScaledAvatar(mood, pScale, pScale);
            case RIGHT:
            return sl.drawScaledAvatar(mood, pScale, pScale);
            case UP:
            return sl.drawScaledAvatar(mood, pScale, pScale);
            case DOWN:
            return sl.drawScaledAvatar(mood, pScale, pScale);
            default:
            return sl.drawScaledAvatar(mood, pScale, pScale);
        }
    }

    /**
     *         // #1: y-Koordinate
    // #2: Rotanteil
    // #3: Gr�nanteil
    // #4: Blauanteil
    // #5: Radius
    // #6: ClientIP
    // #7: Direction
     */
    public String toString() {
        String pString = "";
        return pString += x + "," +                 // #0: x-Koordinate
        y + "," +                                   // #1: y-Koordinate
        farbe.getRed() + "," +                      // #2: Rotanteil
        farbe.getGreen() + "," +                    // #3: Gr�nanteil
        farbe.getBlue() + "," +                     // #4: Blauanteil
        radius + "," +                              // #5: Radius
        clientIP + "," +                            // #6: ClientIP
        direction + "," +                           // #7: Direction
        mood;                                       // #8: Mood
    }

    public void drawCurrentPlayer(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(0, 255, 255, 100));
        g2.fillOval(x * miniMapScale - (labPlayerScale / 3), y * miniMapScale - (labPlayerScale / 3), labPlayerScale, labPlayerScale);

        g2.setColor(new Color(0, 255, 255));
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(x * miniMapScale - (labPlayerScale / 3), y * miniMapScale - (labPlayerScale / 3), labPlayerScale, labPlayerScale);

        BufferedImage img = changeImgMood(labPlayerScale);
        g2.drawImage(img, x * miniMapScale - img.getWidth() / 3, y * miniMapScale - img.getHeight() / 3, null);
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

    public void draw(Graphics g) {
        if (clientIP == "") {
            g.setColor(new Color(150, 150, 150));
        } else {
            g.setColor(farbe);
        }
        BufferedImage img = changeImgMood(miniMapScale + miniMapScale / 2);

        g.drawImage(img, x * miniMapScale, y * miniMapScale - img.getHeight() / 4, null);
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
        BufferedImage img = changeImgMood();

        g.drawImage(img, x + 5, y, null);
    }
}
