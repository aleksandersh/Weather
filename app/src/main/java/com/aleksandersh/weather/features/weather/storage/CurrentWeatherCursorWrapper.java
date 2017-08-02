package com.aleksandersh.weather.features.weather.storage;


import android.database.Cursor;
import android.database.CursorWrapper;

import com.aleksandersh.weather.features.weather.data.storable.Weather;
import com.aleksandersh.weather.features.weather.data.storable.WeatherRequest;
import com.aleksandersh.weather.features.weather.data.storable.WeatherStorableState;
import com.aleksandersh.weather.features.weather.storage.WeatherDbSchema.CurrentWeatherTable.Cols;

import java.util.Date;


/**
 * Created by AleksanderSh on 20.07.2017.
 * <p>
 * Обертка для курсора текущей погоды над обычным.
 */

public class CurrentWeatherCursorWrapper extends CursorWrapper {

    public CurrentWeatherCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public WeatherStorableState getWeatherStorableState() {
        Weather weather = new Weather();
        weather.setCityId(getLong(getColumnIndex(Cols.CITY_ID)));
        weather.setTemperature(getDouble(getColumnIndex(Cols.TEMPERATURE)));
        weather.setPressure(getInt(getColumnIndex(Cols.PRESSURE)));
        weather.setHumidity(getInt(getColumnIndex(Cols.HUMIDITY)));
        weather.setCloudiness(getInt(getColumnIndex(Cols.CLOUDINESS)));
        weather.setWindSpeed(getDouble(getColumnIndex(Cols.WIND_SPEED)));
        weather.setWindDirection(getInt(getColumnIndex(Cols.WIND_DIRECTION)));
        weather.setDescription(getString(getColumnIndex(Cols.DESCRIPTION)));
        weather.setGroup(getString(getColumnIndex(Cols.GROUP)));

        WeatherRequest request = new WeatherRequest(
                getString(getColumnIndex(Cols.UNITS)),
                getString(getColumnIndex(Cols.LANGUAGE)),
                getLong(getColumnIndex(Cols.CITY_ID))
        );

        Date date = new Date(getInt(getColumnIndex(Cols.UPDATE_TIME)));

        return new WeatherStorableState(weather, request, date);
    }
}
