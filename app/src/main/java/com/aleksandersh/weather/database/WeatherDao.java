package com.aleksandersh.weather.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.aleksandersh.weather.database.cursorWrapper.CurrentWeatherCursorWrapper;
import com.aleksandersh.weather.model.Weather;
import com.aleksandersh.weather.model.WeatherRequest;
import com.aleksandersh.weather.storage.WeatherStorableState;

/**
 * Created by AleksanderSh on 20.07.2017.
 * <p>
 * Класс для совершения операций над базой данных.
 */

public class WeatherDao {
    private static final String TAG = "WeatherDao";

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public WeatherDao(Context context) {
        mContext = context;
        mDatabase = new WeatherDbHelper(context).getWritableDatabase();
    }

    /**
     * Сохраняет объект в базу данных. Так как погода в городе может иметь только одно актуальное
     * состояние, перед сохранением из базы удаляются все предыдущие записи по городу.
     *
     * @param storableState Сохраняемое состояние.
     */
    public void saveWeather(WeatherStorableState storableState) {
        mDatabase.beginTransaction();
        try {
            String selection = WeatherDbSchema.CurrentWeatherTable.Cols.CITY_ID + " = ?";
            String[] selectionArgs = new String[]{
                    String.valueOf(storableState.getWeather().getCityId())};
            mDatabase.delete(WeatherDbSchema.CurrentWeatherTable.NAME, selection, selectionArgs);

            ContentValues values = getWeatherStorableStateContentValues(storableState);
            mDatabase.insert(WeatherDbSchema.CurrentWeatherTable.NAME, null, values);

            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    /**
     * Получает сохраненные данные по погоде в указанном городе.
     *
     * @param cityId Идентификатор города.
     * @return Сохраненные данные, если данные отсутствуют, возвращается {@code null}.
     */
    @Nullable
    public WeatherStorableState getWeatherByCityId(long cityId) {
        WeatherStorableState result = null;

        String selection = WeatherDbSchema.CurrentWeatherTable.Cols.CITY_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(cityId)};
        CurrentWeatherCursorWrapper cursor = getCurrentWeatherCursor(selection, selectionArgs);
        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                result = cursor.getWeatherStorableState();
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    private CurrentWeatherCursorWrapper getCurrentWeatherCursor(String selection,
                                                                String[] selectionArgs) {
        return new CurrentWeatherCursorWrapper(
                mDatabase.query(WeatherDbSchema.CurrentWeatherTable.NAME,
                        null,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                ));
    }

    private ContentValues getWeatherStorableStateContentValues(WeatherStorableState storableState) {
        ContentValues values = new ContentValues();

        values.put(WeatherDbSchema.CurrentWeatherTable.Cols.UPDATE_TIME,
                storableState.getDate().getTime());

        Weather weather = storableState.getWeather();
        values.put(WeatherDbSchema.CurrentWeatherTable.Cols.CITY_ID, weather.getCityId());
        values.put(WeatherDbSchema.CurrentWeatherTable.Cols.TEMPERATURE, weather.getTemperature());
        values.put(WeatherDbSchema.CurrentWeatherTable.Cols.PRESSURE, weather.getPressure());
        values.put(WeatherDbSchema.CurrentWeatherTable.Cols.HUMIDITY, weather.getHumidity());
        values.put(WeatherDbSchema.CurrentWeatherTable.Cols.CLOUDINESS, weather.getCloudiness());
        values.put(WeatherDbSchema.CurrentWeatherTable.Cols.WIND_SPEED, weather.getWindSpeed());
        values.put(WeatherDbSchema.CurrentWeatherTable.Cols.WIND_DIRECTION, weather.getWindDirection());
        values.put(WeatherDbSchema.CurrentWeatherTable.Cols.DESCRIPTION, weather.getDescription());
        values.put(WeatherDbSchema.CurrentWeatherTable.Cols.GROUP, weather.getGroup());

        WeatherRequest request = storableState.getRequest();
        values.put(WeatherDbSchema.CurrentWeatherTable.Cols.LANGUAGE, request.getLang());
        values.put(WeatherDbSchema.CurrentWeatherTable.Cols.UNITS, request.getUnits());

        return values;
    }
}
