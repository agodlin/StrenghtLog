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
import com.example.agodlin.strengthlog.db.sql.AppSqlDBHelper;
import com.example.agodlin.strengthlog.ui.weight.dummy.BodyWeightContent;
import com.example.agodlin.strengthlog.utils.FileIO;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by agodlin on 2/12/2018.
 */
@RunWith(AndroidJUnit4.class)
public class sqlInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {

        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
        }
        DataManager.init();
        AppSqlDBHelper appSqlDBHelper = new AppSqlDBHelper(context);
        appSqlDBHelper.onCreate(appSqlDBHelper.getWritableDatabase());
        Exercise exercise = DataManager.exercises.get("press").get(0);
        appSqlDBHelper.insert(exercise);
        appSqlDBHelper.read();
    }
}
