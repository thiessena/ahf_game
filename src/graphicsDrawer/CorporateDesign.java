package src.graphicsDrawer;

import java.awt.Color;
import java.util.HashMap;

/**
 * Definiert Farben gemäß des Corporates Design des 
 * <a href="https://schultraeger.csv-lippe.de/">Christlichen Schulvereins e.V.</a> 
 * und weitere vereinheitlichte Konstanten im AHFSimulator.
 * 
 * @author Jonas Schweizer
 * @version 03.02.2020
 *
 */
public abstract class CorporateDesign {
	/**
	 * Farbe Grün im Corporate Design des 
	 * <a href="https://schultraeger.csv-lippe.de/">Christlichen Schulvereins e.V.</a>
	 */
	public static final Color AHF_GREEN = new Color(200, 210, 16);
	
	/**
	 * Farbe Rot im Corporate Design des 
	 * <a href="https://schultraeger.csv-lippe.de/">Christlichen Schulvereins e.V.</a>
	 */
	public static final Color AHF_RED = new Color(226, 0, 26);
	
	/**
	 * Farbe Blau im Corporate Design des 
	 * <a href="https://schultraeger.csv-lippe.de/">Christlichen Schulvereins e.V.</a>
	 */
	public static final Color AHF_BLUE = new Color(0, 154, 198);
	
	/**
	 * Farbe Dunkelgrau als Schriftfarbe
	 */
	public static final Color FONT_COLOR = new Color(15, 15, 15);
	
	/**
	 * Größeneinheit des {@link mainPackage.ClientLabyrinth}. 
	 * Ein Pixel entspricht einer Größeneinheit.
	 */
	public static final int CLIENT_SCALE = 40;
	
	/**
	 * Größeneinheit des {@link mainPackage.Labyrinth}. 
	 * Ein Pixel entspricht einer Größeneinheit.
	 */
	public static final int MINI_MAP_SCALE = 10;
	
	/**
	 * Größeneinheit des {@link mainPackage.Player} im {@link mainPackage.Labyrinth}. 
	 * Ein Pixel entspricht einer Größeneinheit.
	 */
	public static final int PLAYER_SCALE = 30;
	
	/**
	 * HashMap mit allen Farben
	 */
	public static final HashMap<String, Color> colorMap;
	
	static {
		colorMap = new HashMap<String, Color>();
		colorMap.put("Grün", AHF_GREEN);
		colorMap.put("Rot", AHF_RED);
		colorMap.put("Blau", AHF_BLUE);
		colorMap.put("Grau", FONT_COLOR);
	}
	
	/**
	 * Gibt die gewünschte Farbe als {@link java.awt.Color} zurück.
	 * 
	 * @param pColor Text-String der gewünschten Farbe in der Form "Grün".
	 * @return entsprechende Farbe in der {@link #colorMap}
	 */
	public static Color getColor(String pColor) {
		return colorMap.get(pColor);
	}

	/**
	 * Gibt eine HashMap mit allen hier definierten Farben zurück.
	 * @return colorMap
	 */
	public static HashMap<String, Color> getColorMap() {
		return colorMap;
	}

	/**
	 * Gibt die Farbe ahfGrün zurück.
	 * @return ahfGreen
	 */
	public static Color getAHFGreen() {
		return AHF_GREEN;
	}

	/**
	 * Gibt die Farbe ahfRot zurück.
	 * @return ahfRed
	 */
	public static Color getAHFRed() {
		return AHF_RED;
	}

	/**
	 * Gibt die Farbe ahfBlau zurück.
	 * @return ahfBlue
	 */
	public static Color getAHFBlue() {
		return AHF_BLUE;
	}

	/**
	 * Gibt die Schriftfarbe zurück.
	 * @return fontColor
	 */
	public static Color getFontColor() {
		return FONT_COLOR;
	}
	
	
}
