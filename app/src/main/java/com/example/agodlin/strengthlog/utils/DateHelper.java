package com.example.agodlin.strengthlog.utils;

import java.util.Calendar;

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

    public static long subtractMonths(long timeMilis, int n)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMilis);
        calendar.add(Calendar.MONTH, n*-1);
        return calendar.getTimeInMillis();
    }
}
