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
import java.util.ArrayList;
import javax.imageio.ImageIO;

//Enemigo basico que se mueve de pared a pared

/**
 *
 * @author MarioDiaz
 */
public class Mosseth extends Enemigo {
    
    private _PERSONAJEPRINCIPAL_ osmy;
    
    private boolean boolActivo;
    public boolean boolAtaqueBrinco;
    private boolean boolTransBrinco;
    private boolean boolArriba_PERSONAJEPRINCIPAL_;
    
    private double dYtemp;
    
    /*arrPasos es el patron de ataques que va a realizar
    Ataques:
    Idle 0 = El boss esta en IDLE
    Ataque 1= Va a brincar, escoger una posicion random y aplastar lo que este
                en el suelo.
    Ataque 2= Carga energia y lanza bolas de fuego alrededor del mapa.
    Ataque 3= Gira con sus cuchillas y se mueve de forma horizontal
    
    */
    private int[] arrAcciones ={0, 1, 0 , 2 , 0, 3};
    
    private int iAccion;
    private int iAccionConta;
    
    //Arreglo de las animaciones
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {
        3, 3, 3, 1, 3, 5, 3, 5
    };
    
    //Indicadores para las animaciones
    private static final int IDLEAGUA = 0;
    private static final int IDLETIERRA = 1;
    private static final int IDLEFUEGO = 2;
    private static final int BRINCANDO = 3;
    private static final int TRANSBRINCANDO = 4;
    private static final int DISPARANDO = 5;
    private static final int TRANSGIRAR = 6;   
    private static final int GIRANDO = 7;     
    
    //arreglo de enemigos
    private ArrayList<Enemigo> enemigos;
    

