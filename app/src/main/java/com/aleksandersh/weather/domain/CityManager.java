package com.aleksandersh.weather.domain;

import com.aleksandersh.weather.CityView;
import com.aleksandersh.weather.network.dto.city.CityDto;
import com.aleksandersh.weather.network.httpClient.CityHttpClient;
import com.aleksandersh.weather.network.httpClient.converter.CityDtoConverter;
import com.aleksandersh.weather.utils.PreferencesHelper;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by Vladimir Kondenko on 23.07.17.
 */

public class CityManager {

    private CityView view;
    private CityHttpClient client;
    private CityDtoConverter dtoConverter;
    private PreferencesHelper preferencesHelper;

    private Disposable cityDisposable = null;

    @Inject
    public CityManager(CityView view, CityHttpClient client, CityDtoConverter cityDtoConverter, PreferencesHelper preferencesHelper) {
        this.view = view;
        this.client = client;
        this.dtoConverter = cityDtoConverter;
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
