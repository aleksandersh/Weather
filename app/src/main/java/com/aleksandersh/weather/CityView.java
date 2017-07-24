package com.aleksandersh.weather;

import com.aleksandersh.weather.model.city.City;

import java.util.List;

public interface CityView {

    void updateData(List<City> cities);

    void showError(Throwable throwable);

}
