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
            return 0.2;
        }
    },
    Sword {
        public double getRarity()
        {
            return 0.2;
        }
    },
    Shield {
        public double getRarity()
        {
            return 0.2;
        }
    },
    Ring {
        public double getRarity()
        {
            return 0.05;
        }
    },
    Shoes {
        public double getRarity()
        {
            return 0.25;
        }
    },
    Scroll {
        public double getRarity()
        {
            return 0.1;
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
