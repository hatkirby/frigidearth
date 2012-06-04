/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth.mobs;

import com.fourisland.frigidearth.Mob;
import java.awt.Color;

/**
 *
 * @author hatkirby
 */
public class Mouse extends Mob
{
    public Mouse(int x, int y)
    {
        super(x, y);
        
        health = 1;
        hostile = false;
    }
    
    public char getDisplayCharacter()
    {
        return 'm';
    }
    
    public Color getDisplayColor()
    {
        return Color.GRAY;
    }
    
    public String getName()
    {
        return "Mouse";
    }
    
    public String getBattleMessage()
    {
        return "The mouse bites you";
    }
}
