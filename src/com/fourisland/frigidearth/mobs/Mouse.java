/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth.mobs;

import com.fourisland.frigidearth.Mob;

/**
 *
 * @author hatkirby
 */
public class Mouse extends Mob
{
    public Mouse(int x, int y)
    {
        super(x, y);
    }
    
    
    public char getDisplayCharacter()
    {
        return 'm';
    }
}
