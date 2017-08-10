package com.aleksandersh.weather.features.city.presentation;


import com.aleksandersh.weather.features.city.data.model.storable.City;

import java.util.List;


public interface CityView {

    void showSuggestions(List<City> cities);

    void addSavedCityToList(City cities);

    void showCurrentCity(City city);

    void showSearchState(boolean show);

    void showError(Throwable throwable);

}
