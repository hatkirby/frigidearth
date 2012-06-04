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
public class Game
{
    public int health = 15;
    public int maxHealth = 15;
    public int defense = 0;
    public int level = 1;
    public int experience = 0;
    public List<Item> inventory = new ArrayList<Item>();
}
