package com.example.demo.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeFormatter {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
    private static final Calendar calendar = Calendar.getInstance();

    public static String format(String stringDate) {
        Date date;
        try {
            date = dateFormat.parse(stringDate.substring(0, 19));
        } catch (ParseException e) {
            return "";
        }
        calendar.setTime(date);
        return calendar.getTime().toString();
    }
}
