/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import Entidades.Enemigos.Mosseth;
import TileMap.TileMap;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author MarioDiaz
 */
public class _PERSONAJEPRINCIPAL_ extends Objeto {
    
    //variables de jugador
    public int iHealth;
    private int iMaxHealth;
    
    //Variables de movimiento especiales de jugador
    private boolean boolDoubleJump;
    private boolean boolDoingDoubleJump;
    private double dDoubleJumpStart;
    private int iDoubleJumpCost;
    
    //Variables de energia del jugador
    public int iEnergia;
    public int iMana;
    private int iMaxEnergia;
    
    private boolean boolDead;//variable para saber si esta muerto
    
    //Variables para la invinsibilidad
    private boolean boolFlinching;
    private long longFlinchTimer;
    
    //Variables boolean de los elementos
    public boolean boolWater;
    public boolean boolFire;
    public boolean boolEarth;
    
    //Variables para las transformaciones
    private boolean boolTransWater;
    private boolean boolTransFire;
    private boolean boolTransEarth;
    private boolean boolTransNormal;
    //teletransportacion
    private boolean boolTeleporting;
    //Variable de costo de transformacion
    public int iTransCost;
    
    //Variables de chorro de agua
    public boolean boolWaterpumping;
    private int iWaterpumpDamage;
    private int iWaterpumpRange;
    private int iWaterpumpCost;
    private Waterpump waterPump;
    
    //Variables de bola de fuego
    public boolean boolFiring;
    private int iFireCost;
    private int iFireballDamage;
    private ArrayList<Fireball> fireBalls;
        
    //Arreglo de las animaciones
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
    
    public _PERSONAJEPRINCIPAL_(TileMap tm) {
        super(tm);
        
        //Inicializando variables de Medida normal y Medida de colision
        iWidth = 60;
        iHeight = 70;
        iColHeight = 52;
        iColWidth = 52;
        
        //Inicializando variables de movimiento (caida, velocidad, brinco, etc);
        dMoveSpeed = 3.3;
        dMaxSpeed = 4.0;
        dStopSpeed = 0.4;
        dFallSpeed = 0.35;
        dMaxFallSpeed = 8.0;
        dJumpStart = -7;
        dStopJumpSpeed = 0.3;
        
        dDoubleJumpStart = -8;
        iDoubleJumpCost = 0;
        
        //Inicializando variable que checa hacia donde esta viendo Osmy
        boolFacingRight = true;
        
        boolWater = false;
        boolEarth = false;
        boolFire = false;
        
        //Inicializando variables de VIDA
        iHealth = iMaxHealth = 5;
        
        //Inicializando variables de ENERGIA
        iEnergia = iMaxEnergia = 2500;
        
        //Inicializando variables COSTO DE FUEGO
        iFireCost = 500;
        iFireballDamage = 5;
        
        //Teletransportando
        
        
        //Inicializanod el arreglo de bolas de fuego
        fireBalls = new ArrayList<Fireball>();
        
        //Inicializando variables COSTO DE AGUA
        iWaterpumpCost = 500;
        iWaterpumpDamage = 5;
        iWaterpumpRange = 40;
        
        //Variables booleanas para saber el tipo de transformacion a usar
        boolTransWater = false;
        boolTransFire = false;
        boolTransEarth = false;
        boolTransNormal = false;
        iTransCost = 250;
        
        //cargando los sprites y guardandolos en un arreglo de imagenes
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
        
        //Inicializando la animacion
        animation = new Animation();
        currentAction = IDLE;//Poniendo a IDLE como la animacion inicial
        animation.setFrames(sprites.get(IDLE));//Cargando Idle en las frames
        animation.setDelay(100);//Delay entre cada frame
    }
    
    public int getHealth() { return iHealth; }
    public int getMaxHealth() { return iMaxHealth; }
    public int getMaxFire() { return iMaxEnergia; }
    
    public void setFire() { boolFire = true;}
    public void setWater() { boolWater = true;}
    public void setEarth() { boolEarth = true;}
    
    public void setWaterFalse() { boolWater = false;}
    public void setEarthFalse() { boolEarth = false;}
    public void setFireFalse() { boolFire = false; }
    
    public void setWaterTrans() { boolTransWater = true;}
    public void setEarthTrans() { boolTransEarth = true;}
    public void setFireTrans() { boolTransFire = true; }
    public void setNormalTrans() { boolTransNormal = true; }
        
    public void setFiring() { boolFiring = true; }
    public void setWaterpumping() { boolWaterpumping = true; }
    
    public void setTeleporting(boolean b) { boolTeleporting = b;}  
    
    public int getMana() {
        iMana = iEnergia / 100;
        iMana = iMana / 5;
        return iMana;
    }
    
