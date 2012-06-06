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
public class FireSnake extends Mob
{
    public FireSnake(int x, int y)
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
        return Color.RED;
    }

    public String getName()
    {
        return "Fire Snake";
    }

    public String getBattleMessage()
    {
        return "The fire snake bites you";
    }

    public int getAttackPower()
    {
        return Functions.rollDice(1, 4);
    }

    public int getBaseExperience()
    {
        return 200;
    }
    
}
