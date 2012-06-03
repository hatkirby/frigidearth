/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fourisland.frigidearth;

import java.awt.image.RGBImageFilter;

/**
 *
 * @author hatkirby
 */
public class TransparentPixelFilter extends RGBImageFilter {

    private int trans;
    private int replace;
    public TransparentPixelFilter(int i, int j)
    {
        trans = i;
        replace = j;
        
        canFilterIndexColorModel = true;
    }

    @Override
    public int filterRGB(int x, int y, int rgb)
    {
        if (rgb == trans)
        {
            return 0;
        } else {
            return replace;
        }
    }

}
