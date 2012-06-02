/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import javax.swing.JFrame;

/**
 *
 * @author hatkirby
 */
public class Main
{
    static final int GAME_WIDTH = 15;
    static final int GAME_HEIGHT = 10;
    static final int TILE_WIDTH = 32;
    static final int TILE_HEIGHT = 32;
    
    public static void main(String[] args)
    {
        JFrame mainWindow = new JFrame("Frigid Earth");
        mainWindow.setSize(GAME_WIDTH*TILE_WIDTH, GAME_HEIGHT*TILE_HEIGHT);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }
}
