/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameState;

import Main.GamePanel;
import TileMap.Background;
import TileMap.Tile;
import TileMap.TileMap;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author MarioDiaz
 */
public class Boss1State extends GameState {
    
    //creando variables tileMap y background
    private TileMap tileMap;
    private Background bg;
    
    // EVENTOS
    /*Se utilizan eventos para poder dar instrucciones al usuario al inicio
    de determinado mapa, determinar que hacer cuando se termina el juego, 
    lo que se debe hacer cuando el jugador muere. Aqui tambien se inicializan
    los efectos de trancision entre cada nivel y evento.*/
    
    //La booleana bloquear control bloquea el control del usuario sobre el pers.
    private boolean boolBloquearControl = false;
    private int iEventoConta;
    private ArrayList<Rectangle> arrTransiciones;
    private boolean boolEventoStart;
    private boolean boolEventoFinish;
    private boolean boolEventoDead;
    
    public Boss1State(GameStateManager gsm) {
        super(gsm);
        init();
    }
    
    public void init() {
        //Inicializando el tile map, cargandolo, dandole posicion, etc.
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Resources/Mapas/tilesetf.png");
        tileMap.loadMap("/Resources/Mapas/niveljefef.txt");
        tileMap.setPosition(0, 0);
        tileMap.setScroll(0.07);
        
        //Estableciendo el background
        bg = new Background("/Resources/Fondos/BGjefe.png", 1);
        bg.setVector(0, 0);
        
        //Inicializando el evento de inicio
        boolEventoStart = true;
        arrTransiciones = new ArrayList<Rectangle>();
        //eventoStart();
    }

    @Override
    public void draw(Graphics2D g) {
        //Dibujando el background
        bg.draw(g);
        
        //dibujando tilemap
        tileMap.draw(g);
        
        //dibujando las transiciones
        g.setColor(Color.BLACK);
        for(int i = 0; i < arrTransiciones.size(); i++) {
            g.fill(arrTransiciones.get(i));
        }
    }

    @Override
    public void update() {
       
    }
    
    @Override
    public void keyPressed(int k) {
        
    }

    @Override
    public void keyReleased(int k) {
        
    }
    
    /*
        MANEJO DE LOS EVENTOS
    */
    

}

    

    
