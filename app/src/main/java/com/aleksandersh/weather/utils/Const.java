package com.aleksandersh.weather.utils;

/**
 * Created by Vladimir Kondenko on 22.07.17.
 */

public class Const {


    public static final String BASE_URL_WEATHER = "http://api.openweathermap.org/data/2.5/";
    public static final String BASE_URL_CITY = "http://api.geonames.org/";

    public static final String API_KEY_WEATHER = "7eb42e583dff5e64a589739dd927bd0c";
    public static final String API_KEY_CITY = "vladimirkondenko"; // В сервисе используется имя аккаунта вместо API-ключа

    public static final String API_KEY_PARAM_WEATHER = "APPID";
    public static final String API_KEY_PARAM_CITY = "username";

    public static final String DI_API_SCOPE_WEATHER = "client_weather";
    public static final String DI_API_SCOPE_CITY = "client_city";

    public static final int DEFAULT_SUGGESTIONS_NUMBER = 10;

}
