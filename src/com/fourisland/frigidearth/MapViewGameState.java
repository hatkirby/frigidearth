/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import com.fourisland.frigidearth.mobs.Mouse;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hatkirby
 */
public class MapViewGameState implements GameState
{
    private final int TILE_WIDTH = 12;
    private final int TILE_HEIGHT = 12;
    private final int MAP_WIDTH = 100;
    private final int MAP_HEIGHT = 100;
    private final int MESSAGE_HEIGHT = 5;
    private final int VIEWPORT_WIDTH = Main.CANVAS_WIDTH / TILE_WIDTH;
    private final int VIEWPORT_HEIGHT = Main.CANVAS_HEIGHT / TILE_HEIGHT - MESSAGE_HEIGHT;
    private final int MAX_ROOM_WIDTH = 13;
    private final int MIN_ROOM_WIDTH = 7;
    private final int MAX_ROOM_HEIGHT = 13;
    private final int MIN_ROOM_HEIGHT = 7;
    private final int MAX_CORRIDOR_LENGTH = 6;
    private final int MIN_CORRIDOR_LENGTH = 2;
    private final int[][] OCTET_MULTIPLIERS = new int[][] {new int[] {1,0,0,-1,-1,0,0,1}, new int[] {0,1,-1,0,0,-1,1,0}, new int[] {0,1,1,0,0,-1,-1,0}, new int[] {1,0,0,1,-1,0,0,-1}};
    private Tile[][] grid;
    private boolean[][] gridLighting;
    private String[] messages = new String[MESSAGE_HEIGHT];
    private List<Room> rooms = new ArrayList<Room>();
    private List<Mob> mobs = new ArrayList<Mob>();
    private int playerx = 4;
    private int playery = 4;
    private int viewportx = 0;
    private int viewporty = 0;
    
    public MapViewGameState()
    {
        grid = new Tile[MAP_WIDTH][MAP_HEIGHT];
        gridLighting = new boolean[MAP_WIDTH][MAP_HEIGHT];
        
        for (int x=0; x<MAP_WIDTH; x++)
        {
            for (int y=0; y<MAP_HEIGHT; y++)
            {
                if ((x == 0) || (x == MAP_WIDTH-1) || (y == 0) || (y == MAP_HEIGHT-1))
                {
                    grid[x][y] = Tile.StoneWall;
                } else {
                    grid[x][y] = Tile.Unused;
                }
            }
        }
        
        makeRoom(MAP_WIDTH/2, MAP_HEIGHT/2, Direction.getRandomDirection());
        
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
            Direction validTile = null;
            for (int testing = 0; testing < 1000; testing++)
            {
                newx = Functions.random(1, MAP_WIDTH-1);
                newy = Functions.random(1, MAP_HEIGHT-1);
                validTile = null;
                
                if ((grid[newx][newy] == Tile.DirtWall) || (grid[newx][newy] == Tile.Corridor))
                {
                    if ((grid[newx][newy+1] == Tile.DirtFloor) || (grid[newx][newy+1] == Tile.Corridor))
                    {
                        validTile = Direction.North;
                        xmod = 0;
                        ymod = -1;
                    } else if ((grid[newx-1][newy] == Tile.DirtFloor) || (grid[newx-1][newy] == Tile.Corridor))
                    {
                        validTile = Direction.East;
                        xmod = 1;
                        ymod = 0;
                    } else if ((grid[newx][newy-1] == Tile.DirtFloor) || (grid[newx][newy-1] == Tile.Corridor))
                    {
                        validTile = Direction.South;
                        xmod = 0;
                        ymod = 1;
                    } else if ((grid[newx+1][newy] == Tile.DirtFloor) || (grid[newx+1][newy] == Tile.Corridor))
                    {
                        validTile = Direction.West;
                        xmod = -1;
                        ymod = 0;
                    }
                    
                    if (validTile != null)
                    {
                        if (grid[newx][newy+1] == Tile.Door)
                        {
                            validTile = null;
                        } else if (grid[newx-1][newy] == Tile.Door)
                        {
                            validTile = null;
                        } else if (grid[newx][newy-1] == Tile.Door)
                        {
                            validTile = null;
                        } else if (grid[newx+1][newy] == Tile.Door)
                        {
                            validTile = null;
                        }
                    }
                    
                    if (validTile != null)
                    {
                        break;
                    }
                }
            }
            
            if (validTile != null)
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
        
        int newx = 0;
        int newy = 0;
        int ways = 0;
        int state = 0;
        while (state != 10)
        {
            for (int testing = 0; testing < 1000; testing++)
            {
                newx = Functions.random(1, MAP_WIDTH-1);
                newy = Functions.random(1, MAP_HEIGHT-2);
                ways = 4;
                
                for (Direction dir : Direction.values())
                {
                    Point to = dir.to(new Point(newx, newy));
                    if ((grid[to.x][to.y] == Tile.DirtFloor) || (grid[to.x][to.y] == Tile.Corridor))
                    {
                        ways--;
                    }
                }
                
                if (state == 0)
                {
                    if (ways == 0)
                    {
                        grid[newx][newy] = Tile.UpStairs;
                        state = 1;
                        break;
                    }
                } else if (state == 1)
                {
                    if (ways == 0)
                    {
                        grid[newx][newy] = Tile.DownStairs;
                        playerx=newx+1;
                        playery=newy;
                        state = 10;
                        break;
                    }
                }
            }
        }
        
        adjustViewport();
        calculateFieldOfView();
    }
    
