/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author MarioDiaz
 */
public class Jugador extends Objeto {
    
    //vars health
    public int iHealth;
    private int iMaxHealth;
    private boolean boolDead;
    
    //vars invulnerability
    private boolean boolFlinching;
    private long longFlinchTimer;
    
    //vars flying
    private double dFlySpeed;
    private boolean boolFlying;
    
    //vars sliding
    private double dSlideSpeed;
    private boolean boolSliding;
    private long longSlidingTimer;
    
    //fuel for flying
    public double dFuel;
    
    //array for sprites
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {
      4, 5, 5, 5, 4, 2, 2, 2, 2, 4, 1  
    };
    
    //Indicadores para las animaciones
    private static final int WALKING = 0;
    private static final int TRANSFUEGO = 1;
    private static final int TRANSAGUA = 2;
    private static final int TRANSTIERRA = 3;
    private static final int IDLE = 4;
    private static final int IDLEFUEGO = 5;   
    private static final int IDLEAGUA = 6; 
    private static final int FIREBALL = 7; 
    private static final int MOVIMIENTOAGUA = 8; 
    private static final int ACCIONAGUA = 9; 
    private static final int IDLETIERRA = 10; 
    private static final int MUERTE = 11;
    private static final int TRANSNORMAL = 12;
    
    public Jugador(TileMap tm) {
        super(tm);
        
        //init colission and sprite sizes
        iWidth = 60;
        iHeight = 70;
        iColHeight = 49;
        iColWidth = 49;
        
        //init movement variables
        dMoveSpeed = 7.0;
        dMaxSpeed = 7.5;
        dStopSpeed = 0.8;
        dStopJumpSpeed = 0.3;
        dFallSpeed = 0.45;
        dJumpStart = -7;
        dFlySpeed = .5;
        dSlideSpeed = 4;
        
        //init energy
        dFuel = 5;
        
        //init health
        iHealth = iMaxHealth = 5;
        
        //loading sprites and saving them in sprites arraylist
        try {
            BufferedImage spritesheet = ImageIO.read(
            getClass().getResourceAsStream(
                    "/Resources/Jugador/hozmy2.png"));
            
            sprites = new ArrayList<BufferedImage[]>();
            for(int i=0; i < 11; i++) {
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
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(100);
        
    }
    
    public void stop() {
        boolLeft = boolRight = boolUp = boolDown = 
                boolFlying = boolFlinching = false;
    }
    
    public void setDead() {
        iHealth = 0;
        stop();
    }
    
    public void setFlying(boolean b) {
        this.boolFlying = b;
    }
    
    public void setSliding(boolean b) {
        this.boolSliding = b;
        longSlidingTimer = System.nanoTime();
    }
    
    public int getFuel() {
        return (int) Math.round(dFuel);
    }
    
    public void reset() {
        iHealth = iMaxHealth;
        boolFacingRight = true;
        currentAction = IDLE;
        stop();
    }
    
    public void hit(int damage) {
        if(boolFlinching) return;
        iHealth -= damage;
        if (iHealth < 0) iHealth = 0;
        if (iHealth == 0) boolDead = true;
        boolFlinching = true;
        longFlinchTimer = System.nanoTime();
    }
    
    private void getNextPosition() {
        if(boolSliding){
            if(boolFacingRight){
                dDx += dSlideSpeed;
            } else {
                dDx -= dSlideSpeed;
            }
        } else {
            dDx = dDx;
        }
        //movimiento
        if(boolLeft) {
            dDx -= dMoveSpeed;
            if(dDx < -dMaxSpeed) {
                dDx = -dMaxSpeed;
            }
        }
        else if(boolRight) {
            dDx += dMoveSpeed;
            if(dDx > -dMaxSpeed) {
                dDx = dMaxSpeed;
            }
        }
        else {
            if(dDx > 0) {
                dDx -= dStopSpeed;
                if(dDx < 0) {
                    dDx = 0;
                }
            }
            else if(dDx < 0) {
                dDx += dStopSpeed;
                if (dDx > 0) {
                    dDx = 0;
                }
            }
        }
        
        //flying
        if(boolFlying) {
            if(dFuel > 0) {
                dFuel -= .2;
                dDy -= dFlySpeed;
            }
        } else if(boolFalling) {
            dDy += dFallSpeed;
            if(dDy < 0) {
                dDy += dStopJumpSpeed;
            }
        }
    }
    
    public void checkAttack(ArrayList<Enemigo> enemigos) {
        //Recorriendo los enemigos
        for (int i=0; i < enemigos.size(); i++) {
            Enemigo e = enemigos.get(i);
            
            if(intersects(e)) {
                hit(e.getDamage());
            }
        }        
    }
    
    public void update() {
        getNextPosition();
        checkTileMapCollision();
        setPosition(dXtemp, dYtemp);
        if(boolFlying == false){
            if(dFuel < 5){
                dFuel += .03;
            }
        }
        
        if(boolSliding){
            long elapsed = (System.nanoTime() - longSlidingTimer) / 1000000;
            if(elapsed > 100) {
                boolSliding = false;
            }
        }
        
        //checking invulnerability
        if (boolFlinching) {
           long elapsed = (System.nanoTime() - longFlinchTimer) / 1000000;
           if (elapsed > 500) {
               boolFlinching = false;
           }
        }
        animation.update();
    }
    
    public void draw (Graphics2D g) {
        setMapPosition();
        
        //dibujar al jugador
        if(boolFlinching) {
            long elapsed = (System.nanoTime() - longFlinchTimer) / 1000000;
            if(elapsed / 100 % 2 == 0) {
                return;
            }
        }
        super.draw(g);
    }
    
}
