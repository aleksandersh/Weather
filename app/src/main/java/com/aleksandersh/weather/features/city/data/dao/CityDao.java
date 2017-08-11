package com.aleksandersh.weather.features.city.data.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.aleksandersh.weather.features.city.data.model.storable.City;
import com.aleksandersh.weather.features.city.data.model.storable.LocationTuple;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;


/**
 * Created by Vladimir Kondenko on 08.08.17.
 */

@Dao
public interface CityDao {

    @Query("SELECT * FROM city")
    Flowable<City> getSavedCities();

    @Query("SELECT * FROM city WHERE iscurrent='true'")
    Maybe<City> getCurrentCity();

    @Query("SELECT name FROM city WHERE iscurrent='true'")
    Single<String> getCurrentCityName();

    @Query("SELECT lat, lng FROM city WHERE iscurrent='true'")
    Single<LocationTuple> getCurrentLocation();

    @Update
    void updateCurrentCity(City city);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCity(City city);

    @Delete
    void deleteCity(City city);

}
