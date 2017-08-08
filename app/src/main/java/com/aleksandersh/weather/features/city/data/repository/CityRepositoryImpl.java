package com.aleksandersh.weather.features.city.data.repository;


import com.aleksandersh.weather.features.city.data.dao.CityDao;
import com.aleksandersh.weather.features.city.data.model.CityDtoConverter;
import com.aleksandersh.weather.features.city.data.model.storable.City;
import com.aleksandersh.weather.features.city.data.model.transferable.CityResultDto;
import com.aleksandersh.weather.features.city.domain.repository.CityRepository;
import com.aleksandersh.weather.features.city.domain.service.CityHttpService;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;


/**
 * Created by Vladimir Kondenko on 23.07.17.
 */

public class CityRepositoryImpl implements CityRepository {

    private CityHttpService service;
    private CityDao dao;
    private CityDtoConverter converter;

    @Inject
    public CityRepositoryImpl(CityHttpService service, CityDao cityDao, CityDtoConverter cityDtoConverter) {
        this.service = service;
        this.dao = cityDao;
        this.converter = cityDtoConverter;
    }

    @Override
    public Observable<City> getSuggestions(String name, int suggestionsNumber) {
        return service.searchByName(name, name, suggestionsNumber)
                .map(CityResultDto::getCities)
                .toObservable()
                .flatMap(Observable::fromIterable)
                .map(converter::convert);
    }

    @Override
    public Flowable<City> getSavedCities() {
        return dao.getSavedCities();
    }

    @Override
    public void saveCity(City city) {
        dao.saveCity(city);
    }

    @Override
    public Single<City> getCurrentCity() {
        return null;
    }

    @Override
    public void setCurrentCity(City city) {

    }

}
