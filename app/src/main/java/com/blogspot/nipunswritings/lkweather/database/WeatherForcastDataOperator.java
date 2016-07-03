package com.blogspot.nipunswritings.lkweather.database;

import com.blogspot.nipunswritings.lkweather.weather.Weather;

import java.util.List;

/**
 * Created by Nipun on 7/2/2016.
 */
public interface WeatherForcastDataOperator {
    public Weather insertNewForcastData (float temp,
                                      String desc,
                                      float humid,
                                      float press, float windSpeed, float windDirec, long time);
    public void emptyTable();
    public List<Weather> getAllForcastData();

}
