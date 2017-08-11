package com.aleksandersh.weather.features.city.presentation;


import com.aleksandersh.weather.features.city.data.model.storable.City;
import com.aleksandersh.weather.features.city.domain.interactor.CityInteractor;
import com.aleksandersh.weather.utils.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;


/**
 * Created by Vladimir Kondenko on 23.07.17.
 */

public class CityPresenter extends BasePresenter<CityView> {

    private CityInteractor interactor;

    @Inject
    public CityPresenter(CityInteractor interactor, CompositeDisposable compositeDisposable) {
        super(compositeDisposable);
        this.interactor = interactor;
    }

    public void onAttach(CityView view) {
        super.onAttach(view);
        view.showSearchState(false);
        onSavedCitiesRequested();
    }

    public void onSearchQueryUpdated(String name) {
        disposables.add(interactor.getSuggestions(name).subscribe(view::showSuggestions, (errorMessageId) -> view.showError(errorMessageId)));
    }

    public void onSavedCitiesRequested() {
        disposables.add(interactor.getSavedCities()
                .doOnNext(city -> Timber.i("Emitting " + city))
                .doOnNext(city -> {
                    if (city.isCurrent()) view.showCurrentCity(city);
                })
                .subscribe(view::addSavedCityToList, (errorMessageId) -> view.showError(errorMessageId)));
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
        interactor.deleteCity(city);
    }

}
