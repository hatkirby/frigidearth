/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

/**
 *
 * @author hatkirby
 */
public enum Direction
{
    North,
    East,
    South,
    West;
    
    public static Direction getRandomDirection()
    {
        int r = Functions.random(0, 3);
        
        switch (r)
        {
            case 0: return North;
            case 1: return East;
            case 2: return South;
            case 3: return West;
            default: return null; // This can't happen
        }
    }
}
