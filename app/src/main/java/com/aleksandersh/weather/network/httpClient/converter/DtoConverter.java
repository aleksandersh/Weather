package com.aleksandersh.weather.network.httpClient.converter;

import com.aleksandersh.weather.model.Weather;

/**
 * Created by AleksanderSh on 15.07.2017.
 */

public interface DtoConverter<T> {
    Weather convert(T dto);
}
