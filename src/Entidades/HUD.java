/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author MarioDiaz
 */
public class HUD {
    private Jugador player;
    private BufferedImage[] sprites;
    private int iWidth;
    private int iHeight;
    private Animation animation;
    
    public HUD(Jugador j) {
        player = j;
        
        iWidth = 100;
        iHeight = 100;
        
        try {
            BufferedImage spritesheet = ImageIO.read(
                getClass().getResourceAsStream("/Resources/HUD/tilehud_2.png"));
            
            sprites = new BufferedImage[4];
            for  (int i=0; i < sprites.length; i++ ) {
                sprites[i] = spritesheet.getSubimage(i * iWidth, 0,
                        iWidth, iHeight);
            }
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        animation = new Animation();
        animation.setFrames(sprites);
    }
    
    public void draw (Graphics2D g) {
        
        g.drawImage(animation.getExactImage(1), 0, 0, null);
        //Dibujando la vida
        for (int i=0; i < player.iHealth; i++) {
            g.drawImage(animation.getExactImage(2), 80 + (i*45), -10, null);
        }        
        //Dibujando la mana
        for (int i=0; i < player.getFuel(); i++) {
            g.drawImage(animation.getExactImage(3), 80 + (i*45), 35, null);
        }       
    }
    
    
    
}
