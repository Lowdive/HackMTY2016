/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TileMap;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;


/**
 *
 * @author MarioDiaz
 */
public class TileMap {
    //posicion
    private double dDx;
    private double dDy;
    
    //limites
    private int dDxmin;
    private int dDymin;
    private int dDymax;
    private int dDxmax;
    
    //scroll
    private double iScroll;
    
    //mapa
    private int[][] map;
    private int iTileSize;
    private int iNumRows;
    private int iNumCols;
    private int iWidth;
    private int iHeight;
    
    //tileset
    
    private BufferedImage imaTileset;
    private int iNumTilesAcross;
    private Tile[][] arrTiles;
    
    //limites de pintar
    private int iRowOffset;// en que fila empezar a dibujar
    private int iColOffset;// en que columna empezar a dibujar
    private int iNumRowsToDraw; // numero de filas a dibujar
    private int iNumColsToDraw; // numero de columunas a dibuajr
    
    //Efecto de terremoto
    private boolean boolVibrar;
    private int iMagnitud;
    
    public TileMap (int tileSize) {
        this.iTileSize = tileSize;
        iNumRowsToDraw = GamePanel.HEIGHT / iTileSize + 2;
        iNumColsToDraw = GamePanel.WIDTH /iTileSize + 2;
        iScroll = 1;
    }
    
    public void loadTiles(String s) {
        
        try {
            imaTileset = ImageIO.read(getClass().getResourceAsStream(s));
            iNumTilesAcross = imaTileset.getWidth() / iTileSize;
            arrTiles = new Tile[2][iNumTilesAcross];
            
            BufferedImage imaSubimage;
            for(int col = 0; col < iNumTilesAcross; col++) {
                //Cargando los tiles normales
                imaSubimage = imaTileset.getSubimage( col * iTileSize,
                        0, iTileSize, iTileSize);
                
                arrTiles[0][col] = new Tile(imaSubimage, Tile.NORMAL);
                
                //Cargando los tiles bloqueados
                imaSubimage = imaTileset.getSubimage( col * iTileSize,
                        iTileSize, iTileSize, iTileSize);
                
                arrTiles[1][col] = new Tile(imaSubimage, Tile.BLOCKED);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadMap (String s) {
        
        try {
            InputStream in  = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            iNumCols = Integer.parseInt(br.readLine());
            iNumRows = Integer.parseInt(br.readLine()); 
            map = new int[iNumRows][iNumCols];
            iWidth = iNumCols * iTileSize;
            iHeight = iNumRows * iTileSize;
            
            dDxmin = GamePanel.WIDTH - iWidth;
            dDxmax = 0;
            dDymin = GamePanel.HEIGHT - iHeight;
            dDymax = 0;
            
            String delimeters = "\\s+";
            for (int row = 0; row < iNumRows; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delimeters);
                for(int col = 0; col < iNumCols; col++) {
                    
                    int tokenTemp = Integer.parseInt(tokens[col]);
                    
                    if (tokenTemp > 1) {
                        tokenTemp --;
                    }
                    
                    map[row][col] = tokenTemp;
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getTileSize() { return iTileSize; }
    public double getX() { return dDx; }
    public double getY() { return dDy; }
    public int getWidth() { return iWidth; }
    public int getHeight() { return iHeight; }
    
    public int getNumRows() { return iNumRows; }
    public int getNumCols() { return iNumCols; }
    
    public int getType(int row, int col) {
        int rc = map[row][col];
        int r = rc / iNumTilesAcross;
        int c = rc % iNumTilesAcross;
        return arrTiles[r][c].getType();
    }
    public void setScroll(double x) {
        iScroll = x;
    }
    
    public boolean isVibrando() { return boolVibrar;}
    
    public void setVibrar(boolean b, int i) {
        boolVibrar = b;
        iMagnitud = i;
    }
    
    public void setPosition(double x, double y) {
        this.dDx += (x - this.dDx) * iScroll;
        this.dDy += (y - this.dDy) * iScroll;      
        
        fixBounds();
        
        iColOffset = (int) - this.dDx / iTileSize; //donde empezar a dibujar
        iRowOffset = (int) - this.dDy / iTileSize; //donde empezra  dibujar
        
    }
    
    public void fixBounds() {
        if(dDx < dDxmin) dDx = dDxmin;
        if(dDy < dDymin) dDy = dDymin;
        if(dDx > dDxmax) dDx = dDxmax;
        if(dDy > dDymax) dDy = dDymax;
    }
    
    public void update() {
        if(boolVibrar) {
            this.dDx += Math.random() * iMagnitud - iMagnitud / 2;
	    this.dDy += Math.random() * iMagnitud - iMagnitud / 2;
        }
    }
    
    
    public void draw(Graphics2D g) {
        
        for (int row = iRowOffset; row < iRowOffset + iNumRowsToDraw; row++ ) {
            
            if (row >= iNumRows) break;
            
            for (int col = iColOffset; col <iColOffset + iNumColsToDraw;col++) {
                
                if( col >= iNumCols) break;
                if(map[row][col] == 0) continue;
                
                int rc = map[row][col];
                int r = rc / iNumTilesAcross;
                int c = rc % iNumTilesAcross;
                
                g.drawImage(arrTiles[r][c].getImage(), (int)dDx + col * iTileSize,
                        (int)dDy + row * iTileSize,null);
            }
        }
    }
    
    
    
    
}
