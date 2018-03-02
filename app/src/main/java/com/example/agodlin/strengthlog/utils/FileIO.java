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

    static public void writePrivate(byte[] bytes, Context context, String filename)
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

    static public byte[] readPrivate(Context context, String filename)
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

    static public void writeStorage(byte[] bytes, String filename, String subfolder)
    {
        File dir = getAlbumStorageDir(subfolder);
        File file = new File(dir, filename);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public byte[] readStorage(String filename, String subfolder)
    {
        File dir = getAlbumStorageDir(subfolder);
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

    static public File getAlbumStorageDir(String subfolder) {
        // Get the directory for the user's public pictures directory.
        if (subfolder == null)
        {
            File file = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM);
            return file;
        }
        else
        {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM), subfolder);
            if (!file.mkdirs()) {
                Log.e(TAG, "Directory not created");
            }
            return file;
        }
    }
}
