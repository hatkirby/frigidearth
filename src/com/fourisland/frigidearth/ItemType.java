/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

/**
 *
 * @author hatkirby
 */
public enum ItemType
{
    Scroll {
        public double getRarity()
        {
            return 1;
        }
    },
    Special {
        public double getRarity()
        {
            return 0;
        }
    };
    
    public abstract double getRarity();
}
