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
}
