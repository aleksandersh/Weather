package com.aleksandersh.weather.network.httpClient.converter;

/**
 * Created by AleksanderSh on 15.07.2017.
 */

public interface DtoConverter<T, U> {
    T convert(U dto);
}
