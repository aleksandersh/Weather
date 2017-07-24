package com.aleksandersh.weather.network.httpService;

import com.aleksandersh.weather.model.city.CityResultWrapper;

import io.reactivex.Single;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Vladimir Kondenko on 22.07.17.
 */

public interface CityHttpService {

    /**
     * Поиск города в базе данных GeoNames.
     * <br>
     * TODO Добавить параметр fuzzy (&fuzzy=0.8) для коректировки опечаток (<a href="http://www.geonames.org/export/geonames-search.html">подробнее</a>) и lang для возвращения результата на нужном языке
     *
     * @param name название города или его часть.
     * @return список городов, в названии которых содержится параметр name.
     *
     */
    @GET("searchJSON")
    Single<CityResultWrapper> searchByName(@Query("name") String name, @Query("maxRows") int suggestionsNumber);

}
