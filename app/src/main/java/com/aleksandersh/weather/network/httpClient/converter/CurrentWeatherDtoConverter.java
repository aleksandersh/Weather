package com.aleksandersh.weather.network.httpClient.converter;

import com.aleksandersh.weather.model.Weather;
import com.aleksandersh.weather.network.dto.currentWeather.CurrentWeatherDto;

/**
 * Created by AleksanderSh on 15.07.2017.
 */

public class CurrentWeatherDtoConverter implements DtoConverter<Weather, CurrentWeatherDto> {
    @Override
    public Weather convert(CurrentWeatherDto dto) {
        return new Weather();
    }
}
