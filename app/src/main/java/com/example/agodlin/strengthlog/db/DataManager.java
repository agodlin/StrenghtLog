package com.example.agodlin.strengthlog.db;

import android.content.Context;

import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.common.Exercise;
import com.example.agodlin.strengthlog.db.sql.AppSqlDBHelper;
import com.example.agodlin.strengthlog.ui.weight.BodyWeightItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by agodlin on 7/21/2017.
 */

public class DataManager {
    public static Map<Date, List<Exercise>> workouts = new HashMap<>();
    public static Map<String, List<Exercise>> exercises = new HashMap<>();
    public static List<Exercise> exercisesList = new ArrayList<>();
    public static List<BodyWeightItem> bodyWeightItems;
    public  static AppSqlDBHelper appSqlDBHelper;
    public static void init(Context context)
    {
        Map<Date, List<Exercise>> workouts = new HashMap<>();
        Map<String, List<Exercise>> exercises = new HashMap<>();
        //TODO readPrivate from database
        appSqlDBHelper = new AppSqlDBHelper(context);

        List<Exercise> exerciseList= appSqlDBHelper.readAll();
        bodyWeightItems = appSqlDBHelper.readBodyWeight();
        setAll(exerciseList);
    }

    static public void setAll(List<Exercise> exerciseList)
    {
        workouts = new HashMap<>();
        exercises = new HashMap<>();
        exercisesList = exerciseList;
        for(Exercise exercise : exerciseList)
        {
            String name = exercise.name;
            Date date = exercise.date;
            if (!workouts.containsKey(date))
                workouts.put(date, new ArrayList<Exercise>());
            if (!exercises.containsKey(name))
                exercises.put(name, new ArrayList<Exercise>());
            workouts.get(date).add(exercise);
            exercises.get(name).add(exercise);
        }
        DataManager.workouts = workouts;
        DataManager.exercises = exercises;
    }

    static public List<Exercise> readAll()
    {
        return exercisesList;
    }

    static public void addNewExercise(String exerciseName)
    {
        exercises.put(exerciseName, new ArrayList<Exercise>());
    }

    static public void addNewWorkout(Date date)
    {
        workouts.put(date, new ArrayList<Exercise>());
    }

    public static void delete(Exercise e)
    {
        appSqlDBHelper.deleteExercise(e);
    }

    public static void delete(BodyWeightItem bodyWeightItem)
    {
        appSqlDBHelper.deleteBodyWeight(bodyWeightItem);
        bodyWeightItems = appSqlDBHelper.readBodyWeight();
    }
}
