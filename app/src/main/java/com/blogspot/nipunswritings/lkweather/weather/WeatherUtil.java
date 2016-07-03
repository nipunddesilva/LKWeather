package com.blogspot.nipunswritings.lkweather.weather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Nipun on 7/1/2016.
 */
public class WeatherUtil {

    public static float kelvinToCelsiusTemp (float kelvinTemp) {
        return kelvinTemp - 273.15f;
    }

    public static String getCompassForDegree(int degree) {
        String []compassValues = {"N","NNE","NE","ENE","E","ESE", "SE", "SSE","S","SSW","SW","WSW","W","WNW","NW","NNW"};
        return compassValues[degree%16];
    }

    public static float mdevsTokdevh (float mdevs) {
        return mdevs*3.6f;
    }

    public static long convertTimeToLong (String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(time));
        } catch (ParseException e) {
            calendar.setTimeInMillis(0);
        }

        return calendar.getTimeInMillis();
    }

    public static String getTimeFromLong (long time) {

        String timeText = "";

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        timeText = calendar.get(Calendar.YEAR) +" - "
                +String.format("%02d", calendar.get(Calendar.MONTH)+1)+" - "
                +String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
                +" "+String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY))
                +" : "+String.format("%02d", calendar.get(Calendar.MINUTE));


        return timeText;
    }

}
