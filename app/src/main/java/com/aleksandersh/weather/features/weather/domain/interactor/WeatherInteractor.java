package com.aleksandersh.weather.features.weather.domain.interactor;


import com.aleksandersh.weather.features.city.data.model.storable.City;
import com.aleksandersh.weather.features.weather.data.model.WeatherDtoConverter;
import com.aleksandersh.weather.features.weather.data.model.storable.Weather;
import com.aleksandersh.weather.features.weather.data.model.storable.WeatherRequest;
import com.aleksandersh.weather.features.weather.data.model.storable.WeatherStorableState;
import com.aleksandersh.weather.features.weather.data.model.transferable.forecast.ForecastResultDto;
import com.aleksandersh.weather.features.weather.domain.service.CurrentWeatherService;
import com.aleksandersh.weather.features.weather.domain.service.ForecastService;
import com.aleksandersh.weather.features.weather.storage.WeatherDao;
import com.aleksandersh.weather.storage.SettingsDao;
import com.aleksandersh.weather.utils.Utils;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


/**
 * Created by Vladimir Kondenko on 03.08.17.
 */

public class WeatherInteractor {

    private CurrentWeatherService currentWeatherService;
    private ForecastService forecastService;
    private SettingsDao settingsDao;
    private WeatherDao weatherDao;
    private WeatherDtoConverter converter;

    @Inject
    public WeatherInteractor(
            CurrentWeatherService currentWeatherService,
            ForecastService forecastService,
            SettingsDao settingsDao,
            WeatherDao weatherDao,
            WeatherDtoConverter converter) {
        this.currentWeatherService = currentWeatherService;
        this.forecastService = forecastService;
        this.settingsDao = settingsDao;
        this.weatherDao = weatherDao;
        this.converter = converter;
    }

    public Single<ForecastResultDto> getForecast(City city) {
        Timber.i("Getting forecast for " + city.getName());
        String units = settingsDao.getUnits();
        String locale = settingsDao.getLocale();
        return forecastService.getForecast(city.getLat(), city.getLng(), units, locale)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Weather> getCurrentWeather(City city) {
        Timber.i("Getting weather for " + city.getName());
        String units = settingsDao.getUnits();
        String locale = settingsDao.getLocale();
        return currentWeatherService.getCurrentWeatherByLocation(city.getLat(), city.getLng(), units, locale)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(converter::convert)
                .doOnSuccess(weather -> save(weather, city.getLat(), city.getLng(), units, locale))
                .doOnSuccess(weather -> Timber.i("Weather found - " + weather.getTemperature()))
                .onErrorResumeNext(getSavedWeather())
                ;
    }

    private void save(Weather weather, double lat, double lng, String units, String locale) {
        Timber.i("Saving weather");
        WeatherRequest request = new WeatherRequest(lng, lat, units, locale);
        Date updateTime = new Date(System.currentTimeMillis());
        WeatherStorableState storableState = new WeatherStorableState(weather, request, updateTime);
        Utils.transaction(() -> weatherDao.saveWeather(storableState));
    }

    private Single<Weather> getSavedWeather() {
        Timber.i("Getting saved weather");
        return weatherDao.getSavedWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(WeatherStorableState::getWeather);
    }

}
