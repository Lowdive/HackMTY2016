/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameState;
import TileMap.Background;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author MarioDiaz
 */
public class MenuState extends GameState{
    
    private Background bgMenu, bgFondo;
    
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    
    private int iCurrentChoice = 0; 
    private String[] options = {
        "Inicio",
        "Jefe 1",
        "Salir"
        
    };
    
    private Color titleColor;
    private Font titleFont;
    private Font font;
    
    
    public MenuState(GameStateManager gsm) {
        super(gsm);
        
        try {
            bgMenu = new Background("/Resources/Fondos/pantalla-de-menu.png", 1);
            bgMenu.setVector(0, 0);
            
            //bgFondo = new Background("/Resources/bgNivel1.png", 1);
            bgFondo = new Background("/Resources/Fondos/limboForrest.jpg", 1);
            bgFondo.setVector(-0.3, 0);
            
            titleColor = new Color(128,0,0);
            titleFont = new Font("Century gothic", Font.PLAIN,52);
            
            font = new Font("Arial", Font.PLAIN, 32);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
    }

    @Override
    public void draw(Graphics2D g) {
        //draw background
        bgFondo.draw(g);
        bgMenu.draw(g);
        
        //draw title
        
        g.setColor(titleColor);
        
        //Draw menu options
        g.setFont(font);
        for(int i=0; i < options.length; i++) {
            if (i == iCurrentChoice) {
                g.setColor(Color.BLACK);
            }
            else {
                g.setColor(Color.RED);
            }
            
            if(i == 1) {
                g.drawString(options[i], 590,310 + i * 90);
            }
            else {
                g.drawString(options[i], 602,310 + i * 90);
            }
        }
    }

    @Override
    public void update() {
        bgMenu.update();
        bgFondo.update();
    }
    
    private void select() {
        if(iCurrentChoice == 0) {
            gsm.setState(GameStateManager.NIVEL1STATE);
        }
        if(iCurrentChoice == 1) {
            gsm.setState(GameStateManager.BOSS1STATE);
        }
        if(iCurrentChoice == 2) {
            //quit
            System.exit(0);
        }
    }
    
    @Override
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ENTER) {
            select();
        }
        if(k == KeyEvent.VK_UP) {
            iCurrentChoice--;
            if(iCurrentChoice == -1) {
                iCurrentChoice = options.length -1;
            }
        }
        if(k == KeyEvent.VK_DOWN) {
            iCurrentChoice++;
            if(iCurrentChoice == options.length) {
                iCurrentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(int k) {
    }
}
