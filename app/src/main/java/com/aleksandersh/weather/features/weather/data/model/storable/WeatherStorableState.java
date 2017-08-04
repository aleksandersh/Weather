package com.aleksandersh.weather.features.weather.data.model.storable;


import java.util.Date;


/**
 * Created by AleksanderSh on 16.07.2017.
 * <p>
 * Сохраненное состояние погоды. Используется при работе с хранилищем.
 */

public class WeatherStorableState {

    private Weather weather;

    private WeatherRequest request;

    private Date date;

    public WeatherStorableState(Weather weather, WeatherRequest request, Date date) {
       this.weather = weather;
       this.request = request;
       this.date = date;
    }

    public Weather getWeather() {
        return weather;
    }

    public WeatherRequest getRequest() {
        return request;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }
}
