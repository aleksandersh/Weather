package com.aleksandersh.weather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.preference.PreferenceManager;

import com.aleksandersh.weather.R;
import com.aleksandersh.weather.model.weather.WeatherRequest;

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

    public PreferencesHelper(Context context) {
        mContext = context;
        mDefaultPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        mServiceEnabledKey = context.getString(R.string.pref_service_enabled_key);
        mServiceIntervalKey = context.getString(R.string.pref_service_interval_key);
        mServiceIntervalDefault = context.getString(R.string.pref_service_interval_default);
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
        return new WeatherRequest(getUnits(), getLanguage(), getCityId());
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
        Editor editor = mDefaultPreferences.edit();
        editor.putLong(cityIdKey, cityId);
        editor.apply();
    }
}
