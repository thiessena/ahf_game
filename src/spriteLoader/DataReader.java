package src.spriteLoader;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import src.graphicsDrawer.CorporateDesign;

/**
 * Greift auf die Ordnerstruktur zu und importiert alle nötigen Elemente.
 * Sowohl Bilder bzw. Grafiken als auch Schriftarten werden geladen und in separaten HashMaps abgelegt.
 * Sofern ein Bild keinen Avatar enthält, werden diese schon skaliert, um die Performance zu steigern.
 * 
 * @author Jonas Schweizer
 * @version 27.01.2020
 *
 */
public class DataReader {
	
	/**
	 * Scalewert / Größeneinheit des ClientLabyrinths (<code>1 px = 1 scl</code>).
	 * <p>
	 * Auf diese maximale Größe werden alle Items außer dem Avatar herunterskaliert ({@link #readFiles()}), 
	 * um die Performance zu steigern.
	 * 
	 * @see graphicsDrawer.CorporateDesign#CLIENT_SCALE
	 * @see mainPackage.ClientLabyrinth#mScale
	 */
	private int mClientScale = CorporateDesign.CLIENT_SCALE;
	
	/**
	 * Instanz des FileReaders.
	 */
	public static DataReader sInstance;
	
	/**
	 * Bildordner in der Ordnerstruktur.
	 * <p>
	 * Ist zu Beginn null.
	 */
	private static File img_dir = new File("");
	
	/**
	 * Schriftordner in der Ordnerstruktur.
	 * <p>
	 * Ist zu Beginn null.
	 */
	private static File font_dir = new File("");
	
	/**
	 * Bild-Dateiendungen.
	 */
	private static String[] IMG_EXTENSIONS = new String[] { "jpg", "png", "bmp" };
	
	/**
	 * Schrift-Dateiendungen.
	 */
	private static String[] FONT_EXTENSIONS = new String[] { "otf", "ttf" };
	
	/**
	 * Bild-Dateifilter.
	 * @see #fileFilter(String[])
	 */
	private static FilenameFilter IMAGE_FILTER = fileFilter(IMG_EXTENSIONS);
	
	/**
	 * Schrift-Dateifilter.
	 * @see #fileFilter(String[])
	 */
	private static FilenameFilter FONT_FILTER = fileFilter(FONT_EXTENSIONS);
	
	/**
	 * Name des Avatars.
	 * <p>
	 * Ist zu Beginn null.
	 */
	private static String avatarName = "";
	
	/**
	 * HashMap mit den Elementen, Items und Bildern.
	 */
	private HashMap<String, BufferedImage> imgItems;
	
	/**
	 * HashMap mit den Schriften.
	 */
	private HashMap<String, Font> fonts;
	
    
    /**
     * Initialisiert die Member-Variablen und liest die Dateien aus den definierten Ordnern.
     * Ein Aufruf des Konstruktors ist nur über die Methoden {@link #getInstance(String, String)}
     * und {@link #getInstance(String, String, String, String[], String[])} möglich, um sicherzustellen,
     * dass nur eine Instanz des FileReaders existiert.
     * 
	 * @param pImageFolder		Ordner als String, aus dem die Bilder und Items importiert werden sollen.
	 * @param pFontFolder		Ordner als String, aus dem die Schriften importiert werden sollen.
	 * @param pAvatarName		Name als String des Avatars, der als Spielfigur dient.
	 * @param pImageExtensions	Array mit den Bilddateiendungen, die zu berücksichtigen sind.
	 * @param pFontExtensions	Array mit den Schriftdateiendungen, die zu berücksichtigen sind.
	 * 
	 * @see #readFiles()
     */
    private DataReader(String pImageFolder, String pFontFolder, String pAvatarName, String[] pImageExtensions, String[] pFontExtensions) {
    	img_dir = new File(pImageFolder);
    	font_dir = new File(pFontFolder);
    	
    	imgItems = new HashMap<String, BufferedImage>();
    	fonts = new HashMap<String, Font>();
    	
    	if(!pImageExtensions.equals(IMG_EXTENSIONS)) {
    		IMG_EXTENSIONS = pImageExtensions;
    		IMAGE_FILTER = fileFilter(pImageExtensions);
    	}
    	
    	if(!pFontExtensions.equals(FONT_EXTENSIONS)) {
    		FONT_EXTENSIONS = pFontExtensions;
    		FONT_FILTER = fileFilter(pFontExtensions);
    	}
    	
    	avatarName = pAvatarName;
    	
    	readFiles();
    }
    
