package com.aleksandersh.weather.features.city.data.repository;


import com.aleksandersh.weather.features.city.data.dao.CityDao;
import com.aleksandersh.weather.features.city.data.model.CityDtoConverter;
import com.aleksandersh.weather.features.city.data.model.storable.City;
import com.aleksandersh.weather.features.city.data.model.transferable.CityResultDto;
import com.aleksandersh.weather.features.city.domain.interactor.CityInteractor;
import com.aleksandersh.weather.features.city.domain.service.CityHttpService;
import com.aleksandersh.weather.utils.Const;
import com.aleksandersh.weather.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.Single;


/**
 * Created by Vladimir Kondenko on 23.07.17.
 */

public class CityInteractorImpl implements CityInteractor {

    private CityHttpService service;
    private CityDao dao;
    private CityDtoConverter converter;

    @Inject
    public CityInteractorImpl(CityHttpService service, CityDao cityDao, CityDtoConverter cityDtoConverter) {
        this.service = service;
        this.dao = cityDao;
        this.converter = cityDtoConverter;
    }

    @Override
    public Single<List<City>> getSuggestions(String name) {
        return service.searchByName(name, name, Const.DEFAULT_SUGGESTIONS_NUMBER)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
                .map(CityResultDto::getCities)
                .toObservable()
                .flatMap(Observable::fromIterable)
                .map(converter::convert)
                .distinct(City::getId)
                .distinct(City::getName)
                .distinct(City::getCountryName)
                .toList();
    }

    @Override
    public Flowable<City> getSavedCities() {
        return dao.getSavedCities()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    @Override
    public Single<City> getCurrentCity() {
        return dao.getCurrentCity()
                .switchIfEmpty(getDefaultCity())
                .toSingle();
    }

    @Override
    public void updateCurrentCity(City city) {
        Utils.transaction(() -> dao.updateCurrentCity(city));
    }

    @Override
    public void addCity(City city) {
        Utils.transaction(() -> dao.addCity(city));
    }

    @Override
    public void deleteCity(City city) {
        Utils.transaction(() -> dao.deleteCity(city));
    }

    // An ugly way to return Moscow as a default city
    private MaybeSource<City> getDefaultCity() {
        return getSuggestions("Moscow")
                .map(list -> list.get(0))
                .toMaybe();
    }

}
