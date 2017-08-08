package com.aleksandersh.weather.features.city.presentation;


import com.aleksandersh.weather.features.city.data.model.storable.City;
import com.aleksandersh.weather.features.city.domain.interactor.CityInteractor;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by Vladimir Kondenko on 23.07.17.
 */

public class CityPresenter {

    private CityView view;

    private CityInteractor interactor;

    private Disposable cityDisposable = null;

    @Inject
    public CityPresenter(CityInteractor interactor) {
        this.interactor = interactor;
    }

    public void onAttach(CityView view) {
        this.view = view;
    }

    public void onDetach() {
        view = null;
        if (cityDisposable != null && !cityDisposable.isDisposed()) cityDisposable.dispose();
    }

    public void onSearchQueryUpdated(String name) {
        interactor.getSuggestions(name)
                .subscribe(view::updateData, view::showError);
    }

    public void onCitySelected(City city) {
        interactor.saveCity(city);
    }

}
