package com.example.agodlin.strengthlog.common;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by agodlin on 1/5/2018.
 */

public class Date implements Comparable,Serializable {
    public int day;
    public int month;
    public int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Date date = (Date) o;

        if (day != date.day) return false;
        if (month != date.month) return false;
        return year == date.year;
    }

    @Override
    public int hashCode() {
        int result = day;
        result = 31 * result + month;
        result = 31 * result + year;
        return result;
    }

    @Override

    public String toString() {
        return day +
                "-" + (month+1) +
                "-" + year;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        if (this == o) return 0;
        if (o == null || getClass() != o.getClass()) return -1;

        Date date = (Date) o;
        if (this.year - date.year != 0)
            return date.year - this.year;
        if (this.month - date.month != 0)
            return date.month - this.month;
        if (this.day - date.day != 0)
            return date.day - this.day;
        return 0;
    }
}
