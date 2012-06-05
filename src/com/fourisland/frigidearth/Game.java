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
    public int level = 1;
    public int experience = 0;
    public List<Item> inventory = new ArrayList<Item>();
    private Item helmet = null;
    private Item sword = null;
    private Item shield = null;
    private Item ring = null;
    private Item shoes = null;
    
    public boolean equipItem(final Item i)
    {
        if ((i.getItemType() != ItemType.Helmet) && (i.getItemType() != ItemType.Sword) && (i.getItemType() != ItemType.Shield) && (i.getItemType() != ItemType.Ring) && (i.getItemType() != ItemType.Shoes))
        {
            throw new IllegalArgumentException("You can only equip helmets, swords, shields, rings and shoes.");
        }
        
        boolean alreadyEquipped = false;
        boolean alreadyEquippedSame = false;
        Item equippedItem = null;
        
        switch (i.getItemType())
        {
            case Helmet:
                if (helmet != null)
                {
                    alreadyEquipped = true;
                    equippedItem = helmet;
                    
                    if (helmet == i)
                    {
                        alreadyEquippedSame = true;
                    }
                }
                
                break;
                
            case Sword:
                if (sword != null)
                {
                    alreadyEquipped = true;
                    equippedItem = sword;
                    
                    if (sword == i)
                    {
                        alreadyEquippedSame = true;
                    }
                }
                
                break;
                
            case Shield:
                if (shield != null)
                {
                    alreadyEquipped = true;
                    equippedItem = shield;
                    
                    if (shield == i)
                    {
                        alreadyEquippedSame = true;
                    }
                }
                
                break;
                
            case Ring:
                if (ring != null)
                {
                    alreadyEquipped = true;
                    equippedItem = ring;
                    
                    if (ring == i)
                    {
                        alreadyEquippedSame = true;
                    }
                }
                
                break;
                
            case Shoes:
                if (shoes != null)
                {
                    alreadyEquipped = true;
                    equippedItem = shoes;
                    
                    if (shoes == i)
                    {
                        alreadyEquippedSame = true;
                    }
                }
                
                break;
        }
        
        if (alreadyEquippedSame)
        {
            ((MapViewGameState) Main.getGameState()).printMessage("You have already equipped " + i.getItemName().toLowerCase());
            
            return false;
        } else if (alreadyEquipped)
        {
            inventory.add(equippedItem);
            ((MapViewGameState) Main.getGameState()).printMessage("You unequip your " + equippedItem.getItemName().toLowerCase() + " and equip " + i.getItemName().toLowerCase());
        } else {
            ((MapViewGameState) Main.getGameState()).printMessage("You equip " + i.getItemName().toLowerCase());
        }
        
        switch (i.getItemType())
        {
            case Helmet:
                helmet = i;
                break;
                
            case Sword:
                sword = i;
                break;
                
            case Shield:
                shield = i;
                break;
                
            case Ring:
                ring = i;
                break;
                
            case Shoes:
                shoes = i;
                break;
        }
        
        return true;
    }
    
    public int getAttackPower()
    {
        return (int) (Math.floor(Math.sqrt(level)) + (sword == null ? 0 : sword.getAttackPower()));
    }
    
    public int getDefense()
    {
        return (helmet == null ? 0 : helmet.getDefense()) + (shield == null ? 0 : shield.getDefense()) + (shoes == null ? 0 : shoes.getDefense());
    }
}
