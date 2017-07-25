package com.aleksandersh.weather;

import com.aleksandersh.weather.network.dto.city.CityDto;

import java.util.List;

public interface CityView {

    void updateData(List<CityDto> cities);

    void showError(Throwable throwable);

}