    public static DataReader getInstance() {
    	return sInstance != null ? sInstance : null;
    }
    
    /**
	 * Gibt eine Instanz des {@link spriteLoader.DataReader} zurück.
	 * <p>
	 * Somit wird sicher gestellt, dass im AHF Simulator nur eine Instanz dieser Klasse existiert.
	 * <p>
	 * Die übrigen Parameter werden in dieser Methode standardmäßig in dieser Klasse gesetzt.
	 * Falls dies nicht gewünscht ist, so kann {@link #getInstance(String, String, String, String[], String[])}
	 * aufgerufen werden.
	 * <p>
	 * Für die Bild-Dateiendungen wird hier standardmäßig der Array {@link #IMG_EXTENSIONS} übernommen.
     * Gleiches gilt für die Schrift-Dateiendungen. Diese werden hier standardmäßig von 
     * {@link #FONT_EXTENSIONS} übergeben.
	 * 
     * @param pImageFolder Ordner als String, aus dem die Bilder und Items importiert werden sollen.
     * @param pAvatarName  Name als String des Avatars, der als Spielfigur dient.
     * @return Instanz des FileReaders
     * 
     * @see #getInstance(String, String, String, String[], String[])
     */
	public static DataReader getInstance(String pImageFolder, String pAvatarName) {
		return getInstance(pImageFolder, "font/", pAvatarName, IMG_EXTENSIONS, FONT_EXTENSIONS);
	}
	
	/**
	 * Gibt eine Instanz des {@link spriteLoader.DataReader} zurück. Wenn bereits eine Instanz dieser Klasse 
	 * vorhanden ist, so wird die existierende Instanz zurückgegeben. Andernfalls wird eine neue
	 * Instanz initialisiert.
	 * Somit wird sicher gestellt, dass im AHF Simulator nur eine Instanz dieser Klasse existiert.
	 * <p>
     * Sofern die Bild-Dateiendungen, die dem FileReader übergeben wurden, mit denen der {@link #IMG_EXTENSIONS} 
     * übereinstimmen, werden diese ignoriert und stattdessen der Array übernommen, der standardmäßig gesetzt ist.
     * Gleiches gilt für die Schrift-Dateiendungen und den Array {@link #FONT_EXTENSIONS}.
	 * 
	 * @param pImageFolder		Ordner als String, aus dem die Bilder und Items importiert werden sollen.
	 * @param pFontFolder		Ordner als String, aus dem die Schriften importiert werden sollen.
	 * @param pAvatarName		Name als String des Avatars, der als Spielfigur dient.
	 * @param pImageExtensions	Array mit den Bilddateiendungen, die zu berücksichtigen sind.
	 * @param pFontExtensions	Array mit den Schriftdateiendungen, die zu berücksichtigen sind.
	 * @return Instanz des FileReaders.
	 * 
	 * @see #getInstance(String, String)
	 */
	public static DataReader getInstance(String pImageFolder, String pFontFolder, String pAvatarName, String[] pImageExtensions, String[] pFontExtensions) {
		if(sInstance == null) {
			sInstance = new DataReader(pImageFolder, pFontFolder, pAvatarName, pImageExtensions, pFontExtensions);
		}
		return sInstance;
	}
	
	/**
	 * Gibt die HashMap mit den importierten Schriften zurück.
	 * 
	 * @return HashMap mit den importierten Schriften.
	 */
	public HashMap<String, Font> getFonts() {
		return fonts;
	}
    
	/**
	 * Gibt die HashMap mit den importierten Items und Bildern zurück.
	 * 
	 * @return HashMap mit den importierten Items.
	 */
    public HashMap<String, BufferedImage> getItems() {
    	return imgItems;
    }

