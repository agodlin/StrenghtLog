package com.example.agodlin.strengthlog.common;

import java.util.List;

/**
 * Created by agodlin on 1/13/2018.
 */

public class Exercise {
    public final String name;
    public final Date date;
    public final List<Set> sets;
    public String comment;

    public Exercise(String name, Date date, List<Set> sets) {
        this(name, date, sets, "");
    }

    public Exercise(String name, Date date, List<Set> sets, String comment) {
        this.name = name;
        this.date = date;
        this.sets = sets;
        this.comment = comment;
    }

    public Exercise(Exercise rhs)
    {
        this.name = rhs.name;
        this.date = rhs.date;
        this.sets = rhs.sets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        if (name != null ? !name.equals(exercise.name) : exercise.name != null) return false;
        if (date != null ? !date.equals(exercise.date) : exercise.date != null) return false;
        return sets != null ? sets.equals(exercise.sets) : exercise.sets == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (sets != null ? sets.hashCode() : 0);
        return result;
    }
}
