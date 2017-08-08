package com.aleksandersh.weather.features.city.domain.interactor;


import com.aleksandersh.weather.features.city.data.model.storable.City;
import com.aleksandersh.weather.features.city.domain.repository.CityRepository;
import com.aleksandersh.weather.utils.Const;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Vladimir Kondenko on 08.08.17.
 */

public class CityInteractorImpl implements CityInteractor {

    private CityRepository repository;

    @Inject
    public CityInteractorImpl(CityRepository repository) {
        this.repository = repository;
    }

    public Observable<City> getSuggestions(String name) {
        return repository.getSuggestions(name, Const.DEFAULT_SUGGESTIONS_NUMBER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void saveCity(City city) {
        repository.saveCity(city);
    }

}
