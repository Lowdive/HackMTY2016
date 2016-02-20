/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/**
 *
 * @author MarioDiaz
 */
public class Fireball extends Objeto {

    private boolean boolHit;
    private boolean boolRemove;
    private BufferedImage[] sprites;
    private BufferedImage[] hitSprites;
    
    
   
    public Fireball(TileMap tm, boolean right) {
        
        super(tm);
        
        boolFacingRight = right;
        
        dMoveSpeed = 3.8;
        if(right) dDx = dMoveSpeed;
        else dDx = - dMoveSpeed;
        
        iWidth = 30;
        iHeight = 30;
        iColWidth = 14;
        iColHeight = 14;
        
        // cargar los sprites
        
        try {
            BufferedImage spritesheet = ImageIO.read(
                getClass().getResourceAsStream("/ResourcesTest/fireball.gif"));
            
            sprites = new BufferedImage[4];
            for( int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * iWidth,
                        0, iWidth, iHeight);
            }
            
            hitSprites = new BufferedImage[3];
            for( int i = 0; i < hitSprites.length; i++) {
                hitSprites[i] = spritesheet.getSubimage(i * iWidth,
                        iHeight, iWidth, iHeight);
            }
            
            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(70);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
  
    //funcion que checa si la bola de fuego choca con algo
    public void setHit() {
        if(boolHit) return;
        boolHit = true;
        animation.setFrames(hitSprites);
        dDx = 0;
    }
    
    public boolean shouldRemove() { return boolRemove; }
    
    public void update() {
        checkTileMapCollision();
        setPosition(dXtemp, dYtemp);
        
        if(dDx == 0 && !boolHit) {
            setHit();
        }
        animation.update();
        
        if(boolHit && animation.hasPlayedOnce()) {
            boolRemove = true;
        }
    }
    
    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);
        
    }
    
}
