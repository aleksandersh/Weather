package com.aleksandersh.weather.di.module;


import com.aleksandersh.weather.BuildConfig;
import com.aleksandersh.weather.features.weather.data.WeatherDtoConverter;
import com.aleksandersh.weather.features.weather.data.storable.Weather;
import com.aleksandersh.weather.features.weather.data.transferable.CurrentWeatherDto;
import com.aleksandersh.weather.features.weather.domain.repository.CurrentWeatherRepository;
import com.aleksandersh.weather.features.weather.domain.repository.CurrentWeatherRepositoryImpl;
import com.aleksandersh.weather.features.weather.domain.service.CurrentWeatherHttpService;
import com.aleksandersh.weather.network.interceptors.ApiKeyInterceptor;
import com.aleksandersh.weather.storage.Const;
import com.aleksandersh.weather.storage.DtoConverter;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by AleksanderSh on 21.07.2017.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public GsonConverterFactory provideConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    @Named(Const.DI_API_SCOPE_CITY)
    public ApiKeyInterceptor provideApiKeyInterceptor() {
        return new ApiKeyInterceptor(Const.API_KEY_PARAM_CITY, Const.API_KEY_CITY);
    }

    @Provides
    @Singleton
    public StethoInterceptor provideStethoInterceptor() {
        return new StethoInterceptor();
    }

    @Provides
    @Singleton
    public OkHttpClient provideHttpClient(@Named(Const.DI_API_SCOPE_CITY) ApiKeyInterceptor apiKeyInterceptor, StethoInterceptor stethoInterceptor) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor);
        if (BuildConfig.DEBUG) clientBuilder.addNetworkInterceptor(stethoInterceptor);
        return clientBuilder.build();
    }

    @Provides
    @Singleton
    @Named(Const.DI_API_SCOPE_CITY)
    public Retrofit provideCityRetrofit(OkHttpClient client, GsonConverterFactory converterFactory) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(Const.BASE_URL_CITY)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    @Provides
    @Singleton
    @Named(Const.DI_API_SCOPE_WEATHER)
    public Retrofit provideWeatherRetrofit(GsonConverterFactory converterFactory) {
        return new Retrofit.Builder()
                .baseUrl(Const.BASE_URL_WEATHER)
                .addConverterFactory(converterFactory)
                .build();
    }

    // TODO: Переместить в отдельный Submodule

    @Provides
    @Singleton
    public CurrentWeatherHttpService provideCurrentWeatherHttpService(@Named(Const.DI_API_SCOPE_WEATHER) Retrofit retrofit) {
        return retrofit.create(CurrentWeatherHttpService.class);
    }

    @Provides
    @Singleton
    public DtoConverter<CurrentWeatherDto, Weather> provideDtoConverter() {
        return new WeatherDtoConverter();
    }

    @Provides
    @Singleton
    public CurrentWeatherRepository provideWeatherHttpClient(CurrentWeatherHttpService service, DtoConverter<CurrentWeatherDto, Weather> converter) {
        return new CurrentWeatherRepositoryImpl(service, converter);
    }
}
