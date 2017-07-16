package com.aleksandersh.weather.network.httpClient.strategy;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by AleksanderSh on 15.07.2017.
 * <p>
 * Стратегии http-клиента инкапсулируют специфичную для различных запросов логику.
 */

public interface HttpClientStrategy<T> {

    /**
     * Обращение к http-сервису с запросом на получение погоды. В данном случае может изменяться
     * в разных стратегиях могут отличаться сервисы, запросы или параметры запросов.
     *
     * @param apiKey API-ключ сервиса.
     * @param lang   Язык ответа.
     * @param units  Единица измерения температур.
     * @return Ответ от сервиса.
     * @throws IOException Возникает в следствии недоступности сервера.
     */
    Response<T> getWeather(String apiKey, String lang, String units) throws IOException;
}
