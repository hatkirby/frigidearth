/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hatkirby
 */
public class GridSpace
{
    public Tile tile;
    public boolean lit;
    public List<Item> items;
    
    public GridSpace()
    {
        tile = Tile.Unused;
        lit = false;
        items = new ArrayList<Item>();
    }
}
