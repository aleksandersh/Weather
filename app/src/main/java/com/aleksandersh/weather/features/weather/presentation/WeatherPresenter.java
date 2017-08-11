package com.aleksandersh.weather.features.weather.presentation;


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
        getCurrentWeather();
    }

    private void getCurrentWeather() {
        add(cityInteractor.getCurrentCity()
                .subscribe(city -> {
                    Timber.i("City found: " + city.getName());
                    view.showCurrentCity(city.getName());
                    weatherInteractor.getCurrentWeather(city)
                            .doOnSuccess(w -> view.showLoading(false))
                            .subscribe(view::showCurrentWeather, view::showError);

                }, view::showError)
        );
    }

}
