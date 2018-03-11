package com.example.agodlin.strengthlog;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;

import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.common.Exercise;
import com.example.agodlin.strengthlog.db.DataManager;
import com.example.agodlin.strengthlog.db.DummyData;
import com.example.agodlin.strengthlog.db.sql.AppSqlDBHelper;
import com.example.agodlin.strengthlog.ui.weight.BodyWeightItem;
import com.example.agodlin.strengthlog.utils.FileIO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
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
        BodyWeightItem item = new BodyWeightItem(date, 1, "");
        Gson gson = new Gson();
        String jsonString = gson.toJson(item);

        String filename = "myfile";

        FileIO.writePrivate(jsonString.getBytes(), context, filename);

        String jsonTest = new String(FileIO.readPrivate(context, filename));

        assertEquals(jsonString, jsonTest);

        context.deleteFile(filename);
    }
    @Test
    public void saveDataFromDB() throws Exception {
        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
        }
        AppSqlDBHelper appSqlDBHelper = new AppSqlDBHelper(context);
        DummyData.init(context);
        DataManager.init(context);
        List<Exercise> exercises = DataManager.read();
        Gson gson = new Gson();
        String jsonString = gson.toJson(exercises);

        Type listType = new TypeToken<ArrayList<Exercise>>(){}.getType();
        List<Exercise> list2 = new Gson().fromJson(jsonString, listType);
        assertArrayEquals(exercises.toArray(), list2.toArray());
    }
}
