package com.example.agodlin.strengthlog.db;

import android.content.Context;

import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.common.Exercise;
import com.example.agodlin.strengthlog.common.Set;
import com.example.agodlin.strengthlog.db.sql.AppSqlDBHelper;
import com.example.agodlin.strengthlog.ui.weight.BodyWeightItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by agodlin on 1/19/2018.
 */

public class DummyData {
    public static Map<Date, List<Exercise>> workouts = new HashMap<>();
    public static Map<String, List<Exercise>> exercises = new HashMap<>();
    public static List<BodyWeightItem> bodyWeightItems = new ArrayList<>();

    public static void init(Context context){
        //TODO populate data with dummy initial data.
        exercises.put("squat", new ArrayList<Exercise>());
        exercises.put("deadlift", new ArrayList<Exercise>());
        exercises.put("lunges", new ArrayList<Exercise>());
        exercises.put("leg-curl", new ArrayList<Exercise>());
        exercises.put("twins", new ArrayList<Exercise>());
        exercises.put("bench", new ArrayList<Exercise>());
        exercises.put("press", new ArrayList<Exercise>());
        exercises.put("chin-up", new ArrayList<Exercise>());
        exercises.put("triceps", new ArrayList<Exercise>());
        exercises.put("biceps", new ArrayList<Exercise>());

        day1();
        day2();
        day3();
        day4();
        day5();
        day6();
        day7();
        day8();
        day9();
        day10();
        day11();
        day12();
        day13();
        day14();
        day15();
        day16();
        saveToSQL(context);
    }

    static void saveToSQL(Context context)
    {
        AppSqlDBHelper appSqlDBHelper = new AppSqlDBHelper(context);
        for(Date key : workouts.keySet()) {
            for (Exercise exercise : workouts.get(key))
                appSqlDBHelper.insert(exercise);
        }
        for(BodyWeightItem item : bodyWeightItems)
        {
            appSqlDBHelper.insertWeight(item);
        }
        workouts = null;
        exercises = null;
    }

