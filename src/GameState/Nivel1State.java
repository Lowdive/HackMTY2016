package GameState;

import Entidades.HUD;
import Entidades.Jugador;
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

public class Nivel1State extends GameState {
    
    //creando variables tileMap y background
    private TileMap tileMap;
    private Background bg;
    private Jugador player;
    private HUD hud;
    
    
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
    
    public Nivel1State(GameStateManager gsm) {
        super(gsm);
        init();
    }
    
    public void init() {
        //Inicializando el tile map, cargandolo, dandole posicion, etc.
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Resources/Mapas/tilesetf.png");
        tileMap.loadMap("/Resources/Mapas/NivelUno.txt");
        tileMap.setPosition(0, 0);
        tileMap.setScroll(0.07);
        
        //Estableciendo el background
        bg = new Background("/Resources/Fondos/BGBetter.png", 1);
        bg.setVector(0, 0);
        
        //init player
        player = new Jugador(tileMap);
        player.setPosition(100, 400);
        
        //init HUD
        hud = new HUD(player);
        
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
        
        player.draw(g);
        
        hud.draw(g);
        
        //dibujando las transiciones
        g.setColor(Color.BLACK);
        for(int i = 0; i < arrTransiciones.size(); i++) {
            g.fill(arrTransiciones.get(i));
        }
    }

    @Override
    public void update() {
        player.update();
        
        //Actualizando la posicion del mapa dependiendo la del personaje Osmy
        tileMap.setPosition(GamePanel.WIDTH / 2 - player.getX(),
                GamePanel.HEIGHT / 2 - player.getY());
        //Actualizando los efectos especiales del mapa
        tileMap.update();
        tileMap.fixBounds();
        
        bg.setPosition(tileMap.getX(), tileMap.getY());
        
    }
    
    @Override
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_A) {
            player.setLeft(true);
        }
        if(k == KeyEvent.VK_D) {
            player.setRight(true);
        }
        if(k == KeyEvent.VK_W) {
            player.setUp(true);
        }
        if(k == KeyEvent.VK_SPACE) {
            if(player.getFuel() > 1){
                player.setFlying(true);
            } else {
                player.setFlying(false);
            }
        }
    }

    @Override
    public void keyReleased(int k) {
        
        //Poniendo el falso las variables de movimiento cuando no se presionan.
        if(k == KeyEvent.VK_A) {
            player.setLeft(false);
        }
        if(k == KeyEvent.VK_D) {
            player.setRight(false);
            
        }
        if(k == KeyEvent.VK_W) {
            player.setUp(false);
        }
        if(k == KeyEvent.VK_S) {
            player.setSliding(true);
        }
        if(k == KeyEvent.VK_SPACE) {
            player.setFlying(false);
        }
    }
    
    /*
        MANEJO DE LOS EVENTOS
    */

    
    
}

    

    
