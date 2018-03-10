package com.example.agodlin.strengthlog.db;

import android.content.Context;

import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.common.Exercise;
import com.example.agodlin.strengthlog.db.sql.AppSqlDBHelper;
import com.example.agodlin.strengthlog.ui.weight.BodyWeightItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by agodlin on 7/21/2017.
 */

public class DataManager {
    //TODO add cache logic

    private  static AppSqlDBHelper appSqlDBHelper;

    private static List<BodyWeightItem> bodyWeightItems;

    public static void init(Context context)
    {
        appSqlDBHelper = new AppSqlDBHelper(context);
        bodyWeightItems = appSqlDBHelper.readBodyWeight();
    }

    public static List<BodyWeightItem> readBodyWeight()
    {
        return bodyWeightItems;
    }

    public static List<Date> getDates()
    {
        Set<Date> dates = new HashSet<>();
        List<Exercise> exercises = read();
        for(Exercise e : exercises)
            dates.add(e.date);
        return new ArrayList<>(dates);
    }

    public static List<String> getNames()
    {
        Set<String> names = new HashSet<>();
        List<Exercise> exercises = read();
        for(Exercise e : exercises)
            names.add(e.name);
        return new ArrayList<>(names);
    }

    public static void addBodyWeight(List<BodyWeightItem> bodyWeightItems)
    {
        for(BodyWeightItem bodyWeightItem : bodyWeightItems) {
            bodyWeightItems.add(bodyWeightItem);
            appSqlDBHelper.insertWeight(bodyWeightItem);
        }
    }

    public static void add(BodyWeightItem bodyWeightItem)
    {
        bodyWeightItems.add(bodyWeightItem);
        appSqlDBHelper.insertWeight(bodyWeightItem);
    }

    public static void add(BodyWeightItem bodyWeightItem, int position)
    {
        bodyWeightItems.add(position, bodyWeightItem);
        appSqlDBHelper.insertWeight(bodyWeightItem);
    }

    public static List<Exercise> read()
    {
        List<Exercise> exercises = appSqlDBHelper.readAll();
        return exercises;
    }

    public static void add(List<Exercise> exercises)
    {
        for(Exercise e : exercises)
            appSqlDBHelper.insert(e);
    }

    public static void add(Exercise exercise)
    {
        appSqlDBHelper.insert(exercise);
    }

    public static List<Exercise> read(Date date)
    {
        List<Exercise> exercises = appSqlDBHelper.readAll(date);
        return exercises;
    }

    public static List<Exercise> read(String name)
    {
        List<Exercise> exercises = appSqlDBHelper.readAll(name);
        return exercises;
    }

    static public void addNewExercise(String exerciseName)
    {

    }

    static public void addNewWorkout(Date date)
    {

    }

    public static void delete(Exercise e)
    {
        appSqlDBHelper.deleteExercise(e);
    }

    public static void delete(BodyWeightItem bodyWeightItem)
    {
        appSqlDBHelper.deleteBodyWeight(bodyWeightItem);
    }

    public static void updateExercise(Exercise e)
    {
        appSqlDBHelper.update(e);
    }
}