    static List<Exercise> day1()
    {
        Date date = new Date(10,11,2017);
        bodyWeightItems.add(new BodyWeightItem(date, 80, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("squat", date, sets(new int[]{5,5,5}, 60)));
        exerciseList.add(exercise("deadlift", date, sets(new int[]{3, 1}, 152.5)));
        exerciseList.get(1).sets.addAll(sets(new int[]{5, 5, 5}, 130));
        exerciseList.add(exercise("lunges", date, sets(new int[]{8,8,8}, 35)));
        exerciseList.add(exercise("leg-curl", date, sets(new int[]{6,6,6}, 25)));
        exerciseList.add(exercise("twins", date, sets(new int[]{8,8,8}, 80)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }

        return exerciseList;
    }
    static List<Exercise> day2() {
        Date date = new Date(11,11,2017);
        bodyWeightItems.add(new BodyWeightItem(date, 90, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("bench", date, sets(new int[]{5,5,5,5,5}, 102.5)));
        exerciseList.add(exercise("press", date, sets(new int[]{6,6,6}, 50)));
        exerciseList.add(exercise("chin-up", date, sets(new int[]{5,5,5}, 30)));
        exerciseList.add(exercise("triceps", date, sets(new int[]{10,10,10}, 25)));
        exerciseList.add(exercise("biceps", date, sets(new int[]{10,10,10}, 20)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }

        return exerciseList;
    }
    static List<Exercise> day3() {
        Date date = new Date(13,11,2017);
        bodyWeightItems.add(new BodyWeightItem(date, 85, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("squat", date, sets(new int[]{5,5,5,5}, 60)));
        exerciseList.add(exercise("deadlift", date, sets(new int[]{5,5,5}, 140)));
        exerciseList.add(exercise("lunges", date, sets(new int[]{8,8,8}, 37.5)));
        exerciseList.add(exercise("leg-curl", date, sets(new int[]{6,6,6}, 25)));
        exerciseList.add(exercise("twins", date, sets(new int[]{8,8,8}, 80)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }

        return exerciseList;
    }
    static List<Exercise> day4() {
        Date date = new Date(14,11,2017);
        bodyWeightItems.add(new BodyWeightItem(date, 81, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("press", date, sets(new int[]{5,5,5,4,4}, 60)));
        exerciseList.add(exercise("bench", date, sets(new int[]{6,6,6}, 80)));
        exerciseList.add(exercise("chin-up", date, sets(new int[]{5,5,5}, 30)));
        exerciseList.add(exercise("triceps", date, sets(new int[]{10,10,10}, 25)));
        exerciseList.add(exercise("biceps", date, sets(new int[]{10,10,10}, 20)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }

        return exerciseList;
    }
    static List<Exercise> day5() {
        Date date = new Date(17,11,2017);
        bodyWeightItems.add(new BodyWeightItem(date, 79, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("squat", date, sets(new int[]{5,5,5}, 60)));
        exerciseList.add(exercise("deadlift", date, sets(new int[]{3}, 150)));
        exerciseList.get(1).sets.addAll(sets(new int[]{5}, 140));
        exerciseList.add(exercise("lunges", date, sets(new int[]{6,6,6}, 40)));
        exerciseList.add(exercise("leg-curl", date, sets(new int[]{6,6,6}, 25)));
        exerciseList.add(exercise("twins", date, sets(new int[]{8,8,8}, 80)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }

        return exerciseList;
    }
    static List<Exercise> day6() {
        Date date = new Date(18,11,2017);
        bodyWeightItems.add(new BodyWeightItem(date, 75, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("bench", date, sets(new int[]{5,3,3,3,3,3,3,3}, 105)));
        exerciseList.add(exercise("press", date, sets(new int[]{7,6,5}, 50)));
        exerciseList.add(exercise("chin-up", date, sets(new int[]{5,5,5}, 30)));
        exerciseList.add(exercise("triceps", date, sets(new int[]{10,10,10}, 25)));
        exerciseList.add(exercise("biceps", date, sets(new int[]{10,10,10}, 20)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }

        return exerciseList;
    }
    static List<Exercise> day7() {
        Date date = new Date(20,11,2017);
        bodyWeightItems.add(new BodyWeightItem(date, 80, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("squat", date, sets(new int[]{5,5,5,5}, 60)));
        exerciseList.add(exercise("deadlift", date, sets(new int[]{5,5,4}, 142.5)));
        exerciseList.add(exercise("lunges", date, sets(new int[]{8,8,8}, 40)));
        exerciseList.add(exercise("leg-curl", date, sets(new int[]{10,10,10}, 25)));
        exerciseList.add(exercise("twins", date, sets(new int[]{8,8,8}, 80)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }
        return exerciseList;
    }
    static List<Exercise> day8() {
        Date date = new Date(21,11,2017);
        bodyWeightItems.add(new BodyWeightItem(date, 88, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("press", date, sets(new int[]{5,5,5,5,5}, 60)));
        exerciseList.add(exercise("bench", date, sets(new int[]{8,8,8}, 80)));
        exerciseList.add(exercise("chin-up", date, sets(new int[]{5,5,5}, 30)));
        exerciseList.add(exercise("triceps", date, sets(new int[]{10,10,10}, 25)));
        exerciseList.add(exercise("biceps", date, sets(new int[]{10,10,10}, 20)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }
        return exerciseList;
    }

    static List<Exercise> day9() {
        Date date = new Date(24,11,2017);
        bodyWeightItems.add(new BodyWeightItem(date, 87, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("squat", date, sets(new int[]{5,5,5}, 65)));
        exerciseList.add(exercise("deadlift", date, sets(new int[]{10,10,10}, 100)));
        exerciseList.add(exercise("lunges", date, sets(new int[]{6,6,6}, 40)));
        exerciseList.add(exercise("leg-curl", date, sets(new int[]{6,6,6}, 25)));
        exerciseList.add(exercise("twins", date, sets(new int[]{8,8,8}, 80)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }
        return exerciseList;
    }
    static List<Exercise> day10() {
        Date date = new Date(25,11,2017);
        bodyWeightItems.add(new BodyWeightItem(date, 86, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("bench", date, sets(new int[]{5,5,5,5,4}, 105)));
        exerciseList.add(exercise("press", date, sets(new int[]{6,6,6}, 50)));
        exerciseList.add(exercise("chin-up", date, sets(new int[]{5,5,5}, 30)));
        exerciseList.add(exercise("triceps", date, sets(new int[]{10,10,10}, 25)));
        exerciseList.add(exercise("biceps", date, sets(new int[]{10,10,10}, 20)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }
        return exerciseList;
    }
    static List<Exercise> day11() {
        Date date = new Date(27,11,2017);
        bodyWeightItems.add(new BodyWeightItem(date, 85, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("squat", date, sets(new int[]{5,5}, 70)));
        exerciseList.add(exercise("deadlift", date, sets(new int[]{5,5,5,5}, 140)));
        exerciseList.add(exercise("lunges", date, sets(new int[]{6,6,6}, 40)));
        exerciseList.add(exercise("leg-curl", date, sets(new int[]{10,10,10}, 25)));
        exerciseList.add(exercise("twins", date, sets(new int[]{8,8,8}, 80)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }
        return exerciseList;
    }
    static List<Exercise> day12() {
        Date date = new Date(28,11,2017);
        bodyWeightItems.add(new BodyWeightItem(date, 84, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("press", date, sets(new int[]{5,5,4,3,4}, 62)));
        exerciseList.add(exercise("bench", date, sets(new int[]{8,8,8}, 83)));
        exerciseList.add(exercise("chin-up", date, sets(new int[]{5,5,5}, 26)));
        exerciseList.add(exercise("triceps", date, sets(new int[]{10,10,10}, 25)));
        exerciseList.add(exercise("biceps", date, sets(new int[]{10,10,10}, 20)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }
        return exerciseList;
    }

    static List<Exercise> day13() {
        Date date = new Date(14,0,2018);
        bodyWeightItems.add(new BodyWeightItem(date, 83, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("squat", date, sets(new int[]{5,5,5}, 65)));
        exerciseList.add(exercise("deadlift", date, sets(new int[]{10,10,10}, 100)));
        exerciseList.add(exercise("lunges", date, sets(new int[]{6,6,6}, 40)));
        exerciseList.add(exercise("leg-curl", date, sets(new int[]{6,6,6}, 25)));
        exerciseList.add(exercise("twins", date, sets(new int[]{8,8,8}, 80)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }
        return exerciseList;
    }
    static List<Exercise> day14() {
        Date date = new Date(15,0,2018);
        bodyWeightItems.add(new BodyWeightItem(date, 82, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("bench", date, sets(new int[]{5,5,5,5,4}, 105)));
        exerciseList.add(exercise("press", date, sets(new int[]{6,6,6}, 50)));
        exerciseList.add(exercise("chin-up", date, sets(new int[]{5,5,5}, 30)));
        exerciseList.add(exercise("triceps", date, sets(new int[]{10,10,10}, 25)));
        exerciseList.add(exercise("biceps", date, sets(new int[]{10,10,10}, 20)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }
        return exerciseList;
    }
    static List<Exercise> day15() {
        Date date = new Date(17,0,2018);
        bodyWeightItems.add(new BodyWeightItem(date, 81, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("squat", date, sets(new int[]{5,5}, 70)));
        exerciseList.add(exercise("deadlift", date, sets(new int[]{5,5,5,5}, 140)));
        exerciseList.add(exercise("lunges", date, sets(new int[]{6,6,6}, 40)));
        exerciseList.add(exercise("leg-curl", date, sets(new int[]{10,10,10}, 25)));
        exerciseList.add(exercise("twins", date, sets(new int[]{8,8,8}, 80)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }
        return exerciseList;
    }
    static List<Exercise> day16() {
        Date date = new Date(18,0,2018);
        bodyWeightItems.add(new BodyWeightItem(date, 95, ""));
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise("press", date, sets(new int[]{5,5,4,3,4}, 62)));
        exerciseList.add(exercise("bench", date, sets(new int[]{8,8,8}, 83)));
        exerciseList.add(exercise("chin-up", date, sets(new int[]{5,5,5}, 26)));
        exerciseList.add(exercise("triceps", date, sets(new int[]{10,10,10}, 25)));
        exerciseList.add(exercise("biceps", date, sets(new int[]{10,10,10}, 20)));

        workouts.put(date, exerciseList);
        for(Exercise e : exerciseList)
        {
            exercises.get(e.name).add(e);
        }
        return exerciseList;
    }

    public static Exercise exercise(String name, Date date,List<Set> sets)
    {
        return new Exercise(name, date,sets);
    }

    static Set set(int reps, double weight)
    {
        return new Set(reps, weight);
    }

    static List<Set> sets(int n, int reps, double weight)
    {
        List<Set> sets = new ArrayList<>();
        for(int i = 0; i < n ; i++)
        {
            sets.add(new Set(reps, weight));
        }
        return sets;
    }

    public static List<Set> sets(int[] reps, double weight)
    {
        List<Set> sets = new ArrayList<>();
        for(int i = 0; i < reps.length ; i++)
        {
            sets.add(new Set(reps[i], weight));
        }
        return sets;
    }
}
