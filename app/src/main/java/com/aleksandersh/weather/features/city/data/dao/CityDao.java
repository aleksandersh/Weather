package com.aleksandersh.weather.features.city.data.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.aleksandersh.weather.features.city.data.model.storable.City;

import io.reactivex.Flowable;
import io.reactivex.Single;


/**
 * Created by Vladimir Kondenko on 08.08.17.
 */

@Dao
public interface CityDao {

    @Query("SELECT * FROM city")
    Flowable<City> getSavedCities();

    @Insert
    void saveCity(City city);

    @Query("SELECT * FROM city WHERE selected='true'")
    Single<City> getSelectedCity();

    @Update
    void setSelected(City city);

    @Delete
    void deleteCity(City city);

}
