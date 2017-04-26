package com.weather.app.com.weather.app.util;


import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mahmoud.Fathy on 4/24/2017.
 */
public class DateUtils {
    /**
     * This method is used to retrieve end of today.
     * @return Date.
     */
    public static Date getEndOfToday() {
        Calendar today = setToStart(Calendar.getInstance());
        today.add(Calendar.HOUR, 23);
        today.add(Calendar.MINUTE, 59);
        today.add(Calendar.SECOND, 59);

        return today.getTime();
    }
    /**
     * This method is used to retrieve start of today.
     * @return Date.
     */
    public static Date getStartOfToday() {
        Calendar today = setToStart(Calendar.getInstance());

        return today.getTime();
    }

    private static Calendar setToStart(Calendar calendar) {
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Date now() {
        return Calendar.getInstance().getTime();
    }
}
