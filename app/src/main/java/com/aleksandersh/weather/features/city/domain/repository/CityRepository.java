package com.aleksandersh.weather.features.city.domain.repository;


import com.aleksandersh.weather.features.city.data.model.storable.City;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;


/**
 * Created by Vladimir Kondenko on 23.07.17.
 */

public interface CityRepository {

    public Observable<City> getSuggestions(String name, int suggestionsNumber);

    public Flowable<City> getSavedCities();

    public void saveCity(City city);

    public Single<City> getCurrentCity();

    public void setCurrentCity(City city);

}
