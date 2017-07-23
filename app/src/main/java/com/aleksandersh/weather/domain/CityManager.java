package com.aleksandersh.weather.domain;

import com.aleksandersh.weather.CityDialogView;
import com.aleksandersh.weather.model.city.City;
import com.aleksandersh.weather.network.httpClient.CityHttpClient;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Vladimir Kondenko on 23.07.17.
 */

public class CityManager {

    private CityDialogView view;
    private CityHttpClient client;

    private Disposable cityDisposable = null;

    public CityManager(CityDialogView view, CityHttpClient client) {
        this.view = view;
        this.client = client;
    }

    public void onAttach(CityDialogView view) {
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

}
