package com.blogspot.nipunswritings.lkweather.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nipun on 7/2/2016.
 */
public class WeatherDataManager {
    protected WeatherSQLiteHelper mWeatherSQLiteHelper;
    protected SQLiteDatabase mDatabase;

    protected WeatherDataManager(Context context) {
        mWeatherSQLiteHelper = new WeatherSQLiteHelper(context);
    }

    public void createReadableDb () {
        mDatabase = mWeatherSQLiteHelper.getReadableDatabase();
    }

    public void createWritableDb (){
        mDatabase = mWeatherSQLiteHelper.getWritableDatabase();
    }

    public void closeDb () {
        if (mDatabase != null)
            mDatabase.close();
    }
}
