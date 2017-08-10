package com.aleksandersh.weather.features.city.domain.interactor;


import com.aleksandersh.weather.features.city.data.model.storable.City;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;


/**
 * Created by Vladimir Kondenko on 23.07.17.
 */

public interface CityInteractor {

    Single<List<City>> getSuggestions(String name);

    Flowable<City> getSavedCities();

    void addCity(City city);

    void updateCurrentCity(City city);

    void deleteSavedCity(City city);

}
