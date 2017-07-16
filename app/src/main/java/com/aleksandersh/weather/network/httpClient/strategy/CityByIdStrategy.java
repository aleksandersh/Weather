package com.aleksandersh.weather.network.httpClient.strategy;

import com.aleksandersh.weather.network.dto.currentWeather.CurrentWeatherDto;
import com.aleksandersh.weather.network.httpService.CurrentWeatherHttpService;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by AleksanderSh on 15.07.2017.
 * <p>
 * Стратегия получения текущей погоды от сервиса для города с заданным идентификатором.
 */

public class CityByIdStrategy implements HttpClientStrategy<CurrentWeatherDto> {
    private CurrentWeatherHttpService mHttpService;
    private long mCityId;

    public CityByIdStrategy(CurrentWeatherHttpService httpService, long cityId) {
        mHttpService = httpService;
        mCityId = cityId;
    }

    @Override
    public Response<CurrentWeatherDto> getWeather(String apiKey, String lang, String units)
            throws IOException {
        return mHttpService.getCurrentWeatherByCityId(apiKey, lang, units, mCityId).execute();
    }
}
