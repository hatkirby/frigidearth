/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author hatkirby
 */
public class InventoryOverlay implements Renderable, Inputable
{
    private final int TILE_WIDTH = 12;
    private final int TILE_HEIGHT = 12;
    private final int LIST_HEIGHT = 13;
    
    private int id = 0;
    private int scroll = 0;
    private int width = 0;
    
    public InventoryOverlay()
    {
        for (Item item : Main.currentGame.inventory)
        {
            if (item.getItemName().length() > width)
            {
                width = item.getItemName().length();
            }
        }
        
        width += 2;
    }
    
    public void render(Graphics2D g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(TILE_WIDTH, TILE_HEIGHT, width*TILE_WIDTH, Math.min(LIST_HEIGHT+2,Main.currentGame.inventory.size()+1)*TILE_HEIGHT);
        
        if (scroll > 0)
        {
            g.drawImage(SystemFont.getCharacter((char) 24, Color.WHITE), (1+width/2)*TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
        }
        
        for (int i=scroll; i<Math.min(scroll+LIST_HEIGHT, Main.currentGame.inventory.size()); i++)
        {
            if (i == id)
            {
                g.drawImage(SystemFont.getCharacter((char) 26, Color.WHITE), TILE_WIDTH, (i-scroll+2)*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
            }
            
            String name = Main.currentGame.inventory.get(i).getItemName().toLowerCase();
            for (int j=0; j<name.length(); j++)
            {
                g.drawImage(SystemFont.getCharacter(name.charAt(j), Main.currentGame.inventory.get(i).getDisplayColor()), (3+j)*TILE_WIDTH, (i-scroll+2)*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
            }
        }
        
        if (scroll + LIST_HEIGHT < Main.currentGame.inventory.size())
        {
            g.drawImage(SystemFont.getCharacter((char) 25, Color.WHITE), (1+width/2)*TILE_WIDTH, (LIST_HEIGHT+2)*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
        }
    }

    public void processInput(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_ESCAPE:
                Main.removeRenderable(this);
                Main.removeInputable(this);
                
                return;
                
            case KeyEvent.VK_UP:
                if (id != 0)
                {
                    id--;
                    
                    if (id < scroll)
                    {
                        scroll=id;
                    }
                }
                
                break;
                
            case KeyEvent.VK_DOWN:
                if (id != (Main.currentGame.inventory.size()-1))
                {
                    id++;
                    
                    if (id > (scroll+LIST_HEIGHT-1))
                    {
                        scroll++;
                    }
                }
                
                break;
                
            case KeyEvent.VK_ENTER:
                if (Main.currentGame.inventory.get(id).useItem())
                {
                    Main.currentGame.inventory.remove(id);
                    ((MapViewGameState) Main.getGameState()).tick();
                    Main.removeRenderable(this);
                    Main.removeInputable(this);
                    
                    return;
                }
        }
    }
    
}
