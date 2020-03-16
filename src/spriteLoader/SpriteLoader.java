package src.spriteLoader;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import src.elementFactory.Mood;
import src.elementFactory.avatarMoods;
import src.graphicsDrawer.CorporateDesign;

/**
 * Verarbeitet die Elemente aus dem {@link spriteLoader.DataReader} und stellt entsprechende Methoden bereit.
 * 
 * @author Jonas Schweizer
 *
 */
public class SpriteLoader {
	HashMap<avatarMoods, BufferedImage> avatars;
	HashMap<String, BufferedImage> items;
	HashMap<String, Font> fonts;

	Mood mMood;
	DataReader mDataReader;
	private static SpriteLoader sInstance = null;

	private SpriteLoader() {
		mMood = Mood.getInstance();
		mDataReader = DataReader.getInstance(CorporateDesign.IMAGE_FOLDER, CorporateDesign.getAvatarName());
//		mFileReader = FileReader.getInstance();

		items = mDataReader.getItems();
		fonts = mDataReader.getFonts();
		
		avatars = new HashMap<avatarMoods, BufferedImage>();
		loadAvatarMoods(CorporateDesign.getAvatarName(), CorporateDesign.getMoodWidth(), CorporateDesign.getMoodHeight(), CorporateDesign.getMoodBuff());
	}

	public static SpriteLoader getInstance() {
		if (sInstance == null) {
			sInstance = new SpriteLoader();
		}
		return sInstance;
	}
	
	public Font selectFont(String pFont) {
		return fonts.get(pFont);
	}

	public BufferedImage drawItem(String pName) {
		return items.get(pName);
	}

	public BufferedImage drawScaledItem(String pItem, double pNewWidth, double pNewHeight) {
		return drawScaledItem(pItem, (int) pNewWidth, (int) pNewHeight);
	}
	
	public BufferedImage drawScaledItem(String pName, int pNewWidth, int pNewHeight) {
		return scaleImage(items.get(pName), pNewWidth, pNewHeight);
	}

	public BufferedImage drawAvatar(avatarMoods pMood) {
		return avatars.get(pMood);
	}

	public BufferedImage drawScaledAvatar(avatarMoods pMood, int pBoundaryWidth, int pBoundaryHeight) {
		BufferedImage img = avatars.get(pMood);

		Dimension oldD = new Dimension(img.getWidth(), img.getHeight());
		Dimension newD = getScaledDimension(oldD, new Dimension(pBoundaryWidth, pBoundaryHeight));

		BufferedImage scaled = scaleImage(img, (int) newD.getWidth(), (int) newD.getHeight());

		return scaled;
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

		for (int y = 0; y < yMoods; y++) {
			for (int x = 0; x < xMoods; x++) {
				avatars.put(mMood.getMood(x, y),
						img.getSubimage((width + buff) * x, (height + buff) * y, width, height));
			}
		}
	}

	public BufferedImage scaleImage(BufferedImage pImage, int pWidth, int pHeight) {
		BufferedImage resized = new BufferedImage(pWidth, pHeight, pImage.getType());
		Graphics2D g = resized.createGraphics();

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(pImage, 0, 0, pWidth, pHeight, 0, 0, pImage.getWidth(), pImage.getHeight(), null);
		g.dispose();

		return resized;
	}

	private static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
		int original_width = imgSize.width;
		int original_height = imgSize.height;
		int bound_width = boundary.width;
		int bound_height = boundary.height;
		int new_width = original_width;
		int new_height = original_height;

		// first check if we need to scale width
		if (original_width > bound_width) {
			// scale width to fit
			new_width = bound_width;
			// scale height to maintain aspect ratio
			new_height = (new_width * original_height) / original_width;
		}

		// then check if we need to scale even with the new height
		if (new_height > bound_height) {
			// scale height to fit instead
			new_height = bound_height;
			// scale width to maintain aspect ratio
			new_width = (new_height * original_width) / original_height;
		}

		return new Dimension(new_width, new_height);
	}

	/**
	 * Konvertiert ein {@link java.awt.Image} in ein {@link java.awt.Image.BufferedImage}.
	 * 
	 * @deprecated Die Methode wird aufgrund einer Codeaufbereitung nicht mehr benötigt. 
	 * 			   Vorher wurde mit Images anstatt mit BufferedImages gearbeitet.
	 * 			   Es ist also keine Umstellung nötig, da alle Methoden nun mit BufferedImages arbeiten.
	 * @param img  Bild als Image, das in ein BufferedImage konvertiert werden soll.
	 * @return BufferedImage, des zu konvertierenden Bildes.
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
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
