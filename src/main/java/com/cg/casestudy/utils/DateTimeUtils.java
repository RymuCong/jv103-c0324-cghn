package com.cg.casestudy.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {
    public static String getTimeSinceCreation(Date createdAt) {
        long duration = new Date().getTime() - createdAt.getTime();
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60;

        if (days > 0) {
            return days + " days ago";
        } else if (hours > 0) {
            return hours + " hours ago";
        } else if (minutes > 0) {
            return minutes + " minutes ago";
        } else {
            return seconds + " seconds ago";
        }
    }
}
