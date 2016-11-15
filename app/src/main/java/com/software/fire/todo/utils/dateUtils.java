package com.software.fire.todo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Brad on 11/11/2016.
 */

public class dateUtils {
    public static String getReadableDate(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        Date resultDate = new Date(Long.parseLong(time));
        return sdf.format(resultDate);
    }

    public static String getReadableTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm");
        Date resultDate = new Date(Long.parseLong(time));
        return sdf.format(resultDate);
    }

    public static String getTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date resultDate = new Date(Long.parseLong(time));
        return sdf.format(resultDate);
    }
}
