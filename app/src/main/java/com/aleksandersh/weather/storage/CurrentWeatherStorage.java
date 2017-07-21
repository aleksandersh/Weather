package com.aleksandersh.weather.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.aleksandersh.weather.model.Weather;
import com.aleksandersh.weather.model.WeatherRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

/**
 * Created by AleksanderSh on 16.07.2017.
 */

public class CurrentWeatherStorage implements WeatherStorage {
    private static final String PREFERENCES_NAME = "current_weather";
    private static final String PREF_DATA_KEY = "weather_data";
    private static final String PREF_CITY_ID_KEY = "city_id";
    private static final String PREF_UNITS_KEY = "units";
    private static final String PREF_LANG_KEY = "lang";
    private static final String PREF_DATE_SAVED_KEY = "date";

    private Context mContext;
    private Gson mGson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public CurrentWeatherStorage(Context context) {
        mContext = context;
    }

    /**
     * Сохранение модели.
     *
     * @param storableState Модель.
     */
    @Override
    public void saveState(WeatherStorableState storableState) {
        Weather weather = storableState.getWeather();
        WeatherRequest request = storableState.getRequest();
        Date date = storableState.getDate();

        String json = mGson.toJson(weather);
        SharedPreferences preferences =
                mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(PREF_DATA_KEY, json);
        editor.putLong(PREF_CITY_ID_KEY, request.getCityId());
        editor.putString(PREF_UNITS_KEY, request.getUnits());
        editor.putString(PREF_LANG_KEY, request.getLang());
        editor.putLong(PREF_DATE_SAVED_KEY, date.getTime());
        editor.apply();
    }

    /**
     * Получение сохраненной модели.
     *
     * @return Сохраненная модель. Или {@code null}, если сохраненных данных нет.
     */
    @Override
    public WeatherStorableState loadState() {
        SharedPreferences preferences =
                mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        Weather weather = null;
        String json = preferences.getString(PREF_DATA_KEY, "");
        if (!json.isEmpty()) {
            weather = mGson.fromJson(json, Weather.class);
        } else {
            return null;
        }

        long cityId = preferences.getLong(PREF_CITY_ID_KEY, 0);
        String units = preferences.getString(PREF_UNITS_KEY, "");
        String lang = preferences.getString(PREF_LANG_KEY, "");
        WeatherRequest request = new WeatherRequest(units, lang, cityId);

        Date date = new Date(preferences.getLong(PREF_DATE_SAVED_KEY, 0));

        return new WeatherStorableState(weather, request, date);
    }
}
