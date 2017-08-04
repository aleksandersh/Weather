package com.aleksandersh.weather.features.weather.domain.strategy;


import com.aleksandersh.weather.features.weather.data.model.transferable.CurrentWeatherDto;
import com.aleksandersh.weather.features.weather.domain.service.CurrentWeatherHttpService;

import java.io.IOException;

import retrofit2.Response;


/**
 * Created by AleksanderSh on 15.07.2017.
 * <p>
 * Стратегия получения текущей погоды от сервиса для города с заданным названием.
 */

public class WeatherByCityNameStrategy implements WeatherStrategy<CurrentWeatherDto> {

    private CurrentWeatherHttpService mHttpService;

    private String mCityName;

    public WeatherByCityNameStrategy(CurrentWeatherHttpService httpService) {
        mHttpService = httpService;
    }

    @Override
    public Response<CurrentWeatherDto> getWeather(String lang, String units)
            throws IOException {
        return mHttpService.getCurrentWeatherByCityName(lang, units, mCityName).execute();
    }

    /**
     * Установка названия города.
     *
     * @param cityName Название города.
     */
    public void setCityName(String cityName) {
        this.mCityName = cityName;
    }
}
