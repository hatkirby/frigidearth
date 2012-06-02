/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
    private final int GAME_WIDTH = 100;
    private final int GAME_HEIGHT = 100;
    private final int VIEWPORT_WIDTH = Main.GAME_WIDTH / TILE_WIDTH;
    private final int VIEWPORT_HEIGHT = Main.GAME_HEIGHT / TILE_HEIGHT;
    private final int ROOM_MAX_SIZE = 10;
    private final int ROOM_MIN_SIZE = 6;
    private final int MAX_ROOMS = 30;
    private boolean[][] grid;
    private int playerx = 4;
    private int playery = 4;
    private int viewportx = 0;
    private int viewporty = 0;
    
    public MapViewGameState()
    {
        grid = new boolean[GAME_WIDTH][GAME_HEIGHT];
        
        for (int x=0; x<GAME_WIDTH; x++)
        {
            for (int y=0; y<GAME_HEIGHT; y++)
            {
                grid[x][y] = true;
            }
        }
        
        Rectangle[] rooms = new Rectangle[MAX_ROOMS];
        int numRooms = 0;
        Random r = new Random();
        
        for (int i=0; i<MAX_ROOMS; i++)
        {
            int w = r.nextInt(ROOM_MAX_SIZE - ROOM_MIN_SIZE + 1) + ROOM_MIN_SIZE;
            int h = r.nextInt(ROOM_MAX_SIZE - ROOM_MIN_SIZE + 1) + ROOM_MIN_SIZE;
            int x = r.nextInt(GAME_WIDTH - w - 1);
            int y = r.nextInt(GAME_HEIGHT - h - 1);
            Rectangle newRoom = new Rectangle(x, y, w, h);
            boolean failed = false;
            
            for (Rectangle room : rooms)
            {
               if ((room != null) && (newRoom.intersects(room)))
               {
                   failed = true;
                   break;
               }
            }
            
            if (!failed)
            {
                createRoom(newRoom);
                
                int newX = (int) newRoom.getCenterX();
                int newY = (int) newRoom.getCenterY();
                
                if (numRooms == 0)
                {
                    playerx = newX;
                    playery = newY;
                    
                    adjustViewport();
                } else {
                    int prevX = (int) rooms[numRooms-1].getCenterX();
                    int prevY = (int) rooms[numRooms-1].getCenterY();
                    
                    if (r.nextBoolean())
                    {
                        createHTunnel(prevX, newX, prevY);
                        createVTunnel(prevY, newY, newX);
                    } else {
                        createVTunnel(prevY, newY, prevX);
                        createHTunnel(prevX, newX, newY);
                    }
                }
                
                rooms[numRooms++] = newRoom;
            }
        }
    }
    
    private void createRoom(Rectangle room)
    {
        for (int x=room.x+1; x<room.x+room.width; x++)
        {
            for (int y=room.y+1; y<room.y+room.height; y++)
            {
                grid[x][y] = false;
            }
        }
    }
    
    private void createHTunnel(int x1, int x2, int y)
    {
        if (x2 < x1)
        {
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }
        
        for (int x=x1; x<x2+1; x++)
        {
            grid[x][y] = false;
        }
    }
    
    private void createVTunnel(int y1, int y2, int x)
    {
        if (y2 < y1)
        {
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }
        
        for (int y=y1; y<y2+1; y++)
        {
            grid[x][y] = false;
        }
    }

    public void render(Graphics2D g)
    {
        // Render tiles
        for (int x=viewportx; x<viewportx+VIEWPORT_WIDTH; x++)
        {
            for (int y=viewporty; y<viewporty+VIEWPORT_HEIGHT; y++)
            {
                if (grid[x][y])
                {
                    g.setColor(Color.GRAY);
                } else {
                    g.setColor(Color.BLACK);
                }
                
                g.fillRect((x-viewportx)*TILE_WIDTH, (y-viewporty)*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
            }
        }
        
        // Render player
        g.drawImage(SystemFont.getCharacter('@'), (playerx-viewportx)*TILE_WIDTH, (playery-viewporty)*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
    }
    
    public void processInput(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if ((playerx > 0) && (!grid[playerx-1][playery]))
            {
                playerx--;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if ((playerx < GAME_WIDTH - 1) && (!grid[playerx+1][playery]))
            {
                playerx++;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            if ((playery > 0) && (!grid[playerx][playery-1]))
            {
                playery--;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            if ((playery < GAME_HEIGHT - 1) && (!grid[playerx][playery+1]))
            {
                playery++;
            }
        } else {
            return;
        }
        
        adjustViewport();
    }
    
    private void adjustViewport()
    {
        if (playerx > (VIEWPORT_WIDTH/2))
        {
            if (playerx < (GAME_WIDTH - (VIEWPORT_WIDTH/2-1)))
            {
                viewportx = playerx - (VIEWPORT_WIDTH/2);
            } else {
                viewportx = GAME_WIDTH - VIEWPORT_WIDTH;
            }
        } else {
            viewportx = 0;
        }
        
        if (playery > (VIEWPORT_HEIGHT/2))
        {
            if (playery < (GAME_HEIGHT - (VIEWPORT_HEIGHT/2-1)))
            {
                viewporty = playery - (VIEWPORT_HEIGHT/2);
            } else {
                viewporty = GAME_HEIGHT - VIEWPORT_HEIGHT;
            }
        } else {
            viewporty = 0;
        }
    }
}
