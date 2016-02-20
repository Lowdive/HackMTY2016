/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameState;

import Main.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author MarioDiaz
 */
public class PausaState extends GameState {
    
    private Font font;
    
    public PausaState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Game Paused", 90, 90);
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
    
}
