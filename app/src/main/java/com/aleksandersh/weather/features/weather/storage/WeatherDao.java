package com.aleksandersh.weather.features.weather.storage;


import com.aleksandersh.weather.features.weather.data.storable.WeatherStorableState;


/**
 * Created by AleksanderSh on 21.07.2017.
 * <p>
 * Интерфейс для обращения к хранилищу с данными.
 */

public interface WeatherDao {

    void saveWeather(WeatherStorableState storableState);

    WeatherStorableState getWeatherByCityId(long cityId);
}
