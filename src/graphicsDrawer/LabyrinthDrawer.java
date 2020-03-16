package src.graphicsDrawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.spriteLoader.SpriteLoader;

/**
 * Zeichnet den Irrgarten für das {@link mainPackage.ClientLabyrinth} und das {@link mainPackage.Labyrinth}.
 * 
 * @author Jonas Schweizer
 * @version 02.02.2020
 *
 */
public class LabyrinthDrawer {

    SpriteLoader sl;
    private static int[][] mLabyrinth;
    BufferedImage mImageLab = null;

    private static LabyrinthDrawer sInstance;

    public static LabyrinthDrawer getInstance(int[][] lab, int scl) {
        if(sInstance == null)
            sInstance = new LabyrinthDrawer(lab, scl);
        return sInstance;
    }

    public static LabyrinthDrawer getInstance() {
        if(sInstance == null)
            sInstance = new LabyrinthDrawer();
        return sInstance;
    }

    public LabyrinthDrawer() {
        sl = SpriteLoader.getInstance();
    }

    public LabyrinthDrawer(int[][] lab, int scl) {
        sl = SpriteLoader.getInstance();
        mLabyrinth = lab;
        if(lab != null)
            mImageLab = drawLabyrinth(lab, scl);
    }

    public BufferedImage getLabyrinth(int scl) {
        if(mImageLab != null)
            return sl.scaleImage(mImageLab, scl, scl);
        else
            return null;
    }

    public void setLabyrinthImage(int[][] lab, int scl) {
        mLabyrinth = lab;
        mImageLab = drawLabyrinth(mLabyrinth, scl);
    }

    public BufferedImage drawLabyrinth(int scl) {
        return drawLabyrinth(mLabyrinth, scl);
    }

    public BufferedImage drawLabyrinth(int[][] lab, int scl) {
        BufferedImage img = new BufferedImage(scl * lab.length, scl * lab[0].length, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = img.createGraphics();

        if (lab != null) {
            for (int y = 0; y < lab.length; y++) {
                for (int x = 0; x < lab[0].length; x++) {
                    int newX = x * scl;
                    int newY = y * scl;

                    switch (lab[y][x]) {
                        case 0:
                        drawImageFloor(g, newX, newY, scl);
                        break;
                        case 1:
                        drawImageObstacle(g, newX, newY, scl);
                        break;
                        case 2:
                        drawImageItem(g, "Schlüssel", newX, newY, scl);
                        break;
                        case 3:
                        drawImageItem(g, "Putzeimer", newX, newY, scl);
                        break;
                        case 4:
                        drawImageItem(g, "Pinsel", newX, newY, scl);
                        break;
                        case 5:
                        drawImageItem(g, "Taschenrechner", newX, newY, scl);
                        break;
                    }
                }
            }
        }

        g.dispose();

        mImageLab = img;
        return mImageLab;
    }

    public BufferedImage drawTestLabyrinth(int[][] lab, int scl) {
        BufferedImage img = new BufferedImage(scl * lab.length, scl * lab[0].length, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = img.createGraphics();

        if (lab != null) {
            for (int y = 0; y < lab.length; y++) {
                for (int x = 0; x < lab[0].length; x++) {
                    int newX = x * scl;
                    int newY = y * scl;

                    switch (lab[y][x]) {
                        case 0:
                        drawFloor(g, newX, newY, scl);
                        break;
                        case 1:
                        drawObstacle(g, newX, newY, scl);
                        break;
                        case 2:
                        drawRectangle(g, newX, newY, scl, scl, new Color(255, 50, 50));
                        break;
                    }

                    drawLabNumbers(g, lab, y, x, newX + scl / 2, newY + scl / 2);
                }
            }
        }

        g.dispose();

        return img;
    }

    private void drawLabNumbers(Graphics g, int[][] lab, int x, int y, int newX, int newY) {
        g.setColor(new Color(0, 0, 0));
        g.drawString("" + lab[x][y], newX, newY);
    }

    private void drawImageItem(Graphics g, String pItem, int x, int y, int pLength) {
        int scaledLength = (int) (pLength * 0.7);
        drawImageFloor(g, x, y, pLength);
        g.drawImage(sl.drawScaledItem(pItem, scaledLength, scaledLength), (int) (x + (pLength - scaledLength) / 2), (int) (y + (pLength - scaledLength) / 2), null);
    }

    private void drawImageObstacle(Graphics g, int x, int y, int pLength) {
        g.drawImage(sl.drawScaledItem("Hindernis", pLength, pLength), x, y, null);
    }

    private void drawImageFloor(Graphics g, int x, int y, int pLength) {
        g.drawImage(sl.drawScaledItem("Boden", pLength, pLength), x, y, null);
    }

    private void drawObstacle(Graphics g, int x, int y, int pLength) {
        drawRectangle(g, x, y, pLength, pLength, new Color(140, 140, 140));
    }

    private void drawFloor(Graphics g, int x, int y, int pLength) {
        drawRectangle(g, x, y, pLength, pLength, new Color(200, 200, 200));
    }

    private void drawRectangle(Graphics g, int x, int y, int pWidth, int pHeight, Color pColor) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(pColor);
        g2.fillRect(x, y, pWidth, pHeight);

        // strokeWidth should depend on width and height of the rectangle.
        // 2 stands for the two values width and height, 13 stands for the percentage
        // how thick the stroke will be.
        int strokeWidth = (pWidth + pHeight) / (2 * 13);

        g2.setColor(new Color(pColor.getRed() - 50, pColor.getGreen() - 50, pColor.getBlue() - 50));
        g2.setStroke(new BasicStroke(strokeWidth));
        g2.drawRect(x, y, pWidth, pHeight);
    }

    public static int[][] getLabyrinth() {
        return mLabyrinth;
    }

    public static void setLabyrinth(int[][] mLabyrinth) {
        LabyrinthDrawer.mLabyrinth = mLabyrinth;
    }

}
