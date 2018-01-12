package com.example.agodlin.strengthlog;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.ui.weight.dummy.BodyWeightContent;
import com.example.agodlin.strengthlog.utils.FileIO;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

/**
 * Created by agodlin on 1/12/2018.
 */
@RunWith(AndroidJUnit4.class)
public class jsonReadWriteTest {
    @Test
    public void useAppContext() throws Exception {

        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
        }

        Date date = new Date(1,1,2018);
        BodyWeightContent.BodyWeightItem item = new BodyWeightContent.BodyWeightItem(date, "test1", "test2");
        Gson gson = new Gson();
        String jsonString = gson.toJson(item);

        String filename = "myfile";

        FileIO.write(jsonString.getBytes(), context, filename);

        String jsonTest = new String(FileIO.read(context, filename));

        assertEquals(jsonString, jsonTest);

        context.deleteFile(filename);
    }
}
