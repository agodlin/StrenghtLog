package com.example.agodlin.strengthlog.common;

import java.util.List;

/**
 * Created by agodlin on 1/13/2018.
 */

public class Exercise {
    private final long _id;
    public final String name;
    public final Date date;
    public final List<Set> sets;

    public Exercise(long id, String name, Date date, List<Set> sets) {
        this._id = id;
        this.name = name;
        this.date = date;
        this.sets = sets;
    }

    public Exercise(long id, Exercise rhs)
    {
        this._id = id;
        this.name = rhs.name;
        this.date = rhs.date;
        this.sets = rhs.sets;
    }
}
