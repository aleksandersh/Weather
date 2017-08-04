package com.aleksandersh.weather.features.weather.domain.strategy;


import com.aleksandersh.weather.features.weather.data.model.transferable.CurrentWeatherDto;
import com.aleksandersh.weather.features.weather.domain.service.CurrentWeatherHttpService;

import java.io.IOException;

import retrofit2.Response;


/**
 * Created by AleksanderSh on 15.07.2017.
 * <p>
 * Стратегия получения текущей погоды от сервиса для города с заданным идентификатором.
 */

public class WeatherByCityIdStrategy implements WeatherStrategy<CurrentWeatherDto> {

    private CurrentWeatherHttpService mHttpService;

    private long mCityId;

    public WeatherByCityIdStrategy(CurrentWeatherHttpService httpService, long cityId) {
        mHttpService = httpService;
        mCityId = cityId;
    }

    @Override
    public Response<CurrentWeatherDto> getWeather(String apiKey, String lang, String units)
            throws IOException {
        return mHttpService.getCurrentWeatherByCityId(apiKey, lang, units, mCityId).execute();
    }
}
