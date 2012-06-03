/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.awt.Color;

/**
 *
 * @author hatkirby
 */
public enum Tile
{
    Unused {
        public boolean isBlocked()
        {
            return true;
        }
    },
    DirtWall {
        public boolean isBlocked()
        {
            return true;
        }
        
        public Color getBackgroundColor()
        {
            return new Color(96, 51, 17);
        }
    },
    DirtFloor {
        public boolean isBlocked()
        {
            return false;
        }
        
        public Color getBackgroundColor()
        {
            return new Color(205, 127, 50);
        }
    },
    StoneWall {
        public boolean isBlocked()
        {
            return true;
        }
    },
    Corridor {
        public boolean isBlocked()
        {
            return false;
        }
        
        public char getDisplayCharacter()
        {
            return '.';
        }
        
        public Color getBackgroundColor()
        {
            return new Color(182, 175, 169);
        }
    },
    Door {
        public boolean isBlocked()
        {
            return false;
        }
        
        public char getDisplayCharacter()
        {
            return 'D';
        }
        
        public Color getBackgroundColor()
        {
            return new Color(96, 51, 17);
        }
    },
    UpStairs {
        public boolean isBlocked()
        {
            return false;
        }
        
        public char getDisplayCharacter()
        {
            return '>';
        }
    },
    DownStairs {
        public boolean isBlocked()
        {
            return false;
        }
        
        public char getDisplayCharacter()
        {
            return '<';
        }
    };
    
    public abstract boolean isBlocked();
    
    public char getDisplayCharacter()
    {
        return ' ';
    }
    
    public Color getBackgroundColor()
    {
        return Color.BLACK;
    }
}
