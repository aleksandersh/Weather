package com.aleksandersh.weather.features.weather.domain.repository;


import com.aleksandersh.weather.features.weather.data.model.storable.Weather;
import com.aleksandersh.weather.network.HttpClientResponse;


/**
 * Created by AleksanderSh on 14.07.2017.
 * <p>
 * Интерфейс для http-клиента сервиса. Его задача заключается в получении данных от сервиса
 * и обработке их в удобный для приложения вид.
 */

public interface CurrentWeatherRepository {

    HttpClientResponse<Weather> getCurrentWeatherByCityId(String lang, String units, long cityId);

    HttpClientResponse<Weather> getCurrentWeatherByCityName(String lang, String units,
                                                            String cityName);

    HttpClientResponse<Weather> getCurrentWeatherByLocation(String lang, String units,
                                                            double latitude, double longitude);

}
