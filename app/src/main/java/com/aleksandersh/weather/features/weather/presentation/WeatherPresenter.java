package com.aleksandersh.weather.features.weather.presentation;


import com.aleksandersh.weather.features.city.data.model.storable.City;
import com.aleksandersh.weather.features.city.domain.interactor.CityInteractor;
import com.aleksandersh.weather.features.weather.domain.interactor.WeatherInteractor;
import com.aleksandersh.weather.utils.BasePresenter;

import javax.inject.Inject;

import timber.log.Timber;


/**
 * Created by AleksanderSh on 20.07.2017.
 */

public class WeatherPresenter extends BasePresenter<WeatherView> {

    private WeatherInteractor weatherInteractor;
    private CityInteractor cityInteractor;

    @Inject
    public WeatherPresenter(WeatherInteractor weatherInteractor, CityInteractor cityInteractor) {
        super();
        this.weatherInteractor = weatherInteractor;
        this.cityInteractor = cityInteractor;
    }

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
        view.showLoading(true);
        onUpdate();
    }

    public void onUpdate() {
        add(cityInteractor.getCurrentCity()
                .subscribe(
                        city -> {
                            Timber.i("City found: " + city);
                            view.showCurrentCity(city.getName());
                            getCurrentWeather(city);
                            getForecast(city);
                        },
                        error -> {
                            view.showError(error);
                        })
        );
    }

    private void getForecast(City city) {
        weatherInteractor.getForecast(city)
                .subscribe(view::showForecast, view::showError);
    }

    private void getCurrentWeather(City city) {
        weatherInteractor.getCurrentWeather(city)
                .doOnSuccess(w -> view.showLoading(false))
                .subscribe(view::showCurrentWeather, view::showError);
    }

}
