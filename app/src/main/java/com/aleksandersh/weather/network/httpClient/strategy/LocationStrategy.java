package com.aleksandersh.weather.network.httpClient.strategy;

import com.aleksandersh.weather.network.dto.currentWeather.CurrentWeatherDto;
import com.aleksandersh.weather.network.httpService.CurrentWeatherHttpService;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by AleksanderSh on 16.07.2017.
 * <p>
 * Стратегия получения текущей погоды от сервиса для местоположения, заданного координатами.
 */

public class LocationStrategy implements HttpClientStrategy<CurrentWeatherDto> {
    private CurrentWeatherHttpService mHttpService;
    private double latitude;
    private double longitude;

    public LocationStrategy(CurrentWeatherHttpService httpService) {
        mHttpService = httpService;
    }

    @Override
    public Response<CurrentWeatherDto> getWeather(String apiKey, String lang, String units)
            throws IOException {
        return mHttpService.getCurrentWeatherByLocation(apiKey, lang, units, latitude, longitude)
                .execute();
    }

    /**
     * Задать широту.
     *
     * @param latitude Широта.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Задать долготу.
     *
     * @param longitude Долгота.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