    private boolean makeRoom(int x, int y, Direction direction)
    {
        int width = Functions.random(MIN_ROOM_WIDTH, MAX_ROOM_WIDTH);
        int height = Functions.random(MIN_ROOM_HEIGHT, MAX_ROOM_HEIGHT);
        Tile floor = Tile.DirtFloor;
        Tile wall = Tile.DirtWall;
        Room room = null;
        
        switch (direction)
        {
            case North:
                room = new Room(x-width/2, y-height, width+1, height+1, true);
                
                break;
                
            case East:
                room = new Room(x, y-height/2, width+1, height+1, true);
                
                break;
                
            case South:
                room = new Room(x-width/2, y, width+1, height+1, true);
                
                break;
                
            case West:
                room = new Room(x-width, y-height/2, width+1, height+1, true);
                
                break;
        }
        
        for (int ytemp=room.getY(); ytemp < room.getY()+room.getHeight(); ytemp++)
        {
            if ((ytemp < 0) || (ytemp > MAP_HEIGHT))
            {
                return false;
            }

            for (int xtemp=room.getX(); xtemp < room.getX()+room.getWidth(); xtemp++)
            {
                if ((xtemp < 0) || (xtemp > MAP_WIDTH))
                {
                    return false;
                }

                if (grid[xtemp][ytemp] != Tile.Unused)
                {
                    return false;
                }
            }
        }

        for (int ytemp=room.getY(); ytemp < room.getY()+room.getHeight(); ytemp++)
        {
            for (int xtemp=room.getX(); xtemp < room.getX()+room.getWidth(); xtemp++)
            {
                if (xtemp == room.getX())
                {
                    grid[xtemp][ytemp] = wall;
                } else if (xtemp == room.getX()+room.getWidth()-1)
                {
                    grid[xtemp][ytemp] = wall;
                } else if (ytemp == room.getY())
                {
                    grid[xtemp][ytemp] = wall;
                } else if (ytemp == room.getY()+room.getHeight()-1)
                {
                    grid[xtemp][ytemp] = wall;
                } else {
                    grid[xtemp][ytemp] = floor;
                }
            }
        }
        
        rooms.add(room);
        
        // Place mice in random rooms because yolo
        if (Functions.random(0, 100) < 25)
        {
            Mob mob = new Mouse(Functions.random(room.getX()+1, room.getX()+room.getWidth()-2), Functions.random(room.getY()+1, room.getY()+room.getHeight()-2));
            mobs.add(mob);
        }
        
        return true;
    }
    
