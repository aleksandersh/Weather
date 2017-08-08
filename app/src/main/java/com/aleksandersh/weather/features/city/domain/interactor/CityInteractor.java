package com.aleksandersh.weather.features.city.domain.interactor;


import com.aleksandersh.weather.features.city.data.model.storable.City;

import io.reactivex.Observable;


/**
 * Created by Vladimir Kondenko on 08.08.17.
 */

public interface CityInteractor {

    Observable<City> getSuggestions(String name);

    void saveCity(City city);

}
