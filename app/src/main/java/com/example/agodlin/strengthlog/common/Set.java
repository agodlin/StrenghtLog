package com.example.agodlin.strengthlog.common;

/**
 * Created by agodlin on 1/13/2018.
 */

public class Set {
    public int reps;
    public double weight;

    public Set(int reps, double weight) {
        this.reps = reps;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "reps=" + reps +
                ", weight=" + weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Set set = (Set) o;

        if (reps != set.reps) return false;
        return Double.compare(set.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = reps;
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}