    public Mosseth(TileMap tm, _PERSONAJEPRINCIPAL_ o, ArrayList<Enemigo> enemigos) {
        super(tm);
        osmy = o;
        this.enemigos = enemigos;
        
        dMoveSpeed = 0.4;
        dMaxSpeed = 0.4;
        dMaxFallSpeed = 1.0;
        dFallSpeed = 0.1;
        dStopJumpSpeed = 0.3;
        
        boolAtaqueBrinco = false;
        boolTransBrinco = false;
        
        iWidth = 180;
        iHeight = 180;
        iColWidth = 150;
        iColHeight = 170;
        
        iHealth = iMaxHealth = 50;
        iDamage = 1;
        
        dMoveSpeed = 5.0;
        
        //Cargar los sprites;
        try {
            BufferedImage spritesheet = ImageIO.read(
            getClass().getResourceAsStream(
                    "/Resources/Enemigos/mossethtile.png"));
            
            sprites = new ArrayList<BufferedImage[]>();
            for(int i=0; i < 8; i++) {
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
        currentAction = IDLETIERRA;
        animation.setFrames(sprites.get(IDLETIERRA));
        animation.setDelay(200);
        
        boolFacingRight = true;
        
        iAccion = 0;
        iAccionConta = 0;
        
    }
        
    public void setActivo() { boolActivo = true; }
    
    public int getHealth() { return iHealth; }
    
    public void update() {
        
        //Checar la vida de mosseth
        if(iHealth == 0) { return; }
        
        //Resetear el patron de acciones
        if(iAccion == arrAcciones.length) {
            iAccion = 0;
        }

        //Checar el flinching
        if (boolFlinching) {
            long elapsed = (System.nanoTime() - longFlinchTimer) / 1000000;
            if ( elapsed > 400) {
                boolFlinching = false;
            }
        }
        
        iX += dDx;
        iY += dDy;
        
        animation.update();
        
   //     if(!boolActivo) { return; }
        
        ///////////
        ///Acciones
        ///////////
        
        //IDLETIERRA
        if(arrAcciones[iAccion] == 0) {
            if(currentAction != IDLETIERRA) {
                currentAction = IDLETIERRA;
                animation.setFrames(sprites.get(IDLETIERRA));
                animation.setDelay(50);
                iWidth = 180;
            }
            iAccionConta++;
            if(iAccionConta == 100) {
                iAccion++;
                iAccionConta = 0;
                boolRight = boolLeft = false;
                
            }
            dDx = 0;
        }
        
        //Ataque Brincando
        if(arrAcciones[iAccion] == 1) {
            iAccionConta++; 
            
            if(iAccionConta > 1 && iAccionConta < 60) {
                dDy = -6.5;
                if(currentAction != BRINCANDO) {
                    currentAction = BRINCANDO;
                    animation.setFrames(sprites.get(BRINCANDO));
                    animation.setDelay(50);
                    iWidth = 180;
                }
            }
            if(iAccionConta > 60 && iAccionConta < 250) {
                dDy = 0;
                if(currentAction != IDLEAGUA) {
                    currentAction = IDLEAGUA;
                    animation.setFrames(sprites.get(IDLEAGUA));
                    animation.setDelay(50);
                    iWidth = 180;
                }
                if(this.getX() != osmy.getX()) {
                    
                    if(osmy.getX() > (this.getX() + 10)){
                            dDx = 3;
                    }
                    else {
                            dDx = -3;
                    }
                }
                else {
                    dDx = osmy.dDx;
                    //if((osmy.getX() - ))
                }
            }
            if(iAccionConta > 250 && iAccionConta < 283 ) {
                boolAtaqueBrinco = true;
                dDx = 0;
                dDy = 12;
            }
            if(iAccionConta == 283) {
                dDy = 0;
            }   
            if(iAccionConta == 285) {
                boolAtaqueBrinco = false;
                dDy = 0;
                iAccion++;
                iAccionConta = 0;
                boolRight = boolLeft = false;
            }
        }
        //DISPARANDO
        if(arrAcciones[iAccion] == 2) {
            if(iAccionConta == 0) {
                dYtemp = this.getY();
            }
            
            iAccionConta++;
            if(iAccionConta > 1 && iAccionConta < 30) {
                if(currentAction != IDLEFUEGO) {
                    currentAction = IDLEFUEGO;
                    animation.setFrames(sprites.get(IDLEFUEGO));
                    animation.setDelay(70);
                    iWidth = 180;
                }
                int xMitad = GamePanel.WIDTH / 2;
                int yMitad = GamePanel.HEIGHT / 2;
                
                if(this.getX() != xMitad) {
                    if(this.getX() < xMitad){
                        dDx = 5;
                    }
                    else {
                        dDx = -5;
                    }
                }
                else {
                    dDx = 0;
                }
                
                if(this.getY() != yMitad) {
                    if(this.getY() < yMitad) {
                        dDy = 5;
                    }
                    else {
                        dDy = -5;
                    }
                }
                else {
                    dDy = 0;
                }
            }
            
            if(iAccionConta >= 30 && iAccionConta % 20 == 0) {
                dDx = 0;
                dDy = 0;
                if(currentAction != DISPARANDO) {
                    currentAction = DISPARANDO;
                    animation.setFrames(sprites.get(DISPARANDO));
                    animation.setDelay(70);
                    iWidth = 180;
                }
                Particulas particula = new Particulas(tileMap);
                particula.setPosition(iX, iY);
                particula.setVector(3 * Math.sin(iAccionConta / 32),
                        3 * Math.cos(iAccionConta / 32));
                particula.setTipo(Particulas.BOUNCE);
                enemigos.add(particula);
            }
            if(iAccionConta > 200 && iAccionConta < 239) {
                dDy = 5;
                if(this.getY() == dYtemp) {
                    dDy = 0;
                }
            }
            if(iAccionConta == 239) {
                dDy = 0;
                iAccion++;
                iAccionConta = 0;
                boolRight = boolLeft = false;
                
            }
            dDx = 0;
        }
        
        //Girando
        if(arrAcciones[iAccion] == 3) {
            iAccionConta++;
            if(iAccionConta > 1 && iAccionConta < 30) {
                if(currentAction != IDLETIERRA) {
                    currentAction = IDLETIERRA;
                    animation.setFrames(sprites.get(IDLETIERRA));
                    animation.setDelay(100);
                    iWidth = 180;
                }
                dDy = 2;
            }
            if(iAccionConta == 30) {
                dDx = 4;
            }
            if(iAccionConta > 30 && iAccionConta < 400) {
                dDy = 0;
                if(this.getX() == 950) {
                    dDx = -4;
                }
                else if(this.getX() == 200) {
                    dDx = 4;
                }
                
                if(currentAction != GIRANDO) {
                    currentAction = GIRANDO;
                    animation.setFrames(sprites.get(GIRANDO));
                    animation.setDelay(100);
                    iWidth = 180;
                }
                
            }
            if(iAccionConta >= 400 && iAccionConta < 429) {
                dDx = 0;
                if(currentAction != IDLETIERRA) {
                    currentAction = IDLETIERRA;
                    animation.setFrames(sprites.get(IDLETIERRA));
                    animation.setDelay(100);
                    iWidth = 180;
                }
                dDy = -2;
            }
            if(iAccionConta == 329) {
                dDy = 0;
                iAccion = 0;
                iAccionConta = 0;
            }
        }
    }
    
    public void draw(Graphics2D g) {        
        setMapPosition();
        super.draw(g);
    }
    
}
