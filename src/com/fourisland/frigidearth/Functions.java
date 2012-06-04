/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.util.Random;

/**
 *
 * @author hatkirby
 */
public class Functions
{
    public static int random(int min, int max)
    {
        Random r = new Random();
        
        return r.nextInt(max - min + 1) + min;
    }
    
    public static int rollDice(int dice, int sides)
    {
        int result = 0;
        
        for (int i=0; i<dice; i++)
        {
            result += random(1, sides);
        }
        
        return result;
    }
    
    public static String padLeft(String str, int length, char pad)
    {
        int padding = length - str.length();
        if (str.length() > length)
        {
            return str;
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<padding; i++)
        {
            sb.append(pad);
        }
        
        sb.append(str);
        return sb.toString();
    }
}
