package com.aleksandersh.weather.network.httpClient;

import android.util.Log;

import com.aleksandersh.weather.model.Weather;
import com.aleksandersh.weather.network.dto.currentWeather.CurrentWeatherDto;
import com.aleksandersh.weather.network.httpClient.converter.DtoConverter;
import com.aleksandersh.weather.network.httpClient.converter.OpenWeatherMapDtoConverter;
import com.aleksandersh.weather.network.httpClient.strategy.CityByIdStrategy;
import com.aleksandersh.weather.network.httpClient.strategy.CityByNameStrategy;
import com.aleksandersh.weather.network.httpClient.strategy.HttpClientStrategy;
import com.aleksandersh.weather.network.httpClient.strategy.LocationStrategy;
import com.aleksandersh.weather.network.httpService.CurrentWeatherHttpService;
import com.aleksandersh.weather.utils.ErrorsHelper;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AleksanderSh on 14.07.2017.
 * <p>
 * Реализация http-клиента для сервиса Open weather map.
 */

public class OpenWeatherMapHttpClient implements WeatherHttpClient {
    private static final String TAG = "OwmHttpClient";
    private static final String API_KEY = "7eb42e583dff5e64a589739dd927bd0c";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    private CurrentWeatherHttpService mCurrentWeatherHttpService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrentWeatherHttpService.class);
    private DtoConverter<CurrentWeatherDto> mConverter = new OpenWeatherMapDtoConverter();

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
        CityByIdStrategy strategy =
                new CityByIdStrategy(mCurrentWeatherHttpService, cityId);
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
        CityByNameStrategy strategy =
                new CityByNameStrategy(mCurrentWeatherHttpService);
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
        LocationStrategy strategy = new LocationStrategy(mCurrentWeatherHttpService);
        strategy.setLatitude(latitude);
        strategy.setLongitude(longitude);
        return getCurrentWeather(lang, units, strategy);
    }

    private HttpClientResponse<Weather> getCurrentWeather(
            String lang,
            String units,
            HttpClientStrategy<CurrentWeatherDto> strategy) {
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
                clientResponse.setErrorCode(ErrorsHelper.ERROR_HTTP_REQUEST_FAILED);
            }
        } catch (IOException e) {
            clientResponse.setSuccessful(false);
            clientResponse.setErrorCode(ErrorsHelper.ERROR_HTTP_CONTACTING_SERVER);
            Log.d(TAG, "getCurrentWeatherByCityId: Problem occurred talking to the server.");
        }
        return clientResponse;
    }
}
