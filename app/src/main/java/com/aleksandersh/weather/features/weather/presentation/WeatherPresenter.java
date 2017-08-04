package com.aleksandersh.weather.features.weather.presentation;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.aleksandersh.weather.features.city.data.model.storable.City;
import com.aleksandersh.weather.features.weather.data.model.storable.Weather;
import com.aleksandersh.weather.features.weather.data.model.storable.WeatherRequest;
import com.aleksandersh.weather.features.weather.data.model.storable.WeatherStorableState;
import com.aleksandersh.weather.features.weather.domain.repository.CurrentWeatherRepository;
import com.aleksandersh.weather.features.weather.framework.WeatherUpdateBroadcastHelper;
import com.aleksandersh.weather.features.weather.storage.WeatherDao;
import com.aleksandersh.weather.network.ErrorMapper;
import com.aleksandersh.weather.network.HttpClientResponse;
import com.aleksandersh.weather.network.NetworkUtils;
import com.aleksandersh.weather.storage.PreferencesHelper;

import java.util.Date;

import javax.inject.Inject;


/**
 * Created by AleksanderSh on 20.07.2017.
 * <p>
 * Инкапсулирует логику обновления погоды и рассылает локальное оповещение.
 */

public class WeatherPresenter {

    private static final String TAG = "WeatherManager";

    private PreferencesHelper mPreferencesHelper;

    private CurrentWeatherRepository mHttpClient;

    private WeatherDao mWeatherDao;

    private Context mContext;

    @Inject
    public WeatherPresenter(Context context, PreferencesHelper preferencesHelper,
                            CurrentWeatherRepository httpClient, WeatherDao weatherDao) {
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
        if (!NetworkUtils.isNetworkAvailable(mContext)) {
            sendLocalBroadcast(WeatherUpdateBroadcastHelper
                    .getWeatherUpdateUnsuccessfulIntent(request.getCityId(),
                            ErrorMapper.ERROR_INTERNET_DISCONNECTED));
            return;
        }

        HttpClientResponse<Weather> response = mHttpClient.getCurrentWeatherByLocation(
                request.getLang(),
                request.getUnits(),
                getCity().getLat(),
                getCity().getLng());

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

}
