package com.example.agodlin.strengthlog.utils;

/**
 * Created by agodlin on 3/24/2018.
 */

public class DateHelper {

    public static long roundTimeToDay(long timeMillis)
    {
        return (timeMillis/(1000*60*60*24));
    }

    public static long convertDaysToMilis(long days)
    {

        return (days*(1000*60*60*24));
    }
}
