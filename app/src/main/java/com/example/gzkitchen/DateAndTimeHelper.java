package com.example.gzkitchen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndTimeHelper {
    public String ConvertToString(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
    }

    public Date ConvertToDate(String dateString) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
