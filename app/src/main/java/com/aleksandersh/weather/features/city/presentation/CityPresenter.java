package com.aleksandersh.weather.features.city.presentation;


import com.aleksandersh.weather.features.city.data.model.transferable.CityDto;
import com.aleksandersh.weather.features.city.domain.repository.CityRepository;
import com.aleksandersh.weather.storage.PreferencesHelper;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by Vladimir Kondenko on 23.07.17.
 */

public class CityPresenter {

    private CityView view;

    private CityRepository client;

    private PreferencesHelper preferencesHelper;

    private Disposable cityDisposable = null;

    @Inject
    public CityPresenter(CityRepository client, PreferencesHelper preferencesHelper) {
        this.client = client;
        this.preferencesHelper = preferencesHelper;
    }

    public void onAttach(CityView view) {
        this.view = view;
    }

    public void onDetach() {
        view = null;
        if (cityDisposable != null && !cityDisposable.isDisposed()) cityDisposable.dispose();
    }

    public void onQueryUpdated(String name) {
        client.getCity(name)
                .subscribe(view::updateData, view::showError);
    }

    public void onCitySelected(CityDto city) {
        preferencesHelper.saveCity(city);
    }

}
