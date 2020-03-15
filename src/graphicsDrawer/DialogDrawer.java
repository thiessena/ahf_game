package src.graphicsDrawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;

import src.elementFactory.avatarMoods;
import src.mainPackage.ClientLabyrinth;
import src.spriteLoader.SpriteLoader;

/**
 * Zeichnet Dialoge in ein vom ClientLabyrinth übergegebenes Graphics Panel.
 * 
 * @author Jonas Schweizer
 * @version 03.02.2020
 *
 */
public class DialogDrawer {

	SpriteLoader sl;
	ClientLabyrinth cl;

	int lWidth;
	int lHeight;
	int scl;

	Color bg;
	Color ahfGreen  = CorporateDesign.AHF_GREEN;
	Color ahfRed    = CorporateDesign.AHF_RED;
	Color ahfBlue   = CorporateDesign.AHF_BLUE;
	Color fontColor = CorporateDesign.FONT_COLOR;

	Font pixelFont;

	private static final int ARROW_UP_LEFT = 0;
	private static final int ARROW_HALF_LEFT = 1;
	private static final int ARROW_UP_RIGHT = 2;
	private static final int ARROW_HALF_RIGHT = 3;

	private static final int BOTTOM = 10;
	private static final int TOP = 11;

	/**
	 * Setzt die Member-Variablen, die vom ClientLabyrinth übergeben wurden.
	 * 
	 * @param pLabyrinth ClientLabyrinth
	 */
	public DialogDrawer(ClientLabyrinth pLabyrinth) {
		cl = pLabyrinth;
		sl = SpriteLoader.getInstance("img/");

		lWidth = (int) pLabyrinth.getWidth();
		lHeight = (int) pLabyrinth.getHeight();
		scl = pLabyrinth.getScale();

		bg = pLabyrinth.getBackground();

		pixelFont = sl.selectFont("VPPixel-Simplified");
	}

	/**
	 * Zeichnet einen Kommunikationsdialog mit zwei Sprechblasen und zwei zu übergebenden Textnachrichten.
	 * 
	 * @param g			Graphics, auf dem der Dialog gezeichnet werden soll.
	 * @param pQuestion Frage, die in die obere Sprechblase geschrieben werden soll.
	 * @param pAnswer	Antwort auf die obige Frage.
	 */
	public void drawChatDialog(Graphics g, String pQuestion, String pAnswer) {
		clearPane(g);
		drawSpeachBubble(g, pQuestion, ahfBlue, ARROW_UP_RIGHT, TOP);
		
		cl.getAktPlayer().draw(g, avatarMoods.PLAIN_FRONT, 55, (int) (lHeight - 6.9 * scl), 20 * scl);
		drawSpeachBubble(g, pAnswer, ahfGreen, ARROW_UP_LEFT, BOTTOM);
	}

	/**
	 * Zeichnet eine Dialog passend zu einem zu übegenden Item und einem zu übergenden Quest.
	 * 
	 * @param g		   Graphics, auf dem der Dialog gezeichnet werden soll.
	 * @param pItem	   Item, zu dem dieser Dialog gezeichnet werden soll.
	 * @param pMessage Quest, was zu tun ist, wenn das Item eingesammelt bzw. freigeschaltet wird.
	 */
	public void drawItemDialog(Graphics g, String pItem, String pMessage) {
		clearPane(g);

		g.drawImage(sl.drawScaledItem(pItem, scl, scl), (int) (lWidth * 0.6), (int) (lHeight * 0.1), null);

		g.setColor(fontColor);
		g.setFont(pixelFont);
		g.drawString(pItem, (int) (lWidth * 0.6 + scl * 1.5), (int) (lHeight * 0.1 + scl * 0.7));

		cl.getAktPlayer().draw(g, avatarMoods.PLAIN_FRONT, 55, (int) (lHeight - 6.9 * scl), 20 * scl);
		
		drawSpeachBubble(g, pMessage, ahfGreen, ARROW_UP_LEFT, BOTTOM);
	}

	/**
	 * Zeichnet eine Sprechblase.
	 * 
	 * @param g
	 * @param pMessage
	 * @param pColor
	 * @param pArrowPos
	 * @param pPosition
	 */
	private void drawSpeachBubble(Graphics g, String pMessage, Color pColor, int pArrowPos, int pPosition) {
		Graphics2D g2D = (Graphics2D) g;
		setRenderingHints(g2D);
		
		g2D.setPaint(pColor);

		int w = (int) (lWidth * 0.8);
		int h = (int) (lHeight * 0.3);

		int x = 0;
		int y = 0;

		switch (pPosition) {
		case BOTTOM:
			x = (int) (lWidth - w * 1.15);
			y = (int) (lHeight - h * 1.5);
			break;
		case TOP:
			x = (int) (scl * 1.2);
			y = scl;
			break;
		}

		drawBubble(g, pArrowPos, pColor, x, y, w, h);

		g2D.setColor(fontColor);

		if (pixelFont != null) {
			g2D.setFont(pixelFont);
		} else {
			g2D.setFont(new Font("Consolas", Font.PLAIN, 20));
		}

		drawMultiString(g2D, pMessage, (int) (x + scl), (int) (y + scl), (int) (w - scl));
	}

