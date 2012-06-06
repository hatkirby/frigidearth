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
public class IceSnake extends Mob
{
    public IceSnake(int x, int y)
    {
        super(x, y);
        
        health = Functions.rollDice(3, 4);
        hostile = true;
    }

    public char getDisplayCharacter()
    {
        return 's';
    }

    public Color getDisplayColor()
    {
        return Color.BLUE;
    }

    public String getName()
    {
        return "Ice Snake";
    }

    public String getBattleMessage()
    {
        return "The ice snake bites you";
    }

    public int getAttackPower()
    {
        return Functions.rollDice(1, 4);
    }

    public int getBaseExperience()
    {
        return 450;
    }
    
}
