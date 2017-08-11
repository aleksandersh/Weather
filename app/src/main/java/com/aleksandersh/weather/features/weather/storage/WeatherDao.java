package com.aleksandersh.weather.features.weather.storage;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.aleksandersh.weather.features.weather.data.model.storable.WeatherStorableState;

import io.reactivex.Single;


/**
 * Created by AleksanderSh on 21.07.2017.
 * <p>
 * Интерфейс для обращения к хранилищу с данными.
 */

@Dao
public interface WeatherDao {

    @Insert
    void saveWeather(WeatherStorableState storableState);

    @Query("SELECT * FROM saved_weather")
    Single<WeatherStorableState> getSavedWeather();

}
