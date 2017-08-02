package com.aleksandersh.weather.features.city.presentation;


import com.aleksandersh.weather.features.city.data.transferable.CityDto;

import java.util.List;


public interface CityView {

    void updateData(List<CityDto> cities);

    void showError(Throwable throwable);

}
