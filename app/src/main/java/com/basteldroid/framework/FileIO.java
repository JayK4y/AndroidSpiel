package com.basteldroid.framework;

import android.content.SharedPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Jakob on 11.06.2015.
 */
public interface FileIO {

    public InputStream readFile(String file) throws IOException;

    public OutputStream writeFile(String file) throws IOException;

    public InputStream readAsset(String file) throws IOException;

    public SharedPreferences getSharedPref();
}
