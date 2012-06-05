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
    Helmet {
        public double getRarity()
        {
            return 0.75;
        }
    },
    Scroll {
        public double getRarity()
        {
            return 0.25;
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
