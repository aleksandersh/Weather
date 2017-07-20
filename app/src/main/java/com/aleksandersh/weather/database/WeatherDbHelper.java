package com.aleksandersh.weather.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AleksanderSh on 20.07.2017.
 */

public class WeatherDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "WeatherDbHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "weatherDatabase.db";

    private Context mContext;

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + WeatherDbSchema.CurrentWeatherTable.NAME + "(" +
                WeatherDbSchema.CurrentWeatherTable.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WeatherDbSchema.CurrentWeatherTable.Cols.CITY_ID + " INTEGER, " +
                WeatherDbSchema.CurrentWeatherTable.Cols.UPDATE_TIME + " INTEGER, " +
                WeatherDbSchema.CurrentWeatherTable.Cols.LANGUAGE + " TEXT, " +
                WeatherDbSchema.CurrentWeatherTable.Cols.UNITS + " TEXT, " +
                WeatherDbSchema.CurrentWeatherTable.Cols.TEMPERATURE + " REAL, " +
                WeatherDbSchema.CurrentWeatherTable.Cols.PRESSURE + " INTEGER, " +
                WeatherDbSchema.CurrentWeatherTable.Cols.HUMIDITY + " INTEGER, " +
                WeatherDbSchema.CurrentWeatherTable.Cols.CLOUDINESS + " INTEGER, " +
                WeatherDbSchema.CurrentWeatherTable.Cols.WIND_SPEED + " REAL, " +
                WeatherDbSchema.CurrentWeatherTable.Cols.WIND_DIRECTION + " INTEGER, " +
                WeatherDbSchema.CurrentWeatherTable.Cols.DESCRIPTION + " TEXT, " +
                WeatherDbSchema.CurrentWeatherTable.Cols.GROUP + " TEXT" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
