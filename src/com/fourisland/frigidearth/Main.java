/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JFrame;

/**
 *
 * @author hatkirby
 */
public class Main
{
    static final int GAME_WIDTH = 320;
    static final int GAME_HEIGHT = 240;
    
    private static JFrame mainWindow;
    private static Color[][] grid;
    private static int drawOffsetX = 0;
    private static int drawOffsetY = 0;
    private static Canvas gameCanvas;
    private static List<Renderable> renderables = new CopyOnWriteArrayList<Renderable>();
    private static GameState gameState;
    
    public static void main(String[] args)
    {
        gameCanvas = new Canvas();
        
        mainWindow = new JFrame();
        mainWindow.setTitle("Frigid Earth");
        mainWindow.setSize(GAME_WIDTH*2, GAME_HEIGHT*2);
        mainWindow.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().x-GAME_WIDTH, GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().y-GAME_HEIGHT);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.add(gameCanvas);
        mainWindow.setVisible(true);
        
        gameCanvas.createBufferStrategy(2);
        
        gameState = new TileGameState();
        renderables.add(gameState);
        
        gameState.tick();
        
        for (;;)
        {
            render(gameCanvas);
            
            try
            {
                Thread.sleep(10);
            } catch (InterruptedException ex)
            {
                //Nothing
            }
        }
    }
    
    public static void render(Canvas gameCanvas)
    {
        BufferStrategy buffer = gameCanvas.getBufferStrategy();
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration config = device.getDefaultConfiguration();
        BufferedImage vImg = config.createCompatibleImage(GAME_WIDTH, GAME_HEIGHT, Transparency.TRANSLUCENT);
        Graphics2D g = vImg.createGraphics();
        
        for (Renderable renderable : renderables)
        {
            renderable.render(g);
        }

        do {
            do {
                float wt = mainWindow.getWidth() / (float) GAME_WIDTH;
                float ht = (mainWindow.getHeight() - mainWindow.getInsets().top) / (float) GAME_HEIGHT;
                int renderWidth = Math.round(Math.min(wt, ht) * GAME_WIDTH);
                int renderHeight = Math.round(Math.min(wt, ht) * GAME_HEIGHT);
                int renderX = (mainWindow.getWidth()/2)-(renderWidth/2);
                int renderY = ((mainWindow.getHeight()-mainWindow.getInsets().top)/2)-(renderHeight/2);
                
                Graphics g2 = buffer.getDrawGraphics();
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, mainWindow.getWidth(), mainWindow.getHeight());
                g2.drawImage(vImg, renderX, renderY, renderWidth, renderHeight, gameCanvas);
                g2.dispose();
            } while (buffer.contentsRestored());

            buffer.show();
        } while (buffer.contentsLost());

        
        Toolkit.getDefaultToolkit().sync();
    }
}
