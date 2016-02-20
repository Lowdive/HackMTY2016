/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameState;

import java.awt.Graphics2D;

/**
 *
 * @author MarioDiaz
 */
public abstract class GameState {
        
    protected GameStateManager gsm;
    
    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }
    
    public abstract void init();
    public abstract void draw(Graphics2D g);
    public abstract void update();
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);
}
