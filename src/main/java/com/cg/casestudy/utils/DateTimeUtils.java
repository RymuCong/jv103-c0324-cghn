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

        if (diffInNano > 0 && diffInSeconds <= 0) {
            return "Vừa xong";
        } else if (diffInSeconds < 60) {
            return diffInSeconds + " giây trước";
        } else if (diffInMinutes < 60) {
            return diffInMinutes + " phút trước";
        } else if (diffInHours < 24) {
            return diffInHours + " giờ trước";
        } else if (diffInDays < 30) {
            return diffInDays + " ngày trước";
        } else if (diffInMonths < 12) {
            return diffInMonths + " tháng trước";
        } else {
            return diffInYears + " năm trước";
        }
    }
}