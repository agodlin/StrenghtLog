package com.example.agodlin.strengthlog.common;

import java.util.List;

/**
 * Created by agodlin on 1/13/2018.
 */

public class Exercise {
    public String name;
    public Date date;
    public List<Set> sets;

    public Exercise(String name, Date date, List<Set> sets) {
        this.name = name;
        this.date = date;
        this.sets = sets;
    }
}
