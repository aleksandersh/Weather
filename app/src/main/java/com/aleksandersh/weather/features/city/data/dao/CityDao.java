package com.aleksandersh.weather.features.city.data.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.aleksandersh.weather.features.city.data.model.storable.City;

import io.reactivex.Flowable;


/**
 * Created by Vladimir Kondenko on 08.08.17.
 */

@Dao
public interface CityDao {

    @Query("SELECT * FROM city")
    Flowable<City> getSavedCities();

    @Update
    void updateCurrentCity(City city);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCity(City city);

    @Delete
    void deleteCity(City city);

}
