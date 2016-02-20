/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades.Enemigos;

import Entidades.Animation;
import Entidades.Enemigo;
import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author MarioDiaz
 */
public class Particulas extends Enemigo {
    
    private ArrayList<BufferedImage[]> sprites;
    private boolean boolStart;
    private boolean boolPermanent;
    
    private int iTipo = 0;
    public static int VECTOR = 0;
    public static int GRAVITY = 1;
    public static int BOUNCE = 2;

    public static final int TRANSINICIO = 0;
    public static final int PARTICULA = 1;
    
    private int iBounceCount = 0;
    
    private final int[] numFrames = {
        4, 2, 2, 2, 2, 3, 3, 3, 3, 6, 1, 3
    };
    
    public Particulas(TileMap tm) {
        super(tm);
        
        iHealth = iMaxHealth = 1;
        iWidth = 20;
        iHeight = 20;
        iColWidth = 20;
        iColHeight = 20;
        
        iDamage = 1;
        dMoveSpeed = 5;
        
        try {
            BufferedImage spritesheet = ImageIO.read(
            getClass().getResourceAsStream(
                    "/Resources/Enemigos/DarkEnergy.gif"));
            
            sprites = new ArrayList<BufferedImage[]>();
            for(int i=0; i < 2; i++) {
                BufferedImage[] bi = new BufferedImage[numFrames[i]];
                for(int j=0; j < numFrames[i]; j++) {
                    bi[j] = spritesheet.getSubimage(j * iWidth, i * iHeight,
                            iWidth, iHeight);
                }
                sprites.add(bi);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        animation = new Animation();
        currentAction = TRANSINICIO;
        animation.setFrames(sprites.get(TRANSINICIO));
        animation.setDelay(100);
    }
    
    public void setTipo(int i) { iTipo = i;}
    public void setPermanent(boolean b) { boolPermanent = true;}
    
    public void update() {
        if(boolStart) {
            if(animation.hasPlayedOnce()) {
                animation.setFrames(sprites.get(PARTICULA));
                animation.setDelay(2);
                boolStart = false;
            }
        }
        
        if(iTipo == VECTOR) {
            iX += dDx;
            iY += dDy;
        }
        
        else if(iTipo == GRAVITY) {
            dDy += 0.2;
            iX += dDx;
            iY += dDy;
	}
        else if(iTipo == BOUNCE) {
            double dX2 = dDx;
            double dY2 = dDy;
            checkTileMapCollision();
            if(dDx == 0) {
                dDx = -dX2;
                iBounceCount++;
            }
            if(dDy == 0) {
                dDy = -dY2;
                iBounceCount++;
            }
            iX += dDx;
            iY += dDy;
        }
        //Actualizando la animacion
        animation.update();
        if(!boolPermanent) {
            if(iX < 0 || iX > tileMap.getWidth() || iY < 0 ||
                    iY > tileMap.getHeight()) {
                boolRemove = true;
            }
            if(iBounceCount == 5) {
                boolRemove = true;
            }
        }
        
    }
    
    public void draw(Graphics2D g) {
        super.draw(g);
    }
    
    
}
