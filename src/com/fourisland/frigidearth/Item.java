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
    ScrollOfHealing("Scroll of Healing", '~', Color.YELLOW, ItemType.Scroll) {
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
    WoodenHelmet("Wooden Helmet", '^', new Color(128, 42, 42), ItemType.Helmet) {
        public int getDefense()
        {
            return 1;
        }
    },
    WoodenSword("Wooden Sword", '/', new Color(128, 42, 42), ItemType.Sword) {
        public int getAttackPower()
        {
            return 1;
        }
    },
    WoodenShield("Wooden Shield", 'O', new Color(128, 42, 42), ItemType.Shield) {
        public int getDefense()
        {
            return 1;
        }
    },
    RingOfRegeneration("Ring of Regeneration", 'o', Color.YELLOW, ItemType.Ring) {},
    Crocs("Crocs", 'd', new Color(128, 42, 42), ItemType.Shoes),
    GlassHelmet("Glass Helmet", '^', Color.CYAN, ItemType.Helmet),
    GlassSword("Glass Sword", '/', Color.CYAN, ItemType.Sword),
    GlassShield("Glass Shield", 'O', Color.CYAN, ItemType.Shield),
    WeddingRing("Wedding Ring", 'o', Color.YELLOW, ItemType.Ring),
    GlassSlippers("Glass Slippers", 'd', Color.CYAN, ItemType.Shoes),
    Key("Key", 'k', Color.YELLOW, ItemType.Special) {
        public boolean useItem()
        {
            ((MapViewGameState) Main.getGameState()).printMessage("There's nothing to use the key on!");
            
            return false;
        }
    };
    
    private String itemName;
    private char displayChar;
    private Color displayColor;
    private ItemType itemType;
    
    Item(String m_itemName, char m_displayChar, Color m_displayColor, ItemType m_itemType)
    {
        itemName = m_itemName;
        displayChar = m_displayChar;
        displayColor = m_displayColor;
        itemType = m_itemType;
    }
    
    public String getItemName()
    {
        return itemName;
    }
    
    public char getDisplayCharacter()
    {
        return displayChar;
    }
    
    public Color getDisplayColor()
    {
        return displayColor;
    }
    
    public ItemType getItemType()
    {
        return itemType;
    }
    
    public boolean useItem()
    {
        switch (getItemType())
        {
            case Helmet:
            case Sword:
            case Shield:
            case Ring:
            case Shoes:
                return Main.currentGame.equipItem(this);
                
            default:
                throw new java.lang.AbstractMethodError();
        }
    }
    
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
    
    public int getAttackPower()
    {
        return 0;
    }
    
    public int getDefense()
    {
        return 0;
    }
}
