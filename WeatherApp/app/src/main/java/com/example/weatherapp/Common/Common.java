package com.example.weatherapp.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static final String APP_ID = "95515631c1a7a33c4f13ee665428b1ae";
    public static Location current_location= null;

    public static String convertUnixToDate (long dt) {
        Date date =  new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd EEE MM yyyy");
        String formatted = sdf.format(date);
        return formatted;
    }

    public static String convertUnixToHour(long dt) {
        Date date =  new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm EEE MM yyyy");
        String formatted = sdf.format(date);
        return formatted;
    }
}
