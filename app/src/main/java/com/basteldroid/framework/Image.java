package com.basteldroid.framework;

import android.graphics.ImageFormat;

/**
 * Created by Jakob on 11.06.2015.
 */
public interface Image {

    public int getWidth();
    public int getHeight();
    public ImageFormat getFormat();
    public void dispose();
}
