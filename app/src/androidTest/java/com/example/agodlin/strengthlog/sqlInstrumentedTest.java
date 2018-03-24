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
import com.example.agodlin.strengthlog.utils.DateHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;
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
        AppSqlDBHelper appSqlDBHelper = new AppSqlDBHelper(context);
        DummyData.init(context);
        DataManager.init(context);
        assertEquals(appSqlDBHelper.readAll().size(), 80);

        Date date = new Date(21,12,2017);
        Exercise exercise = DummyData.exercise("press", date, DummyData.sets(new int[]{5,5,5,5,5}, 60));

        appSqlDBHelper.insert(exercise);
        List<Exercise> exerciseList= appSqlDBHelper.readAll();
        assertEquals(exerciseList.size(), 81);

        Exercise exercise2 = DummyData.exercise("bench", date, DummyData.sets(new int[]{8,8,8}, 80));
        appSqlDBHelper.insert(exercise2);
        exerciseList= appSqlDBHelper.readAll();

        assertEquals(exerciseList.size(), 82);
    }
    @Test
    public void testCalendar() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
        }
        AppSqlDBHelper appSqlDBHelper = new AppSqlDBHelper(context);
        Date date = new Date(11,2,2018);
        Exercise exercise = DummyData.exercise("press", date, DummyData.sets(new int[]{5,5,5,5,5}, 60));
        appSqlDBHelper.insert(exercise);
        assertEquals(appSqlDBHelper.readAll(date).size(), 1);
        assertEquals(appSqlDBHelper.readAll(new Date(12,2,2018)).size(), 0);
        assertEquals(appSqlDBHelper.readAll(new Date(13,2,2018)).size(), 0);
        Exercise exercise2 = DummyData.exercise("press", new Date(13,2,2018), DummyData.sets(new int[]{5,5,5,5,5}, 60));
        appSqlDBHelper.insert(exercise2);
        assertEquals(appSqlDBHelper.readAll(date).size(), 1);
        assertEquals(appSqlDBHelper.readAll(new Date(12,2,2018)).size(), 0);
        assertEquals(appSqlDBHelper.readAll(new Date(13,2,2018)).size(), 1);
    }

    @Test
    public void testCalendar2() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
        }
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int m = calendar.get(Calendar.MONTH);
        long milisToday = calendar.getTimeInMillis();
        long milis30Days = DateHelper.subtractMonths(milisToday, 1);
        calendar.setTimeInMillis(milis30Days);
        assertEquals(m-1, calendar.get(Calendar.MONTH));
        assertEquals(day, calendar.get(Calendar.DATE));
    }

}
