package com.aleksandersh.weather.features.weather.domain.interactor;


import com.aleksandersh.weather.features.city.data.dao.CityDao;
import com.aleksandersh.weather.features.city.data.model.storable.City;
import com.aleksandersh.weather.features.weather.data.model.storable.Weather;
import com.aleksandersh.weather.features.weather.data.model.storable.WeatherRequest;
import com.aleksandersh.weather.features.weather.data.model.storable.WeatherStorableState;
import com.aleksandersh.weather.features.weather.data.model.transferable.CurrentWeatherDto;
import com.aleksandersh.weather.features.weather.domain.service.CurrentWeatherService;
import com.aleksandersh.weather.features.weather.storage.WeatherDao;
import com.aleksandersh.weather.storage.DtoConverter;
import com.aleksandersh.weather.storage.SettingsDao;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Vladimir Kondenko on 03.08.17.
 */

public class WeatherInteractor {

    private CurrentWeatherService currentWeatherService;
    private SettingsDao settingsDao;
    private WeatherDao weatherDao;
    private CityDao cityDao;
    private DtoConverter<CurrentWeatherDto, Weather> converter;

    @Inject
    public WeatherInteractor(
            CurrentWeatherService currentWeatherService,
            SettingsDao settingsDao,
            WeatherDao weatherDao,
            CityDao cityDao,
            DtoConverter<CurrentWeatherDto, Weather> converter) {
        this.currentWeatherService = currentWeatherService;
        this.settingsDao = settingsDao;
        this.weatherDao = weatherDao;
        this.cityDao = cityDao;
        this.converter = converter;
    }

    public Single<Weather> getCurrentWeather(City city) {
        String units = settingsDao.getUnits();
        String locale = settingsDao.getLocale();
        return currentWeatherService.getCurrentWeatherByLocation(city.getLat(), city.getLng(), units, locale)
                .map(converter::convert)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(weather -> save(weather, city.getLat(), city.getLng(), units, locale))
                .onErrorResumeNext(getSavedWeather());
    }

    private void save(Weather weather, double lat, double lng, String units, String locale) {
        WeatherRequest request = new WeatherRequest(lng, lat, units, locale);
        Date updateTime = new Date(System.currentTimeMillis());
        WeatherStorableState storableState = new WeatherStorableState(weather, request, updateTime);
        weatherDao.saveWeather(storableState);
    }

    private Single<Weather> getSavedWeather() {
        return weatherDao.getSavedWeather()
                .map(WeatherStorableState::getWeather);
    }

}
