package src.mainPackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import src.elementFactory.ItemList;
import src.elementFactory.Items;
import src.elementFactory.avatarMoods;
import src.graphicsDrawer.CorporateDesign;
import src.spriteLoader.SpriteLoader;

/**
 * Write a description of class Player here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Player {
    protected int x;
    protected int y;
    protected String clientIP;
    protected int clientPort;
    protected Color farbe;
    protected double radius;
    protected int[][] lab;
    
    public Player(int pX, int pY, String pClientIP, int pClientPort, 
    Color pFarbe, double pRadius, 
    int[][] pLab) {
        x = pX;
        y = pY;
        clientIP = pClientIP;
        clientPort = pClientPort;
        farbe = pFarbe;
        radius = pRadius;
        lab = pLab;
    }

    public void move(int i) {
        switch (i) {
            case 0: bewege(-1, 0);
            break;
            case 1:
            bewege(0, -1);
            break;
            case 2:
            bewege(1, 0);
            break;
            case 3:
            bewege(0, 1);
            break;
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

    public void setIPPort(String pClientIP, int pClientPort) {
        clientIP = pClientIP;
        clientPort = pClientPort;
    }
}
