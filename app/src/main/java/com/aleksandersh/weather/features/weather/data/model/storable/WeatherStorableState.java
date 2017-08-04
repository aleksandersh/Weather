package com.aleksandersh.weather.features.weather.data.model.storable;


import java.util.Date;


/**
 * Created by AleksanderSh on 16.07.2017.
 * <p>
 * Сохраненное состояние погоды. Используется при работе с хранилищем.
 */

public class WeatherStorableState {

    private Weather mWeather;

    private WeatherRequest mRequest;

    private Date mDate;

    public WeatherStorableState(Weather weather, WeatherRequest request, Date date) {
        mWeather = weather;
        mRequest = request;
        mDate = date;
    }

    public Weather getWeather() {
        return mWeather;
    }

    public WeatherRequest getRequest() {
        return mRequest;
    }

    public Date getDate() {
        return new Date(mDate.getTime());
    }
}
