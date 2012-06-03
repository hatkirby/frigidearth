/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

/**
 *
 * @author hatkirby
 */
public class Room
{
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean generateMonsters;
    
    public Room(int x, int y, int width, int height, boolean generateMonsters)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.generateMonsters = generateMonsters;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public boolean canGenerateMonsters()
    {
        return generateMonsters;
    }
}
