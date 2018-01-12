package com.example.agodlin.strengthlog.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static android.content.ContentValues.TAG;

/**
 * Created by agodlin on 1/12/2018.
 */

public class FileIO {
    static public void write(byte[] bytes, Context context, String filename)
    {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(bytes);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public byte[] read(Context context, String filename)
    {
        FileInputStream inputStream;

        try {
            inputStream = context.openFileInput(filename);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static public void write2(byte[] bytes, String filename)
    {
        File dir = getAlbumStorageDir("test");
        File file = new File(dir, filename);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public byte[] read2(String filename)
    {
        File dir = getAlbumStorageDir("test");
        File file = new File(dir, filename);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file2 = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), albumName);
        File file = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }
}
