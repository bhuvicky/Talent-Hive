package com.bhuvanesh.talenthive.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by bhuvanesh on 16-01-2017.
 */

public final class DateUtil {

    private DateUtil () {}

    public static final String DATE_TIME_FORMAT_TYPE_dd_MM_yyyy_HH_MM = "dd/MM/yyyy HH:MM";

    private static final int ONE_SEC_IN_MILLIS = 1000;
    private static final int ONE_MIN_IN_MILLIS = 60 * ONE_SEC_IN_MILLIS;
    private static final int ONE_HOUR_IN_MILLIS = 60 * ONE_MIN_IN_MILLIS;
    private static final int ONE_DAY_IN_MILLIS = 24 * ONE_HOUR_IN_MILLIS;

    public static String getTimeAgo(long time) {
        return getTimeAgo(time, new Date().getTime());
    }

    private static String getTimeAgo(long time, long now) {

        if (time > now || time < 0)
            return null;

        long diff = now - time;
        if (diff < ONE_MIN_IN_MILLIS)
            return "Just Now";
        else if (diff < 59 * ONE_MIN_IN_MILLIS)
            return (diff / ONE_MIN_IN_MILLIS) + " mins";
        else if (diff < 61 * ONE_MIN_IN_MILLIS)
            return (diff / ONE_MIN_IN_MILLIS) + " hr";
        else if (diff < 24 * ONE_HOUR_IN_MILLIS)
            return (diff / ONE_HOUR_IN_MILLIS) + " hrs";
        else if (diff < 3 * ONE_DAY_IN_MILLIS)
            return "Yesterday";
        else
            return getFormattedString(diff, DATE_TIME_FORMAT_TYPE_dd_MM_yyyy_HH_MM);
    }

    public static String getFormattedString(long timeStamp, String type) {
        DateFormat df = new SimpleDateFormat(type, Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStamp);
        return df.format(cal.getTime());
    }
}
