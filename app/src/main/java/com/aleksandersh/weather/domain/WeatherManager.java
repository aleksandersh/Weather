package com.aleksandersh.weather.domain;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.content.LocalBroadcastManager;

import com.aleksandersh.weather.database.WeatherDao;
import com.aleksandersh.weather.model.city.City;
import com.aleksandersh.weather.model.weather.Weather;
import com.aleksandersh.weather.model.weather.WeatherRequest;
import com.aleksandersh.weather.model.weather.WeatherStorableState;
import com.aleksandersh.weather.network.httpClient.HttpClientResponse;
import com.aleksandersh.weather.network.httpClient.WeatherHttpClient;
import com.aleksandersh.weather.utils.ErrorsHelper;
import com.aleksandersh.weather.utils.PreferencesHelper;
import com.aleksandersh.weather.utils.WeatherUpdateBroadcastHelper;

import java.util.Date;

import javax.inject.Inject;

/**
 * Created by AleksanderSh on 20.07.2017.
 * <p>
 * Инкапсулирует логику обновления погоды и рассылает локальное оповещение.
 */

public class WeatherManager {
    private static final String TAG = "WeatherManager";

    private PreferencesHelper mPreferencesHelper;
    private WeatherHttpClient mHttpClient;
    private WeatherDao mWeatherDao;
    private Context mContext;

    @Inject
    public WeatherManager(Context context, PreferencesHelper preferencesHelper,
                          WeatherHttpClient httpClient, WeatherDao weatherDao) {
        mContext = context;

        mPreferencesHelper = preferencesHelper;
        mHttpClient = httpClient;
        mWeatherDao = weatherDao;
    }

    /**
     * Инициализация обновления погоды для города с заданным идентификатором.
     *
     * @param cityId Идентификатор города, для которого осуществляется обновление погоды.
     */
    public void updateWeather(long cityId) {
        WeatherRequest request = mPreferencesHelper.getWeatherRequest(cityId);
        mPreferencesHelper.saveCityId(cityId);
        updateWeatherByRequest(request);
    }

    /**
     * Инициализация обновления погоды по сохраненным параметрам.
     */
    public void updateWeather() {
        WeatherRequest request = mPreferencesHelper.getWeatherRequest();
        updateWeatherByRequest(request);
    }

    private void updateWeatherByRequest(WeatherRequest request) {
        if (!isNetworkAvailable()) {
            sendLocalBroadcast(WeatherUpdateBroadcastHelper
                    .getWeatherUpdateUnsuccessfulIntent(request.getCityId(),
                            ErrorsHelper.ERROR_INTERNET_DISCONNECTED));
            return;
        }

        HttpClientResponse<Weather> response = mHttpClient.getCurrentWeatherByCityId(
                request.getLang(),
                request.getUnits(),
                request.getCityId());

        if (response.isSuccessful()) {
            mWeatherDao.saveWeather(new WeatherStorableState(
                    response.getModel(),
                    request,
                    new Date()));

            sendLocalBroadcast(WeatherUpdateBroadcastHelper
                    .getWeatherUpdateSuccessfulIntent(request.getCityId()));
        } else {
            sendLocalBroadcast(WeatherUpdateBroadcastHelper
                    .getWeatherUpdateUnsuccessfulIntent(request.getCityId(),
                            response.getErrorCode()));
        }
    }

    public City getCity() {
        return mPreferencesHelper.getSelectedCity();
    }

    private void sendLocalBroadcast(Intent intent) {
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null
                && manager.getActiveNetworkInfo().isConnected();
    }
}
