package com.aleksandersh.weather.features.city.presentation;


import com.aleksandersh.weather.features.city.data.model.storable.City;
import com.aleksandersh.weather.features.city.domain.interactor.CityInteractor;
import com.aleksandersh.weather.utils.Utils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;


/**
 * Created by Vladimir Kondenko on 23.07.17.
 */

public class CityPresenter {

    private CityView view;

    private CityInteractor interactor;

    private CompositeDisposable disposables = null;

    @Inject
    public CityPresenter(CityInteractor interactor, CompositeDisposable compositeDisposable) {
        this.interactor = interactor;
        this.disposables = compositeDisposable;
    }

    public void onAttach(CityView view) {
        this.view = view;
        // Initial state
        view.showSearchState(false);
        onSavedCitiesRequested();
    }

    public void onDetach() {
        Utils.dispose(disposables);
        view = null;
    }

    public void onSearchQueryUpdated(String name) {
        disposables.add(interactor.getSuggestions(name).subscribe(view::showSuggestions, view::showError));
    }

    public void onSavedCitiesRequested() {
        disposables.add(interactor.getSavedCities()
                .doOnNext(city -> Timber.i("Emitting " + city))
                .doOnNext(city -> {
                    if (city.isCurrent()) view.showCurrentCity(city);
                })
                .subscribe(view::addSavedCityToList, view::showError));
    }

    public void onSuggestionClick(City city) {
        interactor.addCity(city);
        view.showSearchState(false);
        view.showCurrentCity(city);
    }

    public void onSelectedCityClick(City city) {
        interactor.updateCurrentCity(city);
        view.showCurrentCity(city);
    }

    public void onSavedCityDeleted(City city) {
        interactor.deleteSavedCity(city);
    }

}
