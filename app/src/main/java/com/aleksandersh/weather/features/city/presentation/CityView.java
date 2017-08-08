package com.aleksandersh.weather.features.city.presentation;


import com.aleksandersh.weather.features.city.data.model.storable.City;


public interface CityView {

    void updateData(City cities);

    void showError(Throwable throwable);

}
