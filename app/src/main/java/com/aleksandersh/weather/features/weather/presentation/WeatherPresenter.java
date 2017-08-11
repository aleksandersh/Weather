package com.aleksandersh.weather.features.weather.presentation;


import com.aleksandersh.weather.features.city.domain.interactor.CityInteractor;
import com.aleksandersh.weather.features.weather.domain.interactor.WeatherInteractor;
import com.aleksandersh.weather.utils.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by AleksanderSh on 20.07.2017.
 */

public class WeatherPresenter extends BasePresenter<WeatherView> {

    private WeatherInteractor weatherInteractor;
    private CityInteractor cityInteractor;

    @Inject
    public WeatherPresenter(WeatherInteractor weatherInteractor, CityInteractor cityInteractor, CompositeDisposable disposables) {
        super(disposables);
        this.weatherInteractor = weatherInteractor;
        this.cityInteractor = cityInteractor;
    }

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
        onUpdate();
    }

    public void onUpdate() {
        getCurrentWeather();
    }

    private void getCurrentWeather() {
        add(cityInteractor.getCurrentCity()
                .doAfterSuccess(city -> {
                    view.showCurrentCity(city.getName());
                    weatherInteractor.getCurrentWeather(city)
                            .subscribe(view::showCurrentWeather, view::showError);
                })
                .subscribe()
        );
    }

}