	/**
     * Liest die Bilder und Schriften aus den Ordnern {@link img_dir} und {@link font_dir}, import und speichert diese.
     * Die importierten Bilder und Schriften werden in den globalen HashMaps {@link imgItems} und {@link fonts} gespeichert, 
     * um einen Zugriff durch andere Klassen zu erleichtern.
     */
    public void readFiles() {   	
    	if (img_dir.isDirectory()) {
    		for(final File f : img_dir.listFiles(IMAGE_FILTER)) {
            	if(removeFileExtension(f.getName()).equals(avatarName))
            		imgItems.put(removeFileExtension(f.getName()), importImage(f.getAbsolutePath()));
            	else
            		imgItems.put(removeFileExtension(f.getName()), importAndScaleImage(f.getAbsolutePath(), mClientScale, mClientScale));
            }
    		
    		for(final File f : font_dir.listFiles(FONT_FILTER)) {
    			try {
        	    	Font font = Font.createFont(Font.TRUETYPE_FONT, f).deriveFont(22f);
        	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        	        ge.registerFont(font);    
        	        
        	        fonts.put(removeFileExtension(f.getName()), font);
        	   } catch (IOException|FontFormatException e) {
        	   }
    		}
        }
    }
    
    /**
     * Importiert ein bestimmtes Bild mit einem absoluten Dateinamen 
     * bzw.&nbsp;Pfad und skaliert es auf eine zu definierende Größe. 
     * 
     * @param pImageFile der Dateiname des zu importierenden Bildes.
	 * @param pWidth	 Breite, auf die das Bild skaliert werden soll.
	 * @param pHeight    Höhe, auf die das Bild skaliert werden soll.
	 * @return das skalierte und importierte Bild.
     */
	private BufferedImage importAndScaleImage(String pImageFile, int pNewWidth, int pNewHeight) {
		return scaleImage(importImage(pImageFile), pNewWidth, pNewHeight);
	}

	/**
	 * Importiert ein bestimmtes Bild mit einem absoluten Dateinamen bzw.&nbsp;Pfad.
	 * 
	 * @param pImageFile der Dateiname des zu importierenden Bildes.
	 * @return das Bild, was importiert wurde.
	 */
	private BufferedImage importImage(String pImageFile) {
		BufferedImage bImg = null;
		try {
			bImg = ImageIO.read(new File(pImageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bImg;
	}
	
	/**
	 * Skaliert ein Bild auf eine neue Größe und gibt das skalierte Bild zurück.
	 * 
	 * @param pImage Bild, das skaliert werden soll.
	 * @param pWidth Breite, auf die das Bild skaliert werden soll.
	 * @param pHeight Höhe, auf die das Bild skaliert werden soll.
	 * @return das skalierte Bild.
	 */
	private BufferedImage scaleImage(BufferedImage pImage, int pWidth, int pHeight) {
		BufferedImage resized = new BufferedImage(pWidth, pHeight, pImage.getType());
		Graphics2D g = resized.createGraphics();

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(pImage, 0, 0, pWidth, pHeight, 0, 0, pImage.getWidth(), pImage.getHeight(), null);
		g.dispose();

		return resized;
	}
	
	/**
	 * Erstellt einen neuen Dateifilter mit zu übergebenden Dateiendungen.
	 * 
	 * @param pFileExtensions Dateiendungen als Array, nach den gefiltert werden soll.
	 * @return Dateifilter
	 */
	private static FilenameFilter fileFilter(String[] pFileExtensions) {
		return new FilenameFilter() {

	        @Override
	        public boolean accept(final File dir, final String name) {
	            for (final String ext : pFileExtensions) {
	                if (name.endsWith("." + ext)) {
	                    return (true);
	                }
	            }
	            return (false);
	        }
	    };
	}
    
	/**
	 * Entfernt aus einem Dateinamen die Dateiendung.
	 * 
	 * @param pFileName Dateiname, dessen Dateiendung entfernt werden soll.
	 * @return neuer Dateiname ohne Dateiendung.
	 */
    public String removeFileExtension(String pFileName) {
    	return pFileName.replaceFirst("[.][^.]+$", "");
    }
	
}
