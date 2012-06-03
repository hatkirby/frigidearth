/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

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
        
        public char getDisplayCharacter()
        {
            return 'X';
        }
    },
    DirtWall {
        public boolean isBlocked()
        {
            return true;
        }
        
        public char getDisplayCharacter()
        {
            return '#';
        }
    },
    DirtFloor {
        public boolean isBlocked()
        {
            return false;
        }
        
        public char getDisplayCharacter()
        {
            return ' ';
        }
    },
    StoneWall {
        public boolean isBlocked()
        {
            return true;
        }
        
        public char getDisplayCharacter()
        {
            return 'X';
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
    public abstract char getDisplayCharacter();
}
