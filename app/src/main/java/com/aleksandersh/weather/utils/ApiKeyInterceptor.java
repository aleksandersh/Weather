package com.aleksandersh.weather.utils;


import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Добавляет API-ключ ко всем отсылаемым запросам.
 */
public class ApiKeyInterceptor implements Interceptor {

    private static final String TAG = "ApiKeyInterceptor";

    private String apiKey;
    private String paramName;

    public ApiKeyInterceptor(String paramName, String apiKey) {
        this.apiKey = apiKey;
        this.paramName = paramName;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder().addQueryParameter(paramName, apiKey).build();
        request = request.newBuilder().url(url).build();
        Response response = chain.proceed(request);
        return response;
    }
}