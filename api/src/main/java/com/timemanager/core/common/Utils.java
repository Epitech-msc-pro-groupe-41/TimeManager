package com.timemanager.core.common;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Utils {

    public static String dateLongToString(long dateTime) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(dateTime);
        return formatter.format(date);
    }

    public static long stringToLongDate(String date) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long dateLong = 0;
        try {
            dateLong = formatter.parse(date).getTime();
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Invalid date value");  
        }
        return dateLong;
    }
}
