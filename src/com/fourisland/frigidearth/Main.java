/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Random;
import javax.swing.JFrame;

/**
 *
 * @author hatkirby
 */
public class Main
{
    static final int GAME_WIDTH = 15;
    static final int GAME_HEIGHT = 10;
    static int TILE_WIDTH = 32;
    static int TILE_HEIGHT = 32;
    
    private static JFrame mainWindow;
    private static Color[][] grid;
    private static int drawOffsetX = 0;
    private static int drawOffsetY = 0;
    
    public static void main(String[] args)
    {
        mainWindow = new JFrame("Frigid Earth");
        mainWindow.setSize(GAME_WIDTH*TILE_WIDTH, GAME_HEIGHT*TILE_HEIGHT + mainWindow.getInsets().top);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce)
            {
                drawOffsetX = 0;
                drawOffsetY = 0;
                
                TILE_WIDTH = mainWindow.getContentPane().getWidth() / GAME_WIDTH;
                drawOffsetX = mainWindow.getWidth() % GAME_WIDTH / 2;
                TILE_HEIGHT = mainWindow.getContentPane().getHeight() / GAME_HEIGHT;
                drawOffsetY = mainWindow.getHeight() % GAME_HEIGHT / 2;
                
                if (TILE_WIDTH > TILE_HEIGHT)
                {
                    TILE_WIDTH = TILE_HEIGHT;
                    drawOffsetX = (mainWindow.getContentPane().getWidth() - (GAME_WIDTH * TILE_WIDTH)) / 2;
                } else if (TILE_HEIGHT > TILE_WIDTH)
                {
                    TILE_HEIGHT = TILE_WIDTH;
                    drawOffsetY = (mainWindow.getContentPane().getHeight() - (GAME_HEIGHT * TILE_HEIGHT)) / 2;
                }
                
                redrawScreen();
            }
        });
        mainWindow.setVisible(true);
        
        Random r = new Random();
        grid = new Color[GAME_WIDTH][GAME_HEIGHT];
        
        for (int x=0; x<GAME_WIDTH; x++)
        {
            for (int y=0; y<GAME_HEIGHT; y++)
            {
                grid[x][y] = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
            }
        }
        
        redrawScreen();
    }
    
    private static void redrawScreen()
    {
        Graphics g = mainWindow.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, mainWindow.getWidth(), mainWindow.getHeight());
        
        for (int x=0; x<GAME_WIDTH; x++)
        {
            for (int y=0; y<GAME_HEIGHT; y++)
            {
                g.setColor(grid[x][y]);
                g.fillRect(x*TILE_WIDTH + drawOffsetX, y*TILE_HEIGHT + drawOffsetY + mainWindow.getInsets().top, TILE_WIDTH, TILE_HEIGHT);
            }
        }
    }
}
