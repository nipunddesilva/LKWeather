package com.blogspot.nipunswritings.lkweather.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nipun on 7/2/2016.
 */
public class WeatherSQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "weather.db";
    private static final int DB_VERSION = 1;

    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_TIME = "time";
    public static final String COLUMN_NAME_DESC = "description";
    public static final String COLUMN_NAME_TEMP = "temp";
    public static final String COLUMN_NAME_HUMID = "humidity";
    public static final String COLUMN_NAME_PRESSURE = "pressure";
    public static final String COLUMN_NAME_WIND_SPEED = "wind_sped";
    public static final String COLUMN_NAME_WIND_DIREC = "wind_direc";

    public static final String TABLE_NAME_WEATHER_FORECAST = "weather_forecast";

    public final String CREATE_WEATHER__FORECAST_TABLE_SQL = "CREATE TABLE "+TABLE_NAME_WEATHER_FORECAST +"("
            +COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_NAME_TEMP+" REAL NOT NULL, "
            +COLUMN_NAME_DESC+" TEXT NOT NULL, "
            +COLUMN_NAME_HUMID+" REAL NOT NULL, "
            +COLUMN_NAME_PRESSURE+" REAL NOT NULL, "
            +COLUMN_NAME_WIND_SPEED+" REAL NOT NULL, "
            +COLUMN_NAME_WIND_DIREC+" REAL NOT NULL, "
            +COLUMN_NAME_TIME+" INTEGER NOT NULL);";


    public WeatherSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WEATHER__FORECAST_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
