package com.aleksandersh.weather.utils;


import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Добавляет API-ключ ко всем отсылаемым запросам.
 */
public class ApiKeyInterceptor implements Interceptor {

    private String apiKey;
    private String paramName;

    @Inject
    public ApiKeyInterceptor(String paramName, String apiKey) {
        this.apiKey = apiKey;
        this.paramName = paramName;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder().addQueryParameter(paramName, apiKey).build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}