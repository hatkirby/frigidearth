/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

/**
 *
 * @author hatkirby
 */
public class TileGameState implements GameState
{
    private final int TILE_WIDTH = 16;
    private final int TILE_HEIGHT = 16;
    private final int GAME_WIDTH = Main.GAME_WIDTH / TILE_WIDTH;
    private final int GAME_HEIGHT = Main.GAME_HEIGHT / TILE_HEIGHT;
    private Color[][] grid;
    
    public TileGameState()
    {
        Random r = new Random();
        grid = new Color[GAME_WIDTH][GAME_HEIGHT];
        
        for (int x=0; x<GAME_WIDTH; x++)
        {
            for (int y=0; y<GAME_HEIGHT; y++)
            {
                grid[x][y] = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
            }
        }
    }
    
    public void tick()
    {
        
    }

    public void render(Graphics2D g)
    {
        for (int x=0; x<GAME_WIDTH; x++)
        {
            for (int y=0; y<GAME_HEIGHT; y++)
            {
                g.setColor(grid[x][y]);
                g.fillRect(x*TILE_WIDTH, y*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
            }
        }
    }
    
}
