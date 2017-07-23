package com.aleksandersh.weather.network.httpClient.converter;

import com.aleksandersh.weather.model.weather.Weather;

/**
 * Created by AleksanderSh on 15.07.2017.
 * <p>
 * Интерфейс конвертеров объектов передачи данных в используемую в приложении модель погоды.
 * На данных момент подразумевается, что класс используемой модели всегда будет {@link Weather},
 * но объекты передачи данных из http-сервиса могут отличаться.
 */

public interface DtoConverter<T> {
    Weather convert(T dto);
}
