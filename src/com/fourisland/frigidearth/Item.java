/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.awt.Color;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author hatkirby
 */
public enum Item
{
    ScrollOfHealing {
        public String getItemName()
        {
            return "Scroll of Healing";
        }
        
        public char getDisplayCharacter()
        {
            return '~';
        }
        
        public Color getDisplayColor()
        {
            return Color.YELLOW;
        }
        
        public ItemType getItemType()
        {
            return ItemType.Scroll;
        }
        
        public boolean useItem()
        {
            Main.currentGame.health += 20;
            
            if (Main.currentGame.health > Main.currentGame.maxHealth)
            {
                Main.currentGame.health = Main.currentGame.maxHealth;
            }
            
            return true;
        }
    },
    Key {
        public String getItemName()
        {
            return "Key";
        }
        
        public char getDisplayCharacter()
        {
            return 'k';
        }
        
        public Color getDisplayColor()
        {
            return Color.YELLOW;
        }
        
        public ItemType getItemType()
        {
            return ItemType.Special;
        }
        
        public boolean useItem()
        {
            return false;
        }
    };
    
    public abstract String getItemName();
    public abstract char getDisplayCharacter();
    public abstract Color getDisplayColor();
    public abstract ItemType getItemType();
    public abstract boolean useItem();
    
    public static Item getWeightedRandomItem()
    {
        Map<ItemType,Integer> typeCounts = new EnumMap<ItemType,Integer>(ItemType.class);
        for (Item item : Item.values())
        {
            if (typeCounts.containsKey(item.getItemType()))
            {
                typeCounts.put(item.getItemType(), typeCounts.get(item.getItemType())+1);
            } else {
                typeCounts.put(item.getItemType(), 1);
            }
        }
        
        List<Double> probabilities = new ArrayList<Double>();
        for (Item item : Item.values())
        {
            if (probabilities.isEmpty())
            {
                probabilities.add(item.getItemType().getRarity() / typeCounts.get(item.getItemType()));
            } else {
                probabilities.add(item.getItemType().getRarity() / typeCounts.get(item.getItemType()) + probabilities.get(probabilities.size()-1));
            }
        }
        
        Random r = new Random();
        double num = r.nextDouble();
        int i=0;
        for (Item item : Item.values())
        {
            if (num < probabilities.get(i))
            {
                return item;
            }
            
            i++;
        }
        
        return null;
    }
}