    public void stop() {
        boolLeft = boolRight = boolUp = boolDown = boolFlinching = boolJumping =
                boolWater = boolEarth = boolFire = boolDoubleJump =
                boolDoingDoubleJump = boolWaterpumping = boolFiring =
                false;
    }
    
    public void setDead() {
        iHealth = 0;
        stop();
    }
    
    public void reset() {
        iHealth = iMaxHealth;
        boolFacingRight = true;
        currentAction = IDLE;
        stop();
        
    }
    
    public void setJumping(boolean b) {
	if(b && !boolJumping && boolFalling && !boolDoingDoubleJump) {
			boolDoubleJump = true;
	}
	boolJumping = b;
    }
    
    public void checkAttack(ArrayList<Enemigo> enemigos) {
        //Recorriendo los enemigos
        for (int i=0; i < enemigos.size(); i++) {
            Enemigo e = enemigos.get(i);
            //Checar si las bolas de fuego pegaron
            for (int j=0; j < fireBalls.size(); j++) {
                if(fireBalls.get(j).intersects(e)) {
                    e.hitEnemigo(iFireballDamage);
                    fireBalls.get(j).setHit();
                    break;
                }
            }
            //Checar si el jugador choco con un enemigo

            /*Checar si el jugador esta brincando y entra en modo de piedra
            pueda atacar a los enemigos*/
            if(boolFalling && boolEarth) {
                if(intersects(e)) {
                    e.hitEnemigo(iFireballDamage);
                }
            }
            else {
                if(intersects(e)) {
                    hit(e.getDamage());
                }
            }
        }        
    }
    
    public void checkAttackBoss(Mosseth m) {
        Mosseth mo = m;
        if(boolEarth && mo.boolAtaqueBrinco) {
            if(intersects(mo)) {
                mo.hitEnemigo(iFireballDamage);
            }
        }
        else if(boolEarth) {
            
        }
        else {
            if(intersects(mo)) {
                hit(mo.getDamage());
            }
        }
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
        // cno se puede atacar a menos que este en el aire
        if((currentAction == FIREBALL) &&
                !(boolJumping || boolFalling )) {
            dDx = 0;
        }
        // brincando
        if(boolJumping && !boolFalling) {
            dDy = dJumpStart;
            boolFalling = true;
        }
        //Doble brinco
        if(boolDoubleJump && !boolWater && !boolFire && !boolEarth) {
            if(iEnergia > iDoubleJumpCost) {
                dDy = dDoubleJumpStart;
                boolDoingDoubleJump = true;
                boolDoubleJump = false;
                iEnergia -= iDoubleJumpCost;
            }
        }
        
        if(!boolFalling) boolDoingDoubleJump = false;
        
        //falling
        if(boolFalling) {
            if(boolEarth) {
               dFallSpeed = 6.0;
           }
           else {
               dFallSpeed = 0.35;
           }

            dDy += dFallSpeed;
            
            if(dDy < 0 && !boolJumping){
                dDy += dStopJumpSpeed;
            }
            if(dDy > dMaxFallSpeed) {
                dDy = dMaxFallSpeed;
            }
        }
    }
    
    public void getElementIdle() {
        //Checando el ESTADO en que debe estar osmy
       if(!boolWater && !boolEarth && boolFire) {
            if(currentAction != IDLEFUEGO) {
                currentAction = IDLEFUEGO;
                animation.setFrames(sprites.get(IDLEFUEGO));
                animation.setDelay(100);
                iWidth = 30;
           }
       }
       else if(!boolWater && !boolFire && boolEarth) {
            if(currentAction != IDLETIERRA) {
                currentAction = IDLETIERRA;
                animation.setFrames(sprites.get(IDLETIERRA));
                animation.setDelay(100);
                iWidth = 30;    
           }
           
       }
       else if(!boolFire && !boolEarth && boolWater) {
            if(currentAction != IDLEAGUA) {
                currentAction = IDLEAGUA;
                animation.setFrames(sprites.get(IDLEAGUA));
                animation.setDelay(100);
                iWidth = 30;
           }
           
       }
       else {
            if(currentAction != IDLE) {
               currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(400);
                iWidth = 30;
           }
           
       }
    }
    
