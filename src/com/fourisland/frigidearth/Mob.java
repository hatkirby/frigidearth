/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.awt.Point;

/**
 *
 * @author hatkirby
 */
public abstract class Mob
{
    private int x;
    private int y;
    
    public Mob(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public Point getPosition()
    {
        return new Point(x,y);
    }
    
    public void moveInDirection(Direction dir)
    {
        Point to = dir.to(getPosition());
        x = to.x;
        y = to.y;
    }
    
    public abstract char getDisplayCharacter();
}
