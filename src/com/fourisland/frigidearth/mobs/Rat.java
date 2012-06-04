/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth.mobs;

import com.fourisland.frigidearth.Functions;
import com.fourisland.frigidearth.Mob;
import java.awt.Color;

/**
 *
 * @author hatkirby
 */
public class Rat extends Mob
{
    public Rat(int x, int y)
    {
        super(x, y);
        
        health = Functions.rollDice(1, 4);
        hostile = true;
    }

    public char getDisplayCharacter()
    {
        return 'r';
    }

    public Color getDisplayColor()
    {
        return Color.GRAY;
    }

    public String getName()
    {
        return "Rat";
    }
    
    public String getBattleMessage()
    {
        return "The rat bites you";
    }
    
    public int getAttackPower()
    {
        return 1;
    }
    
    public int getBaseExperience()
    {
        return 100;
    }
}
