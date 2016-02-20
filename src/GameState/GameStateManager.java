/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameState;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author MarioDiaz
 */
public class GameStateManager {
    private GameState[] gameStates;
    private int iCurrentState;
    
    private PausaState pausaState;
    public boolean boolPausado;
    
    public static final int NUMSTATES = 10; //Numero de estados de juego
    public static final int MENUSTATE = 0; 
    public static final int NIVEL1STATE = 1;
    public static final int BOSS1STATE = 2;
    
    
    public GameStateManager() {        
        gameStates = new GameState[NUMSTATES];
        
        pausaState = new PausaState(this);
        boolPausado = false;
        
        
       iCurrentState = MENUSTATE;
       loadState(iCurrentState);
    }
    
    private void loadState(int state) {
        if(state == MENUSTATE) {
            gameStates[state] = new MenuState(this);
        }
        else if(state == NIVEL1STATE) {
            gameStates[state] = new Nivel1State(this);
        }
        else if(state == BOSS1STATE) {
            gameStates[state] = new Boss1State(this);
        }
    }
    
    private void unloadState(int state) {
        gameStates[state] = null;
    }
    
    public void setPausado(boolean b) { boolPausado = b; }
    
    public void setState(int state) {
        unloadState(iCurrentState);
        iCurrentState = state;
        loadState(iCurrentState);
    }
    
    public void update(){
        if(boolPausado) {
            pausaState.update();
            return;
        }
        if(gameStates[iCurrentState] != null) gameStates[iCurrentState].update();
    }
    
    public void draw(Graphics2D g) {
        if(boolPausado) {
            pausaState.draw(g);
                return;
	}
        if(gameStates[iCurrentState] != null) {gameStates[iCurrentState].draw(g);}
            else {
		g.setColor(java.awt.Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
	}    
    }
    
    public void keyPressed(int k) {
        gameStates[iCurrentState].keyPressed(k);
    }
    
    public void keyReleased(int k) {
        gameStates[iCurrentState].keyReleased(k);
    }

    
    
}
