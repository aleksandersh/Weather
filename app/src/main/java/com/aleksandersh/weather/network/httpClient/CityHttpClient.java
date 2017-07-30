package com.aleksandersh.weather.network.httpClient;

import com.aleksandersh.weather.network.dto.city.CityDto;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Vladimir Kondenko on 23.07.17.
 */

public interface CityHttpClient {

    public Single<List<CityDto>> getCity(String name);

}
