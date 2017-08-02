package com.aleksandersh.weather.features.weather.domain.repository;


import android.util.Log;

import com.aleksandersh.weather.features.weather.data.storable.Weather;
import com.aleksandersh.weather.features.weather.data.transferable.CurrentWeatherDto;
import com.aleksandersh.weather.features.weather.domain.service.CurrentWeatherHttpService;
import com.aleksandersh.weather.features.weather.domain.strategy.WeatherByCityIdStrategy;
import com.aleksandersh.weather.features.weather.domain.strategy.WeatherByCityNameStrategy;
import com.aleksandersh.weather.features.weather.domain.strategy.WeatherByLocationStrategy;
import com.aleksandersh.weather.features.weather.domain.strategy.WeatherStrategy;
import com.aleksandersh.weather.network.ErrorMapper;
import com.aleksandersh.weather.network.HttpClientResponse;
import com.aleksandersh.weather.storage.DtoConverter;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Response;


/**
 * Created by AleksanderSh on 14.07.2017.
 * <p>
 * Реализация http-клиента для сервиса Open weather map.
 */

public class CurrentWeatherRepositoryImpl implements CurrentWeatherRepository {

    private static final String TAG = "OwmHttpClient";

    private static final String API_KEY = "7eb42e583dff5e64a589739dd927bd0c";

    private CurrentWeatherHttpService mCurrentWeatherHttpService;

    private DtoConverter<CurrentWeatherDto, Weather> mConverter;

    @Inject
    public CurrentWeatherRepositoryImpl(CurrentWeatherHttpService currentWeatherHttpService,
                                        DtoConverter<CurrentWeatherDto, Weather> converter) {
        mCurrentWeatherHttpService = currentWeatherHttpService;
        mConverter = converter;
    }

    /**
     * Получает погоду для города с заданным идентификатором.
     *
     * @param cityId Идентификатор города.
     * @return Обработанные данные от сервиса, однако, в случае ошибки при работе с ним,
     * модель погоды может быть {@code null}.
     */
    @Override
    public HttpClientResponse<Weather> getCurrentWeatherByCityId(String lang, String units,
                                                                 long cityId) {
        WeatherByCityIdStrategy strategy =
                new WeatherByCityIdStrategy(mCurrentWeatherHttpService, cityId);
        return getCurrentWeather(lang, units, strategy);
    }

    /**
     * Получает погоду для города с заданным названием.
     *
     * @param cityName Название города.
     * @return Обработанные данные от сервиса, однако, в случае ошибки при работе с ним,
     * модель погоды может быть {@code null}.
     */
    @Override
    public HttpClientResponse<Weather> getCurrentWeatherByCityName(String lang, String units,
                                                                   String cityName) {
        WeatherByCityNameStrategy strategy =
                new WeatherByCityNameStrategy(mCurrentWeatherHttpService);
        strategy.setCityName(cityName);
        return getCurrentWeather(lang, units, strategy);
    }

    /**
     * Получает погоду для местоположения, заданного координатами.
     *
     * @param latitude  Широта.
     * @param longitude Долгота.
     * @return Обработанные данные от сервиса, однако, в случае ошибки при работе с ним,
     * модель погоды может быть {@code null}.
     */
    @Override
    public HttpClientResponse<Weather> getCurrentWeatherByLocation(
            String lang,
            String units,
            double latitude,
            double longitude) {
        WeatherByLocationStrategy strategy = new WeatherByLocationStrategy(mCurrentWeatherHttpService);
        strategy.setLatitude(latitude);
        strategy.setLongitude(longitude);
        return getCurrentWeather(lang, units, strategy);
    }

    private HttpClientResponse<Weather> getCurrentWeather(
            String lang,
            String units,
            WeatherStrategy<CurrentWeatherDto> strategy) {
        HttpClientResponse<Weather> clientResponse = new HttpClientResponse<>();
        try {
            Response<CurrentWeatherDto> serviceResponse = strategy.getWeather(API_KEY, lang, units);
            if (serviceResponse.isSuccessful()) {
                Weather weather = mConverter.convert(serviceResponse.body());
                clientResponse.setSuccessful(true);
                clientResponse.setModel(weather);
            } else {
                // Было бы не плохо добавить обработку ошибок, но сервер на все возвращает либо
                // 200, либо 401 и иногда текстовое описание. Надо будет подумать.
                clientResponse.setSuccessful(false);
                clientResponse.setErrorCode(ErrorMapper.ERROR_HTTP_REQUEST_FAILED);
            }
        } catch (IOException e) {
            clientResponse.setSuccessful(false);
            clientResponse.setErrorCode(ErrorMapper.ERROR_HTTP_CONTACTING_SERVER);
            Log.d(TAG, "getCurrentWeatherByCityId: Problem occurred talking to the server.");
        }
        return clientResponse;
    }

}
