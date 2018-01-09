package com.example.agodlin.strengthlog;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.ui.weight.dummy.BodyWeightContent;
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

        File file = new File(context.getFilesDir(), "tmp.json");

        Date date = new Date(1,1,2018);
        BodyWeightContent.BodyWeightItem item = new BodyWeightContent.BodyWeightItem(date, "test1", "test2");
        Gson gson = new Gson();
        String jsonString = gson.toJson(item);

        String filename = "myfile";
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonString.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileInputStream inputStream;

        try {
            inputStream = context.openFileInput(filename);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String value = new String(buffer);
            assertEquals(jsonString, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        context.deleteFile(filename);
    }
}