    private boolean makeCorridor(int x, int y, Direction direction)
    {
        int length = Functions.random(MIN_CORRIDOR_LENGTH, MAX_CORRIDOR_LENGTH);
        Tile floor = Tile.Corridor;
        
        int xtemp = 0;
        int ytemp = 0;
        
        switch (direction)
        {
            case North:
                if ((x < 0) || (x > MAP_WIDTH))
                {
                    return false;
                } else {
                    xtemp = x;
                }
                
                for (ytemp = y; ytemp > (y-length); ytemp--)
                {
                    if ((ytemp < 0) || (ytemp > MAP_HEIGHT))
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
                
            case East:
                if ((y < 0) || (y > MAP_HEIGHT))
                {
                    return false;
                } else {
                    ytemp = y;
                }
                
                for (xtemp = x; xtemp < (x+length); xtemp++)
                {
                    if ((xtemp < 0) || (xtemp > MAP_WIDTH))
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
                
            case South:
                if ((x < 0) || (x > MAP_WIDTH))
                {
                    return false;
                } else {
                    xtemp = x;
                }
                
                for (ytemp = y; ytemp < (y+length); ytemp++)
                {
                    if ((ytemp < 0) || (ytemp > MAP_HEIGHT))
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
                
            case West:
                if ((y < 0) || (y > MAP_HEIGHT))
                {
                    return false;
                } else {
                    ytemp = y;
                }
                
                for (xtemp = x; xtemp > (x-length); xtemp--)
                {
                    if ((xtemp < 0) || (xtemp > MAP_WIDTH))
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
        for (int x=0; x<MAP_WIDTH; x++)
        {
            for (int y=0; y<MAP_HEIGHT; y++)
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
        
        // Render mobs
        for (Mob mob : mobs)
        {
            if ((gridLighting[mob.getX()][mob.getY()]) && (mob.getX() > viewportx) && (mob.getX() < viewportx+VIEWPORT_WIDTH) && (mob.getY() > viewporty) && (mob.getY() < viewporty+VIEWPORT_HEIGHT))
            {
                g.drawImage(SystemFont.getCharacter(mob.getDisplayCharacter()), (mob.getX()-viewportx)*TILE_WIDTH, (mob.getY()-viewporty)*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
            }
        }
        
        // Render player
        g.drawImage(SystemFont.getCharacter('@'), (playerx-viewportx)*TILE_WIDTH, (playery-viewporty)*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
        
        // Render messages
        g.setColor(new Color(53, 63, 62));
        g.fillRect(0, VIEWPORT_HEIGHT*TILE_HEIGHT, Main.CANVAS_WIDTH, TILE_HEIGHT*MESSAGE_HEIGHT);
        
        for (int i=0; i<MESSAGE_HEIGHT; i++)
        {
            if (messages[i] != null)
            {
                for (int j=0; j<messages[i].length(); j++)
                {
                    g.drawImage(SystemFont.getCharacter(messages[i].charAt(j)), j*TILE_WIDTH, (VIEWPORT_HEIGHT+i)*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
                }
            }
        }
    }
    
    public void processInput(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                Direction dir = Direction.fromKeyEvent(e);
                Point to = dir.to(new Point(playerx, playery));
                
                if ((isValidPosition(to.x,to.y)) && (!grid[to.x][to.y].isBlocked()))
                {
                    playerx = to.x;
                    playery = to.y;
                } else {
                    printMessage("Blocked: " + dir.name());
                    
                    return;
                }
                
                break;
                
            default:
                printMessage("The sky is the limit! You can also place items in the same manner, but we'll get to that later.");
                
                return;
        }
        
        // Move mobs randomly
        for (Mob mob : mobs)
        {
            Direction toDir = null;
            
            for (int i=0; i<10; i++)
            {
                toDir = Direction.getRandomDirection();
                Point to = toDir.to(mob.getPosition());
                if ((isValidPosition(to.x,to.y)) &&(!grid[to.x][to.y].isBlocked()))
                {
                    mob.moveInDirection(toDir);
                    break;
                }
            }
        }
        
        adjustViewport();
        calculateFieldOfView();
    }
    
    private void adjustViewport()
    {
        if (playerx > (VIEWPORT_WIDTH/2))
        {
            if (playerx < (MAP_WIDTH - (VIEWPORT_WIDTH/2-1)))
            {
                viewportx = playerx - (VIEWPORT_WIDTH/2);
            } else {
                viewportx = MAP_WIDTH - VIEWPORT_WIDTH;
            }
        } else {
            viewportx = 0;
        }
        
        if (playery > (VIEWPORT_HEIGHT/2))
        {
            if (playery < (MAP_HEIGHT - (VIEWPORT_HEIGHT/2-1)))
            {
                viewporty = playery - (VIEWPORT_HEIGHT/2);
            } else {
                viewporty = MAP_HEIGHT - VIEWPORT_HEIGHT;
            }
        } else {
            viewporty = 0;
        }
    }
    
    private boolean isValidPosition(int x, int y)
    {
        if (x < 0) return false;
        if (x > MAP_WIDTH) return false;
        if (y < 0) return false;
        if (y > MAP_HEIGHT) return false;
        
        return true;
    }
    
    private void printMessage(String message)
    {
        String temp = message;
        
        while (temp.length() > VIEWPORT_WIDTH)
        {
            String shortm = temp.substring(0, VIEWPORT_WIDTH);
            
            if ((temp.charAt(VIEWPORT_WIDTH) == ' ') || (shortm.endsWith(" ")))
            {
                pushUpMessages(shortm);
                temp = temp.substring(VIEWPORT_WIDTH);
            } else {
                int lastSpace = shortm.lastIndexOf(" ");
                pushUpMessages(shortm.substring(0, lastSpace));
                temp = temp.substring(lastSpace);
            }
        }
        
        pushUpMessages(temp);
    }
    
    private void pushUpMessages(String message)
    {
        for (int i=1; i<MESSAGE_HEIGHT; i++)
        {
            messages[i-1] = messages[i];
        }
        
        messages[MESSAGE_HEIGHT-1] = message;
    }
}
