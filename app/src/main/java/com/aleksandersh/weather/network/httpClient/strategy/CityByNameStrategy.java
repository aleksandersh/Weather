package com.aleksandersh.weather.network.httpClient.strategy;

import com.aleksandersh.weather.network.dto.currentWeather.CurrentWeatherDto;
import com.aleksandersh.weather.network.httpService.CurrentWeatherHttpService;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by AleksanderSh on 15.07.2017.
 * <p>
 * Стратегия получения текущей погоды от сервиса для города с заданным названием.
 */

public class CityByNameStrategy implements HttpClientStrategy<CurrentWeatherDto> {
    private CurrentWeatherHttpService mHttpService;
    private String mCityName;

    public CityByNameStrategy(CurrentWeatherHttpService httpService) {
        mHttpService = httpService;
    }

    @Override
    public Response<CurrentWeatherDto> getWeather(String apiKey, String lang, String units)
            throws IOException {
        return mHttpService.getCurrentWeatherByCityName(apiKey, lang, units, mCityName).execute();
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
