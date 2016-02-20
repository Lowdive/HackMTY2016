/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import TileMap.TileMap;


/**
 *
 * @author MarioDiaz
 */
public  class Enemigo extends Objeto {

    protected int iHealth;
    protected int iMaxHealth;
    protected boolean boolDead;
    protected int iDamage;
    protected boolean boolRemove;
    
    
    protected boolean boolFlinching;
    protected long longFlinchTimer;
    
    public Enemigo(TileMap tm) {
        super(tm);
    }
    
    public boolean isDead() { return boolDead; }
    public boolean shouldRemove() { return boolRemove; }
    
    public int getDamage() { return iDamage; }
    
    public void hitEnemigo (int damage) {
        if (boolDead || boolFlinching) return;
        iHealth -= damage;
        if (iHealth < 0) iHealth = 0;
        if (iHealth == 0) boolDead = true;
        boolFlinching = true;
        longFlinchTimer = System.nanoTime();
    }
    
    public void update() {
        
    }
    
}
