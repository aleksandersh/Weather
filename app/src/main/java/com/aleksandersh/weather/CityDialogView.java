package com.aleksandersh.weather;

import com.aleksandersh.weather.model.city.City;
import com.aleksandersh.weather.model.city.CityResultWrapper;

import java.util.List;

public interface CityDialogView {

    void updateData(List<City> cities);

    void showError(Throwable r);

}
