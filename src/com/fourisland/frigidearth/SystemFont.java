/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.frigidearth;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author hatkirby
 */
public class SystemFont
{
    private static BufferedImage fontGraphic = null;
    
    public static void initalize()
    {
        try
        {
            BufferedImage temp = ImageIO.read(SystemFont.class.getResourceAsStream("resources/font2.png"));
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = env.getDefaultScreenDevice();
            GraphicsConfiguration config = device.getDefaultConfiguration();
            fontGraphic = config.createCompatibleImage(temp.getWidth(), temp.getHeight(), Transparency.TRANSLUCENT);
            fontGraphic.createGraphics().drawImage(Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(temp.getSource(), new TransparentPixelFilter(temp.getRGB(0, 0)))),0,0,null);
        } catch (IOException ex)
        {
            Logger.getLogger(SystemFont.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static BufferedImage getCharacter(char c)
    {
        if (fontGraphic == null)
        {
            initalize();
        }
        
        return fontGraphic.getSubimage((c % 16) * 12, (c / 16) * 12, 12, 12);
    }
}
