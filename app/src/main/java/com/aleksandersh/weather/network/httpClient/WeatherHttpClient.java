package com.aleksandersh.weather.network.httpClient;

import com.aleksandersh.weather.model.Weather;

/**
 * Created by AleksanderSh on 14.07.2017.
 * <p>
 * Интерфейс для http-клиента сервиса. Его задача заключается в получении данных от сервиса
 * и обработке их в удобный для приложения вид.
 */

public interface WeatherHttpClient {
    HttpClientResponse<Weather> getCurrentWeatherByCityId(long cityId);

    HttpClientResponse<Weather> getCurrentWeatherByCityName(String cityName);

    HttpClientResponse<Weather> getCurrentWeatherByLocation(double latitude, double longitude);
}
