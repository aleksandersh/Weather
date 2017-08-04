package com.aleksandersh.weather.features.weather.domain.service;


import com.aleksandersh.weather.features.weather.data.model.transferable.CurrentWeatherDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by AleksanderSh on 14.07.2017.
 * <p>
 * Определение интерфейса работы с сервисом Open weather map для библиотеки Retrofit 2.
 * Содержит методы для получения текущей погоды.
 */

public interface CurrentWeatherHttpService {

    /**
     * Получение погоды в городе с заданным названием.
     *
     * @param lang   Язык возвращаемых данных.
     * @param units  Единица измерения температур.
     * @param name   Название города.
     * @return Ответ от сервера.
     */
    @GET("weather")
    Call<CurrentWeatherDto> getCurrentWeatherByCityName(
            @Query("lang") String lang,
            @Query("units") String units,
            @Query("q") String name
    );

    /**
     * Получение погоды в городе с заданным идентификатором.
     *
     * @param lang   Язык возвращаемых данных.
     * @param units  Единица измерения температур.
     * @param id     Идентификатор города.
     * @return Ответ от сервера.
     */
    @GET("weather")
    Call<CurrentWeatherDto> getCurrentWeatherByCityId(
            @Query("lang") String lang,
            @Query("units") String units,
            @Query("id") long id
    );

    /**
     * Получение погоды в локации, заданной координатами.
     *
     * @param lang      Язык возвращаемых данных.
     * @param units     Единица измерения температур.
     * @param latitude  Широта.
     * @param longitude Долгота.
     * @return Ответ от сервера.
     */
    @GET("weather")
    Call<CurrentWeatherDto> getCurrentWeatherByLocation(
            @Query("lang") String lang,
            @Query("units") String units,
            @Query("lat") double latitude,
            @Query("lon") double longitude
    );
}
