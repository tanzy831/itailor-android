package com.thea.itailor.util;

import java.util.TimeZone;

/**
 * Created by Thea on 2015/8/8.
 */
public class TimeUtil {
    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    public static boolean isSameDay(long time1, long time2) {
        final long interval = time1 - time2;
        return interval < MILLIS_IN_DAY && interval > -1L * MILLIS_IN_DAY
            && getDay(time1) == getDay(time2);
    }

    private static long getDay(long time) {
        return (time + TimeZone.getDefault().getOffset(time)) / MILLIS_IN_DAY;
    }
}
