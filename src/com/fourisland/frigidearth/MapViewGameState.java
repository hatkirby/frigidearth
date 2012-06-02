/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 *
 * @author hatkirby
 */
public class MapViewGameState implements GameState
{
    private final int TILE_WIDTH = 16;
    private final int TILE_HEIGHT = 16;
    private final int GAME_WIDTH = Main.GAME_WIDTH / TILE_WIDTH;
    private final int GAME_HEIGHT = Main.GAME_HEIGHT / TILE_HEIGHT;
    private Color[][] grid;
    private int playerx = 4;
    private int playery = 4;
    
    public MapViewGameState()
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
        Random r = new Random();
        
        if (r.nextBoolean())
        {
            for (int x=0; x<GAME_WIDTH; x++)
            {
                for (int y=0; y<GAME_HEIGHT; y++)
                {
                    grid[x][y] = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
                }
            }
        }
    }

    public void render(Graphics2D g)
    {
        // Render tiles
        for (int x=0; x<GAME_WIDTH; x++)
        {
            for (int y=0; y<GAME_HEIGHT; y++)
            {
                g.setColor(grid[x][y]);
                g.fillRect(x*TILE_WIDTH, y*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
            }
        }
        
        // Render player
        g.setColor(Color.white);
        g.setFont(new Font("Lucida Sans", Font.BOLD, 16));
        g.drawString("@", playerx*TILE_WIDTH+3, (playery+1)*TILE_HEIGHT);
    }
    
    public void processInput(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if (playerx > 0)
            {
                playerx--;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if (playerx < GAME_WIDTH - 1)
            {
                playerx++;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            if (playery > 0)
            {
                playery--;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            if (playery < GAME_HEIGHT - 1)
            {
                playery++;
            }
        }
    }
}
