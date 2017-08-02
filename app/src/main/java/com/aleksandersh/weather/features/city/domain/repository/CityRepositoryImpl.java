package com.aleksandersh.weather.features.city.domain.repository;


import com.aleksandersh.weather.features.city.data.transferable.CityDto;
import com.aleksandersh.weather.features.city.data.transferable.CityResultDto;
import com.aleksandersh.weather.features.city.domain.service.CityHttpService;
import com.aleksandersh.weather.storage.Const;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Vladimir Kondenko on 23.07.17.
 */

public class CityRepositoryImpl implements CityRepository {

    private CityHttpService service;

    @Inject
    public CityRepositoryImpl(CityHttpService service) {
        this.service = service;
    }

    @Override
    public Single<List<CityDto>> getCity(String name) {
        return service.searchByName(name, name, Const.DEFAULT_SUGGESTIONS_NUMBER)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(CityResultDto::getCities);
    }

}
