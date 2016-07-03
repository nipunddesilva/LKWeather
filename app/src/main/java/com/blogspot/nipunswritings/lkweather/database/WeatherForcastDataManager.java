package com.blogspot.nipunswritings.lkweather.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.blogspot.nipunswritings.lkweather.weather.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nipun on 7/2/2016.
 */
public class WeatherForcastDataManager extends WeatherDataManager
        implements WeatherForcastDataOperator {


    public WeatherForcastDataManager(Context context) {
        super(context);
    }

    @Override
    public Weather insertNewForcastData(float temp, String desc, float humid, float press,
                                        float windSpeed, float windDirec, long time) {

        Weather weather = null;


        ContentValues values = new ContentValues();
        values.put(WeatherSQLiteHelper.COLUMN_NAME_TEMP, temp);
        values.put(WeatherSQLiteHelper.COLUMN_NAME_DESC, desc);
        values.put(WeatherSQLiteHelper.COLUMN_NAME_HUMID, humid);
        values.put(WeatherSQLiteHelper.COLUMN_NAME_PRESSURE, press);
        values.put(WeatherSQLiteHelper.COLUMN_NAME_WIND_SPEED, windSpeed);
        values.put(WeatherSQLiteHelper.COLUMN_NAME_WIND_DIREC, windDirec);
        values.put(WeatherSQLiteHelper.COLUMN_NAME_TIME, time);

        if (mDatabase.insert(WeatherSQLiteHelper.TABLE_NAME_WEATHER_FORECAST, null, values) > -1){
            weather = new Weather(temp, desc, humid, press, windSpeed, windDirec, time);
        }

        return weather;
    }

    @Override
    public void emptyTable() {

        mDatabase.execSQL("DELETE FROM "+WeatherSQLiteHelper.TABLE_NAME_WEATHER_FORECAST);
        mDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                + WeatherSQLiteHelper.TABLE_NAME_WEATHER_FORECAST + "'");


    }

    @Override
    public List<Weather> getAllForcastData() {
        ArrayList<Weather> forcastsList = null;

        Cursor cursor = mDatabase.query(WeatherSQLiteHelper.TABLE_NAME_WEATHER_FORECAST,
                new String[]{
                        WeatherSQLiteHelper.COLUMN_NAME_TIME,
                        WeatherSQLiteHelper.COLUMN_NAME_TIME,
                        WeatherSQLiteHelper.COLUMN_NAME_DESC,
                        WeatherSQLiteHelper.COLUMN_NAME_TEMP,
                        WeatherSQLiteHelper.COLUMN_NAME_HUMID,
                        WeatherSQLiteHelper.COLUMN_NAME_PRESSURE,
                        WeatherSQLiteHelper.COLUMN_NAME_WIND_SPEED,
                        WeatherSQLiteHelper.COLUMN_NAME_WIND_DIREC
                }, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            forcastsList = new ArrayList<>();
            do {
                forcastsList.add(new Weather(cursor.getFloat(cursor.getColumnIndex(WeatherSQLiteHelper.COLUMN_NAME_TEMP)),
                        cursor.getString(cursor.getColumnIndex(WeatherSQLiteHelper.COLUMN_NAME_DESC)),
                        cursor.getFloat(cursor.getColumnIndex(WeatherSQLiteHelper.COLUMN_NAME_HUMID)),
                        cursor.getFloat(cursor.getColumnIndex(WeatherSQLiteHelper.COLUMN_NAME_PRESSURE)),
                        cursor.getFloat(cursor.getColumnIndex(WeatherSQLiteHelper.COLUMN_NAME_WIND_SPEED)),
                        cursor.getFloat(cursor.getColumnIndex(WeatherSQLiteHelper.COLUMN_NAME_WIND_DIREC)),
                        cursor.getLong(cursor.getColumnIndex(WeatherSQLiteHelper.COLUMN_NAME_TIME))
                        ));
            } while (cursor.moveToNext());
        }

        return forcastsList;
    }




}
