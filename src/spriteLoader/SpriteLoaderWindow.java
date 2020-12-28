package spriteLoader;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.util.HashMap;

import elementFactory.Mood;
import elementFactory.avatarMoods;
import mainPackage.*;

public class SpriteLoaderWindow extends Fenster {

    int scl = 20;

    BufferedImage key;
    BufferedImage player;

    HashMap<avatarMoods, BufferedImage> avatars;
    HashMap<String, BufferedImage> items;

    Mood mMood;
    DataReader mDataReader;

    public SpriteLoaderWindow(avatarMoods pMood) {
        mMood = Mood.getInstance();
        mDataReader = DataReader.getInstance("img/", "avatar");
        
        items = mDataReader.getItems();
        avatars = new HashMap<avatarMoods, BufferedImage>();
        loadAvatarMoods(96, 143, 0);
        
        key = drawScaledItem("key", scl, scl);      
        player = drawAvatar(pMood);
    }

    public void draw(Graphics g) {
        g.drawImage(key, 0, 0, this);
        g.drawImage(player, 0, 0, this);
    }

    public BufferedImage drawItem(String pName) {
        return items.get(pName);
    }

    public BufferedImage drawScaledItem(String pName, int pNewWidth, int pNewHeight) {
        return scaleImage(items.get(pName), pNewWidth, pNewHeight);
    }

    public BufferedImage drawAvatar(avatarMoods pMood) {
        return avatars.get(pMood);
    }

    public HashMap<avatarMoods, BufferedImage> getAvatars() {
        return avatars;
    }

    private void loadAvatarMoods(String pAvatarName, int pMoodWidth, int pMoodHeight, int pMoodBuff) {
        BufferedImage img = drawItem(pAvatarName);
        
        // parameters of each character mood
        int buff = pMoodBuff;
        int width = pMoodWidth;
        int height = pMoodHeight;

        // calculates number of moods in each line and column
        int xMoods = (int) img.getWidth() / width;
        int yMoods = (int) img.getHeight() / height;

        for(int y = 0; y < yMoods; y++) {
            for(int x = 0; x < xMoods; x++) {
                avatars.put(mMood.getMood(x, y), img.getSubimage((width + buff) * x, (height + buff) * y, width, height));                
            }
        }
    }

    private void loadAvatarMoods(int pMoodWidth, int pMoodHeight, int pMoodBuff) {
        loadAvatarMoods("avatar", pMoodWidth, pMoodHeight, pMoodBuff);
    }

    public BufferedImage scaleImage(BufferedImage pImage, int pWidth, int pHeight) {
        BufferedImage resized = new BufferedImage(pWidth, pHeight, pImage.getType());
        Graphics2D g = resized.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(pImage, 0, 0, pWidth, pHeight, 0, 0, pImage.getWidth(), pImage.getHeight(), null);
        g.dispose();

        return resized;
    }

    public static BufferedImage toBufferedImage(Image img) {
        if(img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
