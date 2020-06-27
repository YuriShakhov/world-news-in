package com.example.worldnewsin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateManager {
    public static String formatDate (String date) throws ParseException {
        SimpleDateFormat youtubeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat myDayFormat = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat myHourFormat = new SimpleDateFormat("HH:mm");
        Date videoDate = youtubeFormat.parse(date);
        Date currentDate = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);
        Date today = todayCalendar.getTime();
        Date yesterday = new Date();
        yesterday.setTime(today.getTime() - (long)1000*60*60*24);
        String dayString = "";
        if (!videoDate.before(today)) {
            dayString = "Today";
        } else if (!videoDate.before(yesterday)) {
            dayString = "Yesterday";
        } else {
            dayString = myDayFormat.format(videoDate);
        }
        String hourString = myHourFormat.format(videoDate);

        return dayString + ", " + hourString;
    }
}
