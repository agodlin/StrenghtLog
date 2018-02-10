package com.example.agodlin.strengthlog.db;

import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.common.Exercise;

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

    public static void init()
    {
        DummyData.init();
        workouts = DummyData.workouts;
        exercises = DummyData.exercises;
    }

    static public void addNewExercise(String exerciseName)
    {
        exercises.put(exerciseName, new ArrayList<Exercise>());
    }

    static public void addNewWorkout(Date date)
    {
        workouts.put(date, new ArrayList<Exercise>());
    }
}
