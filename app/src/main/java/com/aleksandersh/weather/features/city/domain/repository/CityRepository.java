package com.aleksandersh.weather.features.city.domain.repository;


import com.aleksandersh.weather.features.city.data.transferable.CityDto;

import java.util.List;

import io.reactivex.Single;


/**
 * Created by Vladimir Kondenko on 23.07.17.
 */

public interface CityRepository {

    public Single<List<CityDto>> getCity(String name);

}
