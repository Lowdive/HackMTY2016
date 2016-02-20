
package Main;

import GameState.GameStateManager;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener{
    
    //Dimensiones del JPanel
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    
    //Thread del Juego
    private Thread thread;
    private boolean boolRunning;
    private int iFPS = 60;
    private long targetTime = 1000 / iFPS;
    
    //Imagen
    private BufferedImage image;
    private Graphics2D g;
    
    //Game state manager
    private GameStateManager gsm;
    
    //constructor
    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }
    
    public void addNotify() {
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    private void init() {
        
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        
        g = (Graphics2D) image.getGraphics();
        
        boolRunning = true;
        
        gsm = new GameStateManager();
    }
    
    @Override
    public void run() {
        
        init();
        
        long longStart;
        long longElapsed;
        long longWait;
        
        //Loop del juego
        while(boolRunning){
            update();
            draw();
            drawToScreen();
            
            longStart = System.nanoTime();
            
            longElapsed = System.nanoTime() - longStart;
            
            longWait = targetTime - longElapsed / 1000000;
            
            try {
                Thread.sleep(longWait);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    private void update() {
        gsm.update();
    }
    private void draw() {
        gsm.draw(g);
    }
    private void drawToScreen(){
        Graphics g2 = getGraphics();
        g2.drawImage(image,0,0,null);
        g2.dispose();
    }
    

    @Override
    public void keyTyped(KeyEvent key) {
    }

    @Override
    public void keyPressed(KeyEvent key) {
        gsm.keyPressed(key.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent key) {
        gsm.keyReleased(key.getKeyCode());
    }
}
