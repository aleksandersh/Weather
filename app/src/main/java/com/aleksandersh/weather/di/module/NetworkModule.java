package com.aleksandersh.weather.di.module;

import com.aleksandersh.weather.network.dto.currentWeather.CurrentWeatherDto;
import com.aleksandersh.weather.network.httpClient.OpenWeatherMapHttpClient;
import com.aleksandersh.weather.network.httpClient.WeatherHttpClient;
import com.aleksandersh.weather.network.httpClient.converter.DtoConverter;
import com.aleksandersh.weather.network.httpClient.converter.OpenWeatherMapDtoConverter;
import com.aleksandersh.weather.network.httpService.CityHttpService;
import com.aleksandersh.weather.network.httpService.CurrentWeatherHttpService;
import com.aleksandersh.weather.utils.ApiKeyInterceptor;
import com.aleksandersh.weather.utils.Const;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AleksanderSh on 21.07.2017.
 */

@Module
public class NetworkModule {

    @Provides
    @Named(Const.DI_API_SCOPE_WEATHER)
    public String provideWeatherApuBaseUrl() {
        return Const.BASE_URL_WEATHER;
    }

    @Provides
    @Named(Const.DI_API_SCOPE_CITY)
    public String provideCityApiBaseUrl() {
        return Const.BASE_URL_CITY;
    }

    @Provides
    @Singleton
    public GsonConverterFactory provideConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    @Named(Const.DI_API_SCOPE_CITY)
    public Interceptor provideApiKeyInterceptor() {
        return new ApiKeyInterceptor(Const.API_KEY_PARAM_CITY, Const.API_KEY_CITY);
    }

    @Provides
    @Singleton
    public OkHttpClient provideHttpClient(ApiKeyInterceptor apiKeyInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .build();
    }

    @Provides
    @Singleton
    @Named(Const.DI_API_SCOPE_CITY)
    public Retrofit provideCityRetrofit(@Named(Const.DI_API_SCOPE_CITY) String baseUrl, OkHttpClient client, GsonConverterFactory converterFactory) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(converterFactory)
                .build();

    }

    @Provides
    @Singleton
    @Named(Const.DI_API_SCOPE_WEATHER)
    public Retrofit provideWeatherRetrofit(@Named(Const.DI_API_SCOPE_WEATHER) String baseUrl, GsonConverterFactory converterFactory) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .build();
    }


    @Provides
    @Singleton
    public CurrentWeatherHttpService provideCurrentWeatherHttpService(@Named(Const.DI_API_SCOPE_WEATHER) Retrofit retrofit) {
        return retrofit.create(CurrentWeatherHttpService.class);
    }

    @Provides
    @Singleton
    public CityHttpService provideCityHttpService(@Named(Const.DI_API_SCOPE_CITY) Retrofit retrofit) {
        return retrofit.create(CityHttpService.class);
    }

    @Provides
    @Singleton
    public DtoConverter<CurrentWeatherDto> provideDtoConverter() {
        return new OpenWeatherMapDtoConverter();
    }

    @Provides
    @Singleton
    public WeatherHttpClient provideWeatherHttpClient(CurrentWeatherHttpService service,
                                                      DtoConverter<CurrentWeatherDto> converter) {
        return new OpenWeatherMapHttpClient(service, converter);
    }
}