	private void drawBubble(Graphics g, int pArrowPos, Color pColor, double x, double y, double pWidth, double pHeight) {
		int strokeThickness = 5;
		int padding = strokeThickness / 2;
		int radius = 30;
		int arrowSize = 15;
		int arrowPos = pArrowPos;

		int padX = arrowSize;
		int width = (int) (pWidth);
		int height = (int) (pHeight);

		Graphics2D g2D = (Graphics2D) g.create((int) x, (int) y, lWidth, lHeight);
		setRenderingHints(g2D);

		RoundRectangle2D.Double rect = new RoundRectangle2D.Double(padX, padding, width, height, radius, radius);
		Area bubble = combineTipAndRect(rect, radius, arrowSize, arrowPos, padX);

		g2D.setColor(pColor);
		g2D.fill(bubble);
		g2D.setStroke(new BasicStroke(4));
		g2D.setColor(fontColor);
		g2D.draw(bubble);
	}
	
	/**
	 * Zeichnet eine schöne Sprechblase mit geschwungenem Pfeil. 
	 * Die Methode gibt einen Pfad in Form der Sprechblase zurück.
	 * 
	 * @deprecated Die Methode malt zwar eine durchaus schönere Sprechblase als 
	 * {@link #drawBubble(Graphics, int, Color, double, double, double, double)}, jedoch 
	 * sind ihre Parameter nur schwer zu verändern. 
	 * <p> 
	 * Der bevorzugte Weg, eine Sprechblase zu zeichnen, ist durch Aufruf der obigen Methode.
	 * 
	 * @param pScale  Größeneinheit bzw. Skalierungsfaktor, mit der die Sprechblase vergrößert werden soll.
	 * @param x		  <code>x</code>-Koordinate vom Startpunkt der Sprechblase. 
	 * 				  Der Startpunkt ist die obere linke Ecke mit dem Pfeil.
	 * @param y		  <code>y</code>-Koordinate vom Startpunkt der Sprechblase. 
	 * 				  Der Startpunkt ist die obere linke Ecke mit dem Pfeil.
	 * @param pWidth  Breite der Sprechblase.
	 * @param pHeight Höhe der Sprechblase.
	 * @return Pfad mit der Sprechblase. Der Pfad ist mit keiner Farbe gefüllt!
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private GeneralPath drawOrnateBubble(double pScale, int x, int y, int pWidth, int pHeight) {
		int s = (int) (pScale);
		int w = pWidth;
		int h = pHeight;
		
		GeneralPath path = new GeneralPath();
		path.moveTo ( 5 * s + x, 10 * s + y);
		path.curveTo( 5 * s + x, 10 * s + y,  7 * s + x, 5 * s + y,  0     + x,  0     + y);
		path.curveTo( 0 * s + x,  0     + y, 12 * s + x, 0     + y, 12 * s + x,  5 * s + y);
		path.curveTo(12 * s + x,  5 * s + y, 12 * s + x, 0     + y, 20 * s + x,  0     + y);
		path.lineTo (w - 10 + x,  0     + y);
		path.curveTo(w - 10 + x,  0     + y,  w     + x, 0     + y, w      + x, 10     + y);
		path.lineTo (w      + x, h - 10 + y);
		path.curveTo(w      + x, h - 10 + y,  w     + x, h     + y, w - 10 + x,  h     + y);
		path.lineTo (15 * s + x, h      + y);
//		path.curveTo(15 * a + x, h      + y,  5 * a + x, h     + y,  5 * a + x, h - 10 + y);
		path.curveTo(10 * s + x, h      + y,  5 * s + x, h     + y,  5 * s + x, h - 15 + y);
		path.lineTo ( 5 * s + x, 15 * s + y);
		path.closePath();
		
		return path;
	}

	private Area combineTipAndRect(RoundRectangle2D.Double rect, int radius, int arrowSize, int arrowPos, int paddingX) {
		Polygon arrow = new Polygon();

		int padX = paddingX;

		int xMax = (int) (rect.getMaxX());
		int yMin = (int) (rect.getMinY());

		switch (arrowPos) {
		case ARROW_UP_LEFT:
			arrow.addPoint(padX + radius, yMin);
			arrow.addPoint(padX - arrowSize, yMin);
			arrow.addPoint(padX + 2, (int) (yMin + arrowSize));
			break;
		case ARROW_HALF_LEFT:
			arrow.addPoint(padX + 2, (int) (yMin + radius / 2.5));
			arrow.addPoint(padX - arrowSize, (int) (yMin + radius / 2 + arrowSize));
			arrow.addPoint(padX + 2, (int) (yMin + radius / 2 + arrowSize));
			break;
		case ARROW_UP_RIGHT:
			arrow.addPoint(xMax - radius, yMin);
			arrow.addPoint(xMax + arrowSize, yMin);
			arrow.addPoint(xMax - 2, (int) (yMin + arrowSize));
			break;
		case ARROW_HALF_RIGHT:
			arrow.addPoint(xMax - 2, (int) (yMin + radius / 2.5));
			arrow.addPoint(xMax + arrowSize, (int) (yMin + radius / 2 + arrowSize));
			arrow.addPoint(xMax - 2, (int) (yMin + radius / 2 + arrowSize));
			break;
		}

		Area area = new Area(rect);
		area.add(new Area(arrow));

		return area;
	}

	private void drawMultiString(Graphics2D g, String text, int x, int y, int lineWidth) {
		FontMetrics m = g.getFontMetrics();
		int buff = 4;
		int lineHeight = m.getHeight() + buff * -1;

		if (!text.contains("\n")) {
			if (m.stringWidth(text) < lineWidth) {
				g.drawString(text, x, y);
			} else {
				String[] words = text.split(" ");
				String currentLine = words[0];
	
				for (int i = 1; i < words.length; i++) {
					if (m.stringWidth(currentLine + words[i]) < lineWidth) {
						currentLine += " " + words[i];
					} else {
						y = drawLineAndIncreaseY(g, currentLine, lineHeight, x, y);
						currentLine = words[i];
					}
				}
	
				if (currentLine.trim().length() > 0) {
					g.drawString(currentLine, x, y);
				}
			}
		} else {
			if (m.stringWidth(text) < lineWidth) {
				for(String line : text.split("\n")) {
					y = drawLineAndIncreaseY(g, line, lineHeight, x, y);
				}
			} else {
				String[] words = text.split(" ");
				String currentLine = words[0];
				
				for (int i = 1; i < words.length; i++) {
					int textWidth = m.stringWidth(currentLine + words[i]);
					
					if (textWidth < lineWidth && !words[i].contains("\n")) {
						if(!currentLine.equals("")) {
							currentLine += " " + words[i];
						} else {
							currentLine += words[i];
						}
					} else if (textWidth < lineWidth && words[i].contains("\n")) {
						int lineBreak = words[i].indexOf("\n");
						int lastBreak = words[i].lastIndexOf("\n");
						
						if(lineBreak == lastBreak) {
							String[] l = words[i].split("\n");
							currentLine += " " + l[0];
							
							y = drawLineAndIncreaseY(g, currentLine, lineHeight, x, y);
							currentLine = "";
						} else {
							y = drawLineAndIncreaseY(g, currentLine, lineHeight, x, y);
							
							String[] l = words[i].split("\n");
							currentLine = l[1];
							
							y = drawLineAndIncreaseY(g, currentLine, lineHeight, x, y);
							currentLine = "";
						}
					} else if(textWidth > lineWidth && words[i].contains("\n")) {
						y = drawLineAndIncreaseY(g, currentLine, lineHeight, x, y);
						
						currentLine = words[i].replace("\n", "");
						y = drawLineAndIncreaseY(g, currentLine, lineHeight, x, y);
						currentLine = "";
					} else {
						y = drawLineAndIncreaseY(g, currentLine, lineHeight, x, y);
						currentLine = words[i];
					}
				}
				
				if (currentLine.trim().length() > 0) {
					g.drawString(currentLine, x, y);
				}
			}
			
		}
	}
	
	private int drawLineAndIncreaseY(Graphics g, String pCurrentLine, int pLineHeight, int x, int y) {
		g.drawString(pCurrentLine, x, y);
		return y += pLineHeight;
	}
	
	/**
	 * Setzt Rendering Hints für ein bestimmtes Graphics Panel.
	 * <p>
	 * Rendering Hints beschreiben die Art und Weise, wie das Graphics Panel gezeichnet bzw.&nbsp;gerendert werden soll.
	 * <p>
	 * <b>Hinweis:</b> Diese Methode sollte nicht für das Graphics Panel verwendet werden, auf dem der Irrgarten gezeichnet wird, 
	 * sondern wird nur für aufwendigere Zeichnungen, wie den Sprechblasen verwendet!
	 * 
	 * @param g2D Grahics, dessen Rendering Hints gesetzt werden sollen.
	 */
	private void setRenderingHints(Graphics2D g2D) {
		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2D.setRenderingHints(qualityHints);
	}

	/**
	 * Übermalt ein bestimmtes Graphics Panel mit einem leeren Rechteck in der Hintergrundfarbe vom Fenster.
	 * 
	 * @param g Graphics, das zu übermalen ist.
	 */
	private void clearPane(Graphics g) {
		g.clearRect(0, 0, lWidth, lHeight);
		g.setColor(bg);
		g.fillRect(0, 0, lWidth, lHeight);
	}
}
