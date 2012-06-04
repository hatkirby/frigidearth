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
public class Spider extends Mob
{
    public Spider(int x, int y)
    {
        super(x, y);
        
        health = Functions.rollDice(2, 5);
        hostile = true;
    }

    public char getDisplayCharacter()
    {
        return 's';
    }

    public Color getDisplayColor()
    {
        return Color.BLACK;
    }

    public String getName()
    {
        return "Spider";
    }

    public String getBattleMessage()
    {
        return "The spider bites you";
    }

    public int getAttackPower()
    {
        return 1;
    }
    
    public int getBaseExperience()
    {
        return 150;
    }
}
