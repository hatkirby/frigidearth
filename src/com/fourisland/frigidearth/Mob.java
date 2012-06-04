/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author hatkirby
 */
public abstract class Mob
{
    public int x;
    public int y;
    public int health;
    public boolean hostile;
    
    public Mob(int x, int y)
    {
        this.x = x;
        this.y = y;
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
    public abstract Color getDisplayColor();
    public abstract String getName();
    public abstract String getBattleMessage();
    public abstract int getAttackPower();
    public abstract int getBaseExperience();
}
