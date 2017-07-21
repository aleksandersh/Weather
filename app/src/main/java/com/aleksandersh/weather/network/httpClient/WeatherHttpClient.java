package com.aleksandersh.weather.network.httpClient;

import com.aleksandersh.weather.model.Weather;

/**
 * Created by AleksanderSh on 14.07.2017.
 * <p>
 * Интерфейс для http-клиента сервиса. Его задача заключается в получении данных от сервиса
 * и обработке их в удобный для приложения вид.
 */

public interface WeatherHttpClient {
    HttpClientResponse<Weather> getCurrentWeatherByCityId(String lang, String units, long cityId);

    HttpClientResponse<Weather> getCurrentWeatherByCityName(String lang, String units,
                                                            String cityName);

    HttpClientResponse<Weather> getCurrentWeatherByLocation(String lang, String units,
                                                            double latitude, double longitude);
}
