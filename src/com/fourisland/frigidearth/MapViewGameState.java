/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

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
    private final int MAX_ROOM_WIDTH = 13;
    private final int MIN_ROOM_WIDTH = 7;
    private final int MAX_ROOM_HEIGHT = 13;
    private final int MIN_ROOM_HEIGHT = 7;
    private final int MAX_CORRIDOR_LENGTH = 6;
    private final int MIN_CORRIDOR_LENGTH = 2;
    private final int[][] OCTET_MULTIPLIERS = new int[][] {new int[] {1,0,0,-1,-1,0,0,1}, new int[] {0,1,-1,0,0,-1,1,0}, new int[] {0,1,1,0,0,-1,-1,0}, new int[] {1,0,0,1,-1,0,0,-1}};
    private Tile[][] grid;
    private boolean[][] gridLighting;
    private int playerx = 4;
    private int playery = 4;
    private int viewportx = 0;
    private int viewporty = 0;
    
    public MapViewGameState()
    {
        grid = new Tile[GAME_WIDTH][GAME_HEIGHT];
        gridLighting = new boolean[GAME_WIDTH][GAME_HEIGHT];
        
        for (int x=0; x<GAME_WIDTH; x++)
        {
            for (int y=0; y<GAME_HEIGHT; y++)
            {
                if ((x == 0) || (x == GAME_WIDTH-1) || (y == 0) || (y == GAME_HEIGHT-1))
                {
                    grid[x][y] = Tile.StoneWall;
                } else {
                    grid[x][y] = Tile.Unused;
                }
            }
        }
        
        makeRoom(GAME_WIDTH/2, GAME_HEIGHT/2, Functions.random(0, 3));
        playerx = GAME_WIDTH/2;
        playery = GAME_HEIGHT/2;
        
        int currentFeatures = 1;
        int objects = 300;
        
        for (int countingTries = 0; countingTries < 1000; countingTries++)
        {
            if (currentFeatures == objects)
            {
                break;
            }
            
            int newx = 0;
            int xmod = 0;
            int newy = 0;
            int ymod = 0;
            int validTile = -1;
            for (int testing = 0; testing < 1000; testing++)
            {
                newx = Functions.random(1, GAME_WIDTH-1);
                newy = Functions.random(1, GAME_HEIGHT-1);
                validTile = -1;
                
                if ((grid[newx][newy] == Tile.DirtWall) || (grid[newx][newy] == Tile.Corridor))
                {
                    if ((grid[newx][newy+1] == Tile.DirtFloor) || (grid[newx][newy+1] == Tile.Corridor))
                    {
                        validTile = 0;
                        xmod = 0;
                        ymod = -1;
                    } else if ((grid[newx-1][newy] == Tile.DirtFloor) || (grid[newx-1][newy] == Tile.Corridor))
                    {
                        validTile = 1;
                        xmod = 1;
                        ymod = 0;
                    } else if ((grid[newx][newy-1] == Tile.DirtFloor) || (grid[newx][newy-1] == Tile.Corridor))
                    {
                        validTile = 2;
                        xmod = 0;
                        ymod = 1;
                    } else if ((grid[newx+1][newy] == Tile.DirtFloor) || (grid[newx+1][newy] == Tile.Corridor))
                    {
                        validTile = 3;
                        xmod = -1;
                        ymod = 0;
                    }
                    
                    if (validTile > -1)
                    {
                        if (grid[newx][newy+1] == Tile.Door)
                        {
                            validTile = -1;
                        } else if (grid[newx-1][newy] == Tile.Door)
                        {
                            validTile = -1;
                        } else if (grid[newx][newy-1] == Tile.Door)
                        {
                            validTile = -1;
                        } else if (grid[newx+1][newy] == Tile.Door)
                        {
                            validTile = -1;
                        }
                    }
                    
                    if (validTile > -1)
                    {
                        break;
                    }
                }
            }
            
            if (validTile > -1)
            {
                if (Functions.random(0, 100) <= 75)
                {
                    if (makeRoom(newx+xmod, newy+ymod, validTile))
                    {
                        currentFeatures++;
                        grid[newx][newy] = Tile.Door;
                        grid[newx+xmod][newy+ymod] = Tile.DirtFloor;
                    }
                } else {
                    if (makeCorridor(newx+xmod, newy+ymod, validTile))
                    {
                        currentFeatures++;
                        grid[newx][newy] = Tile.Door;
                    }
                }
            }
        }
    }
    
    private boolean makeRoom(int x, int y, int direction)
    {
        int width = Functions.random(MIN_ROOM_WIDTH, MAX_ROOM_WIDTH);
        int height = Functions.random(MIN_ROOM_HEIGHT, MAX_ROOM_HEIGHT);
        Tile floor = Tile.DirtFloor;
        Tile wall = Tile.DirtWall;
        int dir = 0;
        
        if ((direction > 0) && (direction < 4))
        {
            dir = direction;
        }
        
        switch (dir)
        {
            case 0: // North
                for (int ytemp=y; ytemp > (y-height); ytemp--)
                {
                    if ((ytemp < 0) || (ytemp > GAME_HEIGHT))
                    {
                        return false;
                    }
                    
                    for (int xtemp=(x-width/2); xtemp < (x+(width+1)/2); xtemp++)
                    {
                        if ((xtemp < 0) || (xtemp > GAME_WIDTH))
                        {
                            return false;
                        }

                        if (grid[xtemp][ytemp] != Tile.Unused)
                        {
                            return false;
                        }
                    }
                }
                
                for (int ytemp = y; ytemp > (y-height); ytemp--)
                {
                    for (int xtemp = (x-width/2); xtemp < (x+(width+1)/2); xtemp++)
                    {
                        if (xtemp == (x-width/2))
                        {
                            grid[xtemp][ytemp] = wall;
                        } else if (xtemp == (x+(width-1)/2))
                        {
                            grid[xtemp][ytemp] = wall;
                        } else if (ytemp == y)
                        {
                            grid[xtemp][ytemp] = wall;
                        } else if (ytemp == (y-height+1))
                        {
                            grid[xtemp][ytemp] = wall;
                        } else {
                            grid[xtemp][ytemp] = floor;
                        }
                    }
                }
                
                break;
                
            case 1: // East
                for (int ytemp=(y-height/2); ytemp < (y+(height+1)/2); ytemp++)
                {
                    if ((ytemp < 0) || (ytemp > GAME_HEIGHT))
                    {
                        return false;
                    }
                    
                    for (int xtemp=x; xtemp < (x+width); xtemp++)
                    {
                        if ((xtemp < 0) || (xtemp > GAME_WIDTH))
                        {
                            return false;
                        }

                        if (grid[xtemp][ytemp] != Tile.Unused)
                        {
                            return false;
                        }
                    }
                }
                
                for (int ytemp=(y-height/2); ytemp < (y+(height+1)/2); ytemp++)
                {
                    for (int xtemp=x; xtemp < (x+width); xtemp++)
                    {
                        if (xtemp == x)
                        {
                            grid[xtemp][ytemp] = wall;
                        } else if (xtemp == (x+width-1))
                        {
                            grid[xtemp][ytemp] = wall;
                        } else if (ytemp == (y-height/2))
                        {
                            grid[xtemp][ytemp] = wall;
                        } else if (ytemp == (y+(height-1)/2))
                        {
                            grid[xtemp][ytemp] = wall;
                        } else {
                            grid[xtemp][ytemp] = floor;
                        }
                    }
                }
                
                break;
                
            case 2: // South
                for (int ytemp=y; ytemp < (y+height); ytemp++)
                {
                    if ((ytemp < 0) || (ytemp > GAME_HEIGHT))
                    {
                        return false;
                    }
                    
                    for (int xtemp=(x-width/2); xtemp < (x+(width+1)/2); xtemp++)
                    {
                        if ((xtemp < 0) || (xtemp > GAME_WIDTH))
                        {
                            return false;
                        }

                        if (grid[xtemp][ytemp] != Tile.Unused)
                        {
                            return false;
                        }
                    }
                }
                
                for (int ytemp=y; ytemp < (y+height); ytemp++)
                {
                    for (int xtemp = (x-width/2); xtemp < (x+(width+1)/2); xtemp++)
                    {
                        if (xtemp == (x-width/2))
                        {
                            grid[xtemp][ytemp] = wall;
                        } else if (xtemp == (x+(width-1)/2))
                        {
                            grid[xtemp][ytemp] = wall;
                        } else if (ytemp == y)
                        {
                            grid[xtemp][ytemp] = wall;
                        } else if (ytemp == (y+height-1))
                        {
                            grid[xtemp][ytemp] = wall;
                        } else {
                            grid[xtemp][ytemp] = floor;
                        }
                    }
                }
                
                break;
                
            case 3: // West
                for (int ytemp=(y-height/2); ytemp < (y+(height+1)/2); ytemp++)
                {
                    if ((ytemp < 0) || (ytemp > GAME_HEIGHT))
                    {
                        return false;
                    }
                    
                    for (int xtemp=x; xtemp > (x-width); xtemp--)
                    {
                        if ((xtemp < 0) || (xtemp > GAME_WIDTH))
                        {
                            return false;
                        }

                        if (grid[xtemp][ytemp] != Tile.Unused)
                        {
                            return false;
                        }
                    }
                }
                
                for (int ytemp=(y-height/2); ytemp < (y+(height+1)/2); ytemp++)
                {
                    for (int xtemp=x; xtemp > (x-width); xtemp--)
                    {
                        if (xtemp == x)
                        {
                            grid[xtemp][ytemp] = wall;
                        } else if (xtemp == (x-width+1))
                        {
                            grid[xtemp][ytemp] = wall;
                        } else if (ytemp == (y-height/2))
                        {
                            grid[xtemp][ytemp] = wall;
                        } else if (ytemp == (y+(height-1)/2))
                        {
                            grid[xtemp][ytemp] = wall;
                        } else {
                            grid[xtemp][ytemp] = floor;
                        }
                    }
                }
                
                break;
        }
        
        return true;
    }
    
    private boolean makeCorridor(int x, int y, int direction)
    {
        int length = Functions.random(MIN_CORRIDOR_LENGTH, MAX_CORRIDOR_LENGTH);
        Tile floor = Tile.Corridor;
        int dir = 0;
        if ((direction > 0) && (direction < 4))
        {
            dir = direction;
        }
        
        int xtemp = 0;
        int ytemp = 0;
        
        switch (dir)
        {
            case 0: // North
                if ((x < 0) || (x > GAME_WIDTH))
                {
                    return false;
                } else {
                    xtemp = x;
                }
                
                for (ytemp = y; ytemp > (y-length); ytemp--)
                {
                    if ((ytemp < 0) || (ytemp > GAME_HEIGHT))
                    {
                        return false;
                    }
                    
                    if (grid[xtemp][ytemp] != Tile.Unused)
                    {
                        return false;
                    }
                }
                
                for (ytemp = y; ytemp > (y-length); ytemp--)
                {
                    grid[xtemp][ytemp] = floor;
                }
                
                break;
                
            case 1: // East
                if ((y < 0) || (y > GAME_HEIGHT))
                {
                    return false;
                } else {
                    ytemp = y;
                }
                
                for (xtemp = x; xtemp < (x+length); xtemp++)
                {
                    if ((xtemp < 0) || (xtemp > GAME_WIDTH))
                    {
                        return false;
                    }
                    
                    if (grid[xtemp][ytemp] != Tile.Unused)
                    {
                        return false;
                    }
                }
                
                for (xtemp = x; xtemp < (x+length); xtemp++)
                {
                    grid[xtemp][ytemp] = floor;
                }
                
                break;
                
            case 2: // South
                if ((x < 0) || (x > GAME_WIDTH))
                {
                    return false;
                } else {
                    xtemp = x;
                }
                
                for (ytemp = y; ytemp < (y+length); ytemp++)
                {
                    if ((ytemp < 0) || (ytemp > GAME_HEIGHT))
                    {
                        return false;
                    }
                    
                    if (grid[xtemp][ytemp] != Tile.Unused)
                    {
                        return false;
                    }
                }
                
                for (ytemp = y; ytemp < (y+length); ytemp++)
                {
                    grid[xtemp][ytemp] = floor;
                }
                
                break;
                
            case 3: // West
                if ((y < 0) || (y > GAME_HEIGHT))
                {
                    return false;
                } else {
                    ytemp = y;
                }
                
                for (xtemp = x; xtemp > (x-length); xtemp--)
                {
                    if ((xtemp < 0) || (xtemp > GAME_WIDTH))
                    {
                        return false;
                    }
                    
                    if (grid[xtemp][ytemp] != Tile.Unused)
                    {
                        return false;
                    }
                }
                
                for (xtemp = x; xtemp > (x-length); xtemp--)
                {
                    grid[xtemp][ytemp] = floor;
                }
                
                break;
        }
        
        return true;
    }
    
    private void calculateFieldOfView()
    {
        for (int x=0; x<GAME_WIDTH; x++)
        {
            for (int y=0; y<GAME_HEIGHT; y++)
            {
                gridLighting[x][y] = false;
            }
        }
        
        for (int i=0; i<8; i++)
        {
            castLight(playerx, playery, 1, 1.0, 0.0, Math.max(VIEWPORT_WIDTH/2, VIEWPORT_HEIGHT/2), OCTET_MULTIPLIERS[0][i], OCTET_MULTIPLIERS[1][i], OCTET_MULTIPLIERS[2][i], OCTET_MULTIPLIERS[3][i], 0);
        }
    }
    
    private void castLight(int cx, int cy, int row, double start, double end, int radius, int xx, int xy, int yx, int yy, int id)
    {
        if (start < end)
        {
            return;
        }
        
        int r2 = radius * radius;
        for (int j=row; j<radius+1; j++)
        {
            int dx = -j-1;
            int dy = -j;
            boolean blocked = false;
            double newStart = 0.0;
            
            while (dx <= 0)
            {
                dx++;
                
                int x = cx + dx*xx + dy*xy;
                int y = cy + dx*yx + dy*yy;
                double l_slope = ((double)dx-0.5)/((double)dy+0.5);
                double r_slope = ((double)dx+0.5)/((double)dy-0.5);
                
                if (start < r_slope)
                {
                    continue;
                } else if (end > l_slope)
                {
                    break;
                } else {
                    if ((dx*dx + dy*dy) < r2)
                    {
                        gridLighting[x][y] = true;
                    }
                    
                    if (blocked)
                    {
                        if (grid[x][y].isBlocked())
                        {
                            newStart = r_slope;
                            continue;
                        } else {
                            blocked = false;
                            start = newStart;
                        }
                    } else {
                        if ((grid[x][y].isBlocked()) && (j < radius))
                        {
                            blocked = true;
                            castLight(cx, cy, j+1, start, l_slope, radius, xx, xy, yx, yy, id+1);
                            newStart = r_slope;
                        }
                    }
                }
            }
            
            if (blocked)
            {
                break;
            }
        }
    }

    public void render(Graphics2D g)
    {
        // Render tiles
        for (int x=viewportx; x<viewportx+VIEWPORT_WIDTH; x++)
        {
            for (int y=viewporty; y<viewporty+VIEWPORT_HEIGHT; y++)
            {
                if (gridLighting[x][y])
                {
                    char displayChar = grid[x][y].getDisplayCharacter();
                    Color displayColor = grid[x][y].getBackgroundColor();

                    if (!displayColor.equals(Color.BLACK))
                    {
                        g.setColor(displayColor);
                        g.fillRect((x-viewportx)*TILE_WIDTH, (y-viewporty)*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
                    }

                    if (displayChar != ' ')
                    {
                        g.drawImage(SystemFont.getCharacter(grid[x][y].getDisplayCharacter()), (x-viewportx)*TILE_WIDTH, (y-viewporty)*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
                    }
                }
            }
        }
        
        // Render player
        g.drawImage(SystemFont.getCharacter('@'), (playerx-viewportx)*TILE_WIDTH, (playery-viewporty)*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
    }
    
    public void processInput(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if ((playerx > 0) && (!grid[playerx-1][playery].isBlocked()))
            {
                playerx--;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if ((playerx < GAME_WIDTH - 1) && (!grid[playerx+1][playery].isBlocked()))
            {
                playerx++;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            if ((playery > 0) && (!grid[playerx][playery-1].isBlocked()))
            {
                playery--;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            if ((playery < GAME_HEIGHT - 1) && (!grid[playerx][playery+1].isBlocked()))
            {
                playery++;
            }
        } else {
            return;
        }
        
        adjustViewport();
        calculateFieldOfView();
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
