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
            return new Color(0, 63, 135);
        }
    },
    DirtFloor {
        public boolean isBlocked()
        {
            return false;
        }
        
        public Color getBackgroundColor()
        {
            return new Color(162, 181, 205);
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
            return new Color(0, 63, 135);
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
        
        public Color getBackgroundColor()
        {
            return new Color(162, 181, 205);
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
        
        public Color getBackgroundColor()
        {
            return new Color(162, 181, 205);
        }
    },
    Window {
        public boolean isBlocked()
        {
            return true;
        }
        
        public char getDisplayCharacter()
        {
            return 'W';
        }
        
        public Color getBackgroundColor()
        {
            return new Color(0, 63, 135);
        }
    },
    ShatteredWindow {
        public boolean isBlocked()
        {
            return false;
        }
        
        public char getDisplayCharacter()
        {
            return 'W';
        }
        
        public Color getBackgroundColor()
        {
            return new Color(162, 181, 205);
        }
    },
    Snow {
        public boolean isBlocked()
        {
            return false;
        }
        
        public Color getBackgroundColor()
        {
            return new Color(255, 250, 250);
        }
    },
    SnowTemp {
        public boolean isBlocked()
        {
            return false;
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
