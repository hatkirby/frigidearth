/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hatkirby
 */
public class MessageOverlay implements Renderable
{
    private final int TILE_WIDTH = 12;
    private final int TILE_HEIGHT = 12;
    
    List<String> message;
    int x;
    int y;
    int width;
    
    public MessageOverlay(String msg, int x, int y, int width)
    {
        message = new ArrayList<String>();
        this.x = x;
        this.y = y;
        this.width = width;
        
        String temp = msg;
        
        while (temp.length() > width)
        {
            String shortm = temp.substring(0, width);
            
            if ((temp.charAt(width) == ' ') || (shortm.endsWith(" ")))
            {
                message.add(shortm);
                temp = temp.substring(width);
            } else {
                int lastSpace = shortm.lastIndexOf(" ");
                message.add(shortm.substring(0, lastSpace));
                temp = temp.substring(lastSpace);
            }
        }
        
        message.add(temp);
    }

    public void render(Graphics2D g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(x*TILE_WIDTH, y*TILE_HEIGHT, (x+width)*TILE_WIDTH, (y+message.size())*TILE_HEIGHT);
        
        for (int i=0; i<message.size(); i++)
        {
            for (int j=0; j<message.get(i).length(); j++)
            {
                g.drawImage(SystemFont.getCharacter(message.get(i).charAt(j), Color.WHITE), (x+j)*TILE_WIDTH, (y+i)*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, null);
            }
        }
    }
    
}
