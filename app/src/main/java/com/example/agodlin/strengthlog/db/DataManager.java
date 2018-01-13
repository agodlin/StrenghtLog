package com.example.agodlin.strengthlog.db;

import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.common.Exercise;
import com.example.agodlin.strengthlog.common.Set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by agodlin on 7/21/2017.
 */

public class DataManager {
    public static final Map<String, List<Exercise>> ITEM_MAP = new HashMap<String, List<Exercise>>();

    static {
        addItem("squat");
        addItem("bench");
        addItem("deadlift");
    }

    static void addItem(String name)
    {
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(new Exercise(name, new Date(1,1,2018), Arrays.asList(new Set(5, 80), new Set(5, 90))));
        exerciseList.add(new Exercise(name, new Date(3,1,2018), Arrays.asList(new Set(5, 85), new Set(5, 95))));
        exerciseList.add(new Exercise(name, new Date(5,1,2018), Arrays.asList(new Set(5, 90), new Set(5, 100))));

        ITEM_MAP.put(name, exerciseList);
    }
}
