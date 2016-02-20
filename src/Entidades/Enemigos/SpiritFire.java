/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades.Enemigos;

import Entidades.Animation;
import Entidades.Enemigo;
import Entidades._PERSONAJEPRINCIPAL_;
import Main.GamePanel;
import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

//Enemigo basico que se mueve de pared a pared

/**
 *
 * @author MarioDiaz
 */
public class SpiritFire extends Enemigo {
    
    private BufferedImage[] sprites;
    private _PERSONAJEPRINCIPAL_ osmy;
    private boolean boolActivo;

    public SpiritFire(TileMap tm, _PERSONAJEPRINCIPAL_ o) {
        super(tm);
        osmy = o;
        
        dMoveSpeed = 0.4;
        dMaxSpeed = 0.4;
        dMaxFallSpeed = 1.0;
        dFallSpeed = 0.1;
        dStopJumpSpeed = 0;
        
        iWidth = 60;
        iHeight = 90;
        iColWidth = 40;
        iColHeight = 70;
        
        iHealth = iMaxHealth = 2;
        iDamage = 1;
        
        boolFalling = true;        
        //Cargar los sprites;
        try{
            BufferedImage spritesheet = ImageIO.read(
                getClass().getResourceAsStream(
                        "/Resources/Enemigos/firetile.png"));
            
            sprites = new BufferedImage[5];
            for (int i=0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * iWidth, 0,
                        iWidth, iHeight);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(200);
        
        boolRight = true;
        boolFacingRight = true;
        
    }
    
    
    public void update() {
        
        if(!boolActivo) {
            if(Math.abs(osmy.getX() - iX) < GamePanel.WIDTH) {
                boolActivo = true;
            }
            return;
        }
        //Checar el flinching
        if (boolFlinching) {
            long elapsed = (System.nanoTime() - longFlinchTimer) / 1000000;
            if ( elapsed > 400) {
                boolFlinching = false;
            }
        }
        
        //hace el update de la posicion
        //getNextPosition();
        //checkTileMapCollision();
        //setPosition(dXtemp, dYtemp);
        
       
        //update de la animacion
        animation.update();
    }
    
    public void draw(Graphics2D g) {        
        setMapPosition();
        super.draw(g);
    }
    
}
