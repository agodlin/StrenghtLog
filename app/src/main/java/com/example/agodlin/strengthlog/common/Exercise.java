package com.example.agodlin.strengthlog.common;

import java.util.List;

/**
 * Created by agodlin on 1/13/2018.
 */

public class Exercise {
    public final long _id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        if (_id != exercise._id) return false;
        if (name != null ? !name.equals(exercise.name) : exercise.name != null) return false;
        if (date != null ? !date.equals(exercise.date) : exercise.date != null) return false;
        return sets != null ? sets.equals(exercise.sets) : exercise.sets == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (_id ^ (_id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (sets != null ? sets.hashCode() : 0);
        return result;
    }
}
