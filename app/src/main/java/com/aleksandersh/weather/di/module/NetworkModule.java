package com.aleksandersh.weather.di.module;

import com.aleksandersh.weather.network.dto.currentWeather.CurrentWeatherDto;
import com.aleksandersh.weather.network.httpClient.OpenWeatherMapHttpClient;
import com.aleksandersh.weather.network.httpClient.WeatherHttpClient;
import com.aleksandersh.weather.network.httpClient.converter.DtoConverter;
import com.aleksandersh.weather.network.httpClient.converter.OpenWeatherMapDtoConverter;
import com.aleksandersh.weather.network.httpService.CurrentWeatherHttpService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AleksanderSh on 21.07.2017.
 */

@Module
public class NetworkModule {
    private String mBaseUrl;

    public NetworkModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    public WeatherHttpClient provideWeatherHttpClient(CurrentWeatherHttpService service,
                                                      DtoConverter<CurrentWeatherDto> converter) {
        return new OpenWeatherMapHttpClient(service, converter);
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public CurrentWeatherHttpService provideCurrentWeatherHttpService(Retrofit retrofit) {
        return retrofit.create(CurrentWeatherHttpService.class);
    }

    @Provides
    @Singleton
    public DtoConverter<CurrentWeatherDto> provideDtoConverter() {
        return new OpenWeatherMapDtoConverter();
    }
}
