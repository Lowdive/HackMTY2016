/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import Main.GamePanel;
import TileMap.Tile;
import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.Rectangle;
/**
 *
 * @author MarioDiaz
 */
public abstract class Objeto {
    
    protected TileMap tileMap;
    protected int iTileSize;
    
    //Coordenadas 
    protected double dXmap;
    protected double dYmap;
    
    //Posicion y vector
    public double iX;
    public double iY;
    public double dDx;
    public double dDy;
    
    //Dimensiones
    protected int iWidth;
    protected int iHeight;
    
    //Dimensiones colision
    protected int iColWidth;
    protected int iColHeight;
    
    //colision
    protected int iCurrRow;
    protected int iCurrCol;
    protected double dXdest;
    protected double dYdest;
    protected double dXtemp;
    protected double dYtemp;
    protected boolean boolTopLeft;
    protected boolean boolBotLeft;
    protected boolean boolTopRight;
    protected boolean boolBotRight;
    
    //Animacion
    protected Animation animation;
    protected int currentAction;
    protected int previousAction;
    protected boolean boolFacingRight;
    
    //Movimiento
    protected boolean boolLeft;
    protected boolean boolRight;
    protected boolean boolUp;
    protected boolean boolDown;
    protected boolean boolJumping;
    protected boolean boolFalling;
    
    //Atributos de Movimiento
    protected double dMoveSpeed;
    protected double dMaxSpeed;
    protected double dStopSpeed;
    protected double dFallSpeed;
    protected double dMaxFallSpeed;
    protected double dJumpStart;
    protected double dStopJumpSpeed;
    
    //constructor
    
    public Objeto(TileMap tm) {
        tileMap = tm;
        iTileSize = tm.getTileSize();
    }
    
    public boolean intersects(Objeto o) {
        Rectangle r1 = getRectangle ();
        
        Rectangle r2 = o.getRectangle();
        
        return r1.intersects(r2);
    }
    
    public Rectangle getRectangle() {
        return new Rectangle((int)iX- (iColWidth/2),(int)iY - (iColHeight/2),
        iColWidth, iColHeight);
        
        
   
    }
    
    public void calculateCorners(double x, double y) {

        int leftTile = (int)(x - iColWidth / 2) / iTileSize;
        int rightTile = (int)(x + iColWidth / 2 - 1) / iTileSize;
        int topTile = (int)(y - iColHeight / 2) / iTileSize;
        int botTile = (int) (y + iColHeight / 2 -1) / iTileSize;
        
        if (topTile < 0 || botTile >= tileMap.getNumRows() || 
                leftTile < 0 || rightTile >= tileMap.getNumCols()) {
            
            boolTopLeft = boolTopRight = boolBotLeft = boolBotRight = false;
            return;
        }
        
        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(botTile, leftTile);
        int br = tileMap.getType(botTile, rightTile);
        
        boolTopLeft = tl == Tile.BLOCKED;
        boolTopRight = tr == Tile.BLOCKED;
        boolBotLeft = bl == Tile.BLOCKED;
        boolBotRight = br == Tile.BLOCKED;
    }
    
    public void checkTileMapCollision() {
        
        iCurrCol = (int)iX  / iTileSize;
        iCurrRow = (int)iY / iTileSize;
        
        dXdest = iX + dDx;
        dYdest = iY + dDy;
        
        dXtemp = iX;
        dYtemp = iY;
        
        calculateCorners(iX,dYdest);
        
        //Colision si esta brincando
        if(dDy < 0) {
            if(boolTopLeft || boolTopRight) {
                dDy = 0;
                dYtemp = iCurrRow * iTileSize + iColHeight / 2;
            }
            else {
                dYtemp += dDy;
            }
        }
        //Colision si esta callendo
        //Hare un cambio / boolBotLeft x boolTopLeft y boolBotRight x boolTopRight
        if(dDy > 0) {
            if(boolBotLeft || boolBotRight) {
                dDy = 0;
                boolFalling = false;
                dYtemp = (iCurrRow + 1) * iTileSize - iColHeight / 2;
            }
            else {
                dYtemp += dDy;
            }
        }
        //
        calculateCorners(dXdest, iY);
        
        //Colision a la izquierda
        if(dDx < 0) {
            if(boolTopLeft || boolBotLeft) {
                dDx = 0;
                dXtemp = iCurrCol * iTileSize + iColWidth / 2;
            }
            else {
                dXtemp += dDx;
            }
        }
        //Colision a la derecha
        if(dDx > 0) {
            if(boolTopRight || boolBotRight) {
                dDx = 0;
                dXtemp = (iCurrCol + 1) * iTileSize - iColWidth / 2;
            }
            else {
                dXtemp += dDx;
            }
        }
        //Checar si esta callendo
        if (!boolFalling) {
            calculateCorners(iX, dYdest + 1);
            if(!boolBotLeft && !boolBotRight) {
                boolFalling = true;
            }
        }
    }
    
    public int getX() { return (int)iX; }
    public int getY() { return (int)iY; }
    public int getWidth() { return iWidth; }
    public int getHeight() { return iHeight; }
    public int getColHeight() { return iColHeight; }
    public int getColWidth() { return iColWidth; }
    
    public void setPosition(double x, double y) {
        this.iX = x;
        this.iY = y;
    }
    
    public void setVector(double dx, double dy) {
        this.dDx = dx;
        this.dDy = dy;
    }
    
    public void setMapPosition() {
        dXmap = tileMap.getX();
        dYmap = tileMap.getY();
    }
    
    public boolean contains(Objeto o) {
	Rectangle r1 = getRectangle();
	Rectangle r2 = o.getRectangle();
	return r1.contains(r2);
    }
    
    public boolean contains(Rectangle r) {
	return getRectangle().contains(r);
    }
    
    public void setLeft (boolean b) { boolLeft = b;}
    public void setRight (boolean b) { boolRight = b;}
    public void setUp (boolean b) { boolUp = b; }
    public void setDown (boolean b) { boolDown = b; }
    public void setJumping (boolean b) { boolJumping = b; }
    
    public boolean notOnScreen () {
        return iX + dXmap + iWidth < 0 || 
                iX + dXmap - iWidth > GamePanel.WIDTH ||
                iY + dYmap + iHeight < 0 ||
                iY + dYmap - iHeight > GamePanel.HEIGHT;
    }
    
    public void draw (Graphics2D g) {
         if (boolFacingRight) {
            g.drawImage(animation.getImage(),
                    (int)(iX + dXmap - iWidth / 2), 
                    (int)(iY + dYmap - iHeight / 2) , null);
        }
        else {
            g.drawImage(animation.getImage(),
                    (int)(iX + dXmap - iWidth / 2 + iWidth), 
                    (int)(iY + dYmap - iHeight / 2) ,-iWidth, iHeight, null);
        }
    }
    
}
