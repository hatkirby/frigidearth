/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.awt.Point;
import java.awt.event.KeyEvent;

/**
 *
 * @author hatkirby
 */
public enum Direction
{
    North {
        public Point to(Point pos)
        {
            return new Point(pos.x, pos.y-1);
        }
    },
    East {
        public Point to(Point pos)
        {
            return new Point(pos.x+1, pos.y);
        }
    },
    South {
        public Point to(Point pos)
        {
            return new Point(pos.x, pos.y+1);
        }
    },
    West {
        public Point to(Point pos)
        {
            return new Point(pos.x-1, pos.y);
        }
    };
    
    public abstract Point to(Point pos);
    
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
    
    public static Direction fromKeyEvent(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_UP: return North;
            case KeyEvent.VK_RIGHT: return East;
            case KeyEvent.VK_DOWN: return South;
            case KeyEvent.VK_LEFT: return West;
            default: return null;
        }
    }
}