    public void update() {
        //update position
        
       getNextPosition();
       checkTileMapCollision();
       setPosition(dXtemp, dYtemp);
       // checar si dejo de atacar
       if(currentAction == FIREBALL ) {
           if (animation.hasPlayedOnce()) boolFiring = false;
       }
       //Checar si se dejo de transformar en agua
       if(currentAction == TRANSAGUA ) {
           if (animation.hasPlayedOnce()) boolTransWater = false;
       }
         //Checar si se dejo de transformar en tierra
       if(currentAction == TRANSTIERRA) {
           if (animation.hasPlayedOnce()) boolTransEarth = false;
       }
       if(currentAction == TRANSFUEGO ) {
           if (animation.hasPlayedOnce()) boolTransFire = false;
       }
       //Checar si se dejo de transformar en fuego
       if(currentAction == TRANSNORMAL ) {
           if (animation.hasPlayedOnce()) boolTransNormal = false;
       }
       //Checar si se dejo de transformar a normal
       
       iEnergia += 4;//Aumentando la energia gradualmente
       
       ///BOLAS DE FUEGO
       //Disparando la bola de fuego
       if(iEnergia > iMaxEnergia) iEnergia = iMaxEnergia;
       if(boolFiring && currentAction != FIREBALL) {
           if(iEnergia > iFireCost) {
               iEnergia -= iFireCost;
               Fireball fb = new Fireball(tileMap, boolFacingRight);
               fb.setPosition(iX, iY);
               fireBalls.add(fb);
           }
       }
       //update para las bolas de fuego
       for(int i = 0; i < fireBalls.size(); i++) {
           fireBalls.get(i).update();
           if(fireBalls.get(i).shouldRemove()) {
               fireBalls.remove(i);
               i--;
           }
       }
       
       //Checar la invinsibilidad
       if (boolFlinching) {
           long elapsed = (System.nanoTime() - longFlinchTimer) / 1000000;
           if (elapsed > 1000) {
               boolFlinching = false;
           }
       }
       
       ///SET LAS ANIMACIONES///
       if(boolFiring) {
            if(currentAction != FIREBALL) {
               currentAction = FIREBALL;
               animation.setFrames(sprites.get(FIREBALL));
               animation.setDelay(100);
               iWidth = 30;
           }
       }
       else if(boolTransWater) {
           if(currentAction != TRANSAGUA) {
               currentAction = TRANSAGUA;
               animation.setFrames(sprites.get(TRANSAGUA));
               animation.setDelay(100);
               iWidth = 30;
           }
       }
       else if(boolTransFire) {
           if(currentAction != TRANSFUEGO) {
               currentAction = TRANSFUEGO;
               animation.setFrames(sprites.get(TRANSFUEGO));
               animation.setDelay(100);
               iWidth = 30;
           }
       }
       else if(boolTransEarth) {
           if(currentAction != TRANSTIERRA) {
               currentAction = TRANSTIERRA;
               animation.setFrames(sprites.get(TRANSTIERRA));
               animation.setDelay(100);
               iWidth = 30;
           }
       }
       else if(boolTransNormal) {
           if(currentAction != TRANSNORMAL) {
               currentAction = TRANSNORMAL;
               animation.setFrames(sprites.get(TRANSFUEGO));
               animation.setDelay(100);
               iWidth = 30;
           }
       }
       else if(dDy > 0){
           if(!boolTransWater && !boolTransFire && !boolTransEarth) {
                getElementIdle();
           }
       }
       //Poner la aimacion correcta cuando se esta brincando
       else if(dDy < 0) {
           if(boolWater) {
               if(currentAction != IDLEAGUA) {
                   currentAction = IDLEAGUA;
                   animation.setFrames(sprites.get(IDLEAGUA));
                   animation.setDelay(100);
               }
           }
           else if(boolFire) {
               if(currentAction != IDLEFUEGO) {
                   currentAction = IDLEFUEGO;
                   animation.setFrames(sprites.get(IDLEFUEGO));
                   animation.setDelay(100);
               }
           }
           else {
               if(currentAction != WALKING) {
               currentAction = WALKING;
               animation.setFrames(sprites.get(WALKING));
               animation.setDelay(100);
               iWidth = 30;
               }
           }
       }
       //Poner la animacin correcta cuando se esta moviendo ala derecha o izq.
       else if(boolLeft || boolRight) {
           if (boolWater) {
               if(currentAction != IDLEAGUA) {
                   currentAction = IDLEAGUA;
                   animation.setFrames(sprites.get(IDLEAGUA));
                   animation.setDelay(100);
               }
           }
           else {
               if(currentAction != WALKING) {
               currentAction = WALKING;
               animation.setFrames(sprites.get(WALKING));
               animation.setDelay(100);
               iWidth = 30;
               }
           }
           
       }
       else {
            if(!boolTransWater && !boolTransFire && !boolTransEarth) {
                getElementIdle();
           }
       }
       //dando update a la animacion
       animation.update();
       
       //Poniendo direccion a la bola de fuego
       if(currentAction != FIREBALL) {
           if(boolRight) boolFacingRight = true;
           if(boolLeft) boolFacingRight = false;
       }
    }
    
    public void draw (Graphics2D g) {
        setMapPosition();
        //dibujar las bolasd e fuego
        for(int i = 0; i < fireBalls.size(); i++) {
            fireBalls.get(i).draw(g);
        }
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
