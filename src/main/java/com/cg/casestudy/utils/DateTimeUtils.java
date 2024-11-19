package com.cg.casestudy.utils;

import java.time.LocalDateTime;

public class DateTimeUtils {
    static public String getTimeDifference(LocalDateTime createdAt) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        long diffInNano = java.time.Duration.between(createdAt, currentDateTime).toNanos();
        long diffInSeconds = java.time.Duration.between(createdAt, currentDateTime).getSeconds();
        long diffInMinutes = java.time.Duration.between(createdAt, currentDateTime).toMinutes();
        long diffInHours = java.time.Duration.between(createdAt, currentDateTime).toHours();
        long diffInDays = java.time.Duration.between(createdAt, currentDateTime).toDays();
        long diffInMonths = java.time.Duration.between(createdAt, currentDateTime).toDays() / 30;
        long diffInYears = java.time.Duration.between(createdAt, currentDateTime).toDays() / 365;

        if (diffInNano < 0) {
            return "Just now";
        } else if (diffInSeconds < 60) {
            return diffInSeconds + " seconds ago";
        } else if (diffInMinutes < 60) {
            return diffInMinutes + " minutes ago";
        } else if (diffInHours < 24) {
            return diffInHours + " hours ago";
        } else if (diffInDays < 30) {
            return diffInDays + " days ago";
        } else if (diffInMonths < 12) {
            return diffInMonths + " months ago";
        } else {
            return diffInYears + " years ago";
        }
    }
}
