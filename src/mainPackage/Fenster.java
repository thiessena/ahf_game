package mainPackage;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import graphicsDrawer.CorporateDesign;
import spriteLoader.DataReader;
/**
 * Erstellt ein leeres Fenster.
 * 
 * @author Andreas Thiessen
 * @version 27.01.2020
 */
public class Fenster extends JFrame implements ActionListener, KeyListener
{ 
    JPanel zeichenfeld;
    Timer timer;
    boolean animationLaeuft;
    int frameRate;
    int width=500; 
    int height=400;
    boolean isLoaded = true;
    DataReader mDataReader;
    
    /**
     * Erstellt ein Fenster.
     * Ruft die draw-Methoden aus den Unterklassen {@link mainPackage.ClientLabyrinth} und {@link mainPackage.Labyrinth} auf.
     */
    public Fenster()
    {
        
        this.setSize(width,height);
        
        this.setTitle("Maze");
        this.setLocationRelativeTo(null);
        setVisible(true);

        animationLaeuft = true;
        frameRate = 30;
        timer = new Timer(1000/frameRate, this);
        timer.start(); 
        //timer.setInitialDelay(30);
        
//        Thread t = new Thread(() -> {
//        		mFileReader = FileReader.getInstance("img/", "avatar");
//        		mFileReader.readFiles();
//        		SpriteLoader.getInstance();
//        		LabyrinthDrawer.getInstance();
//        		
//        		isLoaded = true;
//        });
//        t.start();
        zeichenfeld = new JPanel(){
            public void paintComponent(Graphics g){
                if(!isLoaded) {
//                	g.drawImage(null, 0, 0, null);
                	g.setColor(CorporateDesign.FONT_COLOR);
                	g.drawString("LadeScreen", 10, 10);
                } else {
                	draw(g);
                }
            }        
        }; 
        addKeyListener(this);
        
        this.setLayout(new BorderLayout());
        this.add(zeichenfeld, BorderLayout.CENTER);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                animationLaeuft = false; 
                System.exit(0);
            }
        });
    }
    
    /**
     * Setz die Bildwiederholrate (fps)
     * 
     * @param frameRate zu setzende Bildwiederholrate
     * @see #getFrameRate()
     */
    public void setFrameRate(int frameRate){
        this.frameRate = frameRate;
        timer.setDelay(1000/frameRate);
    }
    
    /**
     * Gibt die Bildwiederholrate zurück
     * 
     * @return Bildwiederholrate
     */
    public int getFrameRate(){
        return frameRate;
    }
    
    /**
     * Obligatorische Methode zum Zeichnen von Elementen. 
     * Wird von den Unterklassen {@link mainPackage.ClientLabyrinth} 
     * und {@link mainPackage.Labyrinth} überschrieben.
     * 
     * @param g Graphics, auf dem gezeichnet werden soll. Wird im Konstruktor gesetzt.
     */
    public void draw(Graphics g){
    }
    
    
    public void actionPerformed(ActionEvent e){
        repaint();
    }
    
    public void windowClosing(java.awt.event.WindowEvent e){
        System.exit(0);
    }
    
    /** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) {
        //System.out.println("KEY TYPED: "+e.getKeyChar());
    }

    /** Handle the key-pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
        //System.out.println( "KEY PRESSED: "+e.getKeyChar());
    }

    /** Handle the key-released event from the text field. */
    public void keyReleased(KeyEvent e) {
        //System.out.println( "KEY RELEASED: "+e.getKeyChar());
    }
    
    
    public int mouseX(){
        return (int)mousePoint().getX();
    }
    
    public int mouseY(){
        return (int)mousePoint().getY();
    }
    
    public Point mousePoint(){
        Point p = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(p, zeichenfeld);
        return p;
    }
    
     public static void main(String[] args){
        //System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Fenster();
            }
        });
        System.exit(0);
    }    
}
