package com.aleksandersh.weather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.preference.PreferenceManager;

import com.aleksandersh.weather.R;
import com.aleksandersh.weather.model.city.City;
import com.aleksandersh.weather.model.weather.WeatherRequest;
import com.aleksandersh.weather.network.dto.city.CityDto;

import java.util.function.Consumer;

/**
 * Created by AleksanderSh on 19.07.2017.
 * <p>
 * Облегчает работу с настройками.
 */

public class PreferencesHelper {

    private Context mContext;
    private SharedPreferences mDefaultPreferences;

    private String mServiceEnabledKey;
    private String mServiceIntervalKey;
    private String mServiceIntervalDefault;

    private String mCurrentCityId;
    private String mCurrentCityName;
    private String mCurrentCityCountryName;
    private String mCurrentCityLng;
    private String mCurrentCityLat;

    public PreferencesHelper(Context context) {
        mContext = context;
        mDefaultPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        mServiceEnabledKey = context.getString(R.string.pref_service_enabled_key);
        mServiceIntervalKey = context.getString(R.string.pref_service_interval_key);
        mServiceIntervalDefault = context.getString(R.string.pref_service_interval_default);

        mCurrentCityId = context.getString(R.string.pref_current_city_id);
        mCurrentCityName = context.getString(R.string.pref_current_city_name);
        mCurrentCityCountryName = context.getString(R.string.pref_current_city_country_name);
        mCurrentCityLng = context.getString(R.string.pref_current_city_lng);
        mCurrentCityLat = context.getString(R.string.pref_current_city_lat);
    }

    public boolean isServiceEnabled() {
        return mDefaultPreferences.getBoolean(mServiceEnabledKey, false);
    }

    /**
     * Интервал, указанный в настройках (в минутах).
     *
     * @return Интервал.
     */
    public int getServiceInterval() {
        return Integer.parseInt(mDefaultPreferences.getString(mServiceIntervalKey,
                mServiceIntervalDefault));
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
        return mContext.getString(R.string.api_language_value);
    }

    private String getUnits() {
        String unitsKey = mContext.getString(R.string.pref_units_key);
        String unitsDefault = mContext.getString(R.string.pref_units_default);
        return PreferenceManager.getDefaultSharedPreferences(mContext)
                .getString(unitsKey, unitsDefault);
    }

    private long getCityId() {
        String cityIdKey = mContext.getString(R.string.pref_last_city_id_key);
        long cityIdDefault = Long.parseLong(mContext.getString(R.string.pref_last_city_id_default));
        return PreferenceManager.getDefaultSharedPreferences(mContext)
                .getLong(cityIdKey, cityIdDefault);
    }

    /**
     * Сохраняет идентификатор города как последний использованный.
     *
     * @param cityId Идентификатор города.
     */
    public void saveCityId(long cityId) {
        String cityIdKey = mContext.getString(R.string.pref_last_city_id_key);
        put(editor -> editor.putLong(cityIdKey, cityId));
    }

    public int getId() {
        return mDefaultPreferences.getInt(mCurrentCityId, 0);
    }

    public double getLng() {
        return Double.valueOf(mDefaultPreferences.getString(mCurrentCityLng, "0"));
    }

    public double getLat() {
        return Double.parseDouble(mDefaultPreferences.getString(mCurrentCityLat, "0"));
    }

    public void saveCity(CityDto city) {
        float lng = Float.parseFloat(city.getLng());
        float lat = Float.parseFloat(city.getLat());
        put(editor -> {
            editor.putInt(mCurrentCityId, city.getCityId());
            editor.putString(mCurrentCityName, city.getName());
            editor.putString(mCurrentCityCountryName, city.getCountryName());
            editor.putFloat(mCurrentCityLng, lng);
            editor.putFloat(mCurrentCityLat, lat);
        });
    }

    public void saveCity(City city) {
        put(editor -> {
            editor.putInt(mCurrentCityId, city.getId());
            editor.putString(mCurrentCityName, city.getName());
            editor.putString(mCurrentCityCountryName, city.getCountryName());
            editor.putFloat(mCurrentCityLng, (float) city.getLng());
            editor.putFloat(mCurrentCityLat, ((float) city.getLat()));
        });
    }

    public City getSelectedCity() {
        if (!mDefaultPreferences.contains(mCurrentCityId)) {
            City defaultCity = new City(2643743, mContext.getString(R.string.default_city), mContext.getString(R.string.default_country), -0.12574, 51.50853);
            saveCity(defaultCity);
            return defaultCity;
        }
        int id = mDefaultPreferences.getInt(mCurrentCityId, 0);
        String name = mDefaultPreferences.getString(mCurrentCityName, "");
        String countryName = mDefaultPreferences.getString(mCurrentCityCountryName, "");
        double lng = mDefaultPreferences.getFloat(mCurrentCityLng, 0);
        double lat = mDefaultPreferences.getFloat(mCurrentCityLat, 0);
        return new City(id, name, countryName, lng, lat);
    }

    private void put(Consumer<Editor> f) {
        Editor editor = mDefaultPreferences.edit();
        f.accept(editor);
        editor.apply();
    }

}
