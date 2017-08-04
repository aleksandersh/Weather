package com.aleksandersh.weather.storage;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.preference.PreferenceManager;

import com.aleksandersh.weather.R;
import com.aleksandersh.weather.features.city.data.model.storable.City;
import com.aleksandersh.weather.features.city.data.model.transferable.CityDto;
import com.aleksandersh.weather.features.weather.data.model.storable.WeatherRequest;

import java.util.function.Consumer;


/**
 * Created by AleksanderSh on 19.07.2017.
 * <p>
 * Облегчает работу с настройками.
 */

public class PreferencesHelper {

    private Context context;

    private SharedPreferences defaultPreferences;

    private String serviceEnabledKey;

    private String serviceIntervalKey;

    private String serviceIntervalDefault;

    private String currentCityId;

    private String currentCityName;

    private String currentCityCountryName;

    private String currentCityLng;

    private String currentCityLat;

    public PreferencesHelper(Context context) {
        this.context = context;
        defaultPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        serviceEnabledKey = context.getString(R.string.pref_service_enabled_key);
        serviceIntervalKey = context.getString(R.string.pref_service_interval_key);
        serviceIntervalDefault = context.getString(R.string.pref_service_interval_default);

        currentCityId = context.getString(R.string.pref_current_city_id);
        currentCityName = context.getString(R.string.pref_current_city_name);
        currentCityCountryName = context.getString(R.string.pref_current_city_country_name);
        currentCityLng = context.getString(R.string.pref_current_city_lng);
        currentCityLat = context.getString(R.string.pref_current_city_lat);
    }

    public boolean isServiceEnabled() {
        return defaultPreferences.getBoolean(serviceEnabledKey, false);
    }

    /**
     * Интервал, указанный в настройках (в минутах).
     *
     * @return Интервал.
     */
    public int getServiceInterval() {
        return Integer.parseInt(defaultPreferences.getString(serviceIntervalKey,
                serviceIntervalDefault));
    }

    /**
     * Получение последнего сохраненного запроса.
     *
     * @return Сформированный запрос.
     */
    public WeatherRequest getWeatherRequest() {
        return getWeatherRequest(getCityId());
    }

    /**
     * Получение последнего сохраненного запроса для нового города.
     *
     * @param cityId Город, для которого необходимо получить запрос.
     * @return Сформированный запрос.
     */
    public WeatherRequest getWeatherRequest(long cityId) {
        return new WeatherRequest(getUnits(), getLanguage(), cityId);
    }

    private String getLanguage() {
        return context.getString(R.string.api_language_value);
    }

    private String getUnits() {
        String unitsKey = context.getString(R.string.pref_units_key);
        String unitsDefault = context.getString(R.string.pref_units_default);
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(unitsKey, unitsDefault);
    }

    private long getCityId() {
        String cityIdKey = context.getString(R.string.pref_last_city_id_key);
        long cityIdDefault = Long.parseLong(context.getString(R.string.pref_last_city_id_default));
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getLong(cityIdKey, cityIdDefault);
    }

    /**
     * Сохраняет идентификатор города как последний использованный.
     *
     * @param cityId Идентификатор города.
     */
    public void saveCityId(long cityId) {
        String cityIdKey = context.getString(R.string.pref_last_city_id_key);
        put(editor -> editor.putLong(cityIdKey, cityId));
    }

    public int getId() {
        return defaultPreferences.getInt(currentCityId, 0);
    }

    public double getLng() {
        return Double.valueOf(defaultPreferences.getString(currentCityLng, "0"));
    }

    public double getLat() {
        return Double.parseDouble(defaultPreferences.getString(currentCityLat, "0"));
    }

    public void saveCity(CityDto city) {
        float lng = Float.parseFloat(city.getLng());
        float lat = Float.parseFloat(city.getLat());
        put(editor -> {
            editor.putInt(currentCityId, city.getCityId());
            editor.putString(currentCityName, city.getName());
            editor.putString(currentCityCountryName, city.getCountryName());
            editor.putFloat(currentCityLng, lng);
            editor.putFloat(currentCityLat, lat);
        });
    }

    public void saveCity(City city) {
        put(editor -> {
            editor.putInt(currentCityId, city.getId());
            editor.putString(currentCityName, city.getName());
            editor.putString(currentCityCountryName, city.getCountryName());
            editor.putFloat(currentCityLng, (float) city.getLng());
            editor.putFloat(currentCityLat, ((float) city.getLat()));
        });
    }

    public City getSelectedCity() {
        if (!defaultPreferences.contains(currentCityId)) {
            City defaultCity = new City(2643743, context.getString(R.string.default_city), context.getString(R.string.default_country), -0.12574, 51.50853);
            saveCity(defaultCity);
            return defaultCity;
        }
        int id = defaultPreferences.getInt(currentCityId, 0);
        String name = defaultPreferences.getString(currentCityName, "");
        String countryName = defaultPreferences.getString(currentCityCountryName, "");
        double lng = defaultPreferences.getFloat(currentCityLng, 0);
        double lat = defaultPreferences.getFloat(currentCityLat, 0);
        return new City(id, name, countryName, lng, lat);
    }

    private void put(Consumer<Editor> f) {
        Editor editor = defaultPreferences.edit();
        f.accept(editor);
        editor.apply();
    }

}
