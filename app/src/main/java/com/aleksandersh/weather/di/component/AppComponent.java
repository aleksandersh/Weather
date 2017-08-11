package com.aleksandersh.weather.di.component;


import android.content.Context;

import com.aleksandersh.weather.App;
import com.aleksandersh.weather.di.module.AppModule;
import com.aleksandersh.weather.di.module.DataModule;
import com.aleksandersh.weather.di.module.NetworkModule;
import com.aleksandersh.weather.di.module.ServiceModule;
import com.aleksandersh.weather.features.city.data.dao.CityDao;
import com.aleksandersh.weather.features.city.data.model.CityDtoConverter;
import com.aleksandersh.weather.features.city.di.CityModule;
import com.aleksandersh.weather.features.city.domain.interactor.CityInteractor;
import com.aleksandersh.weather.features.city.domain.service.CitySearchService;
import com.aleksandersh.weather.features.city.presentation.CityChooserFragment;
import com.aleksandersh.weather.features.settings.SettingsFragment;
import com.aleksandersh.weather.features.weather.data.model.WeatherDtoConverter;
import com.aleksandersh.weather.features.weather.di.WeatherModule;
import com.aleksandersh.weather.features.weather.domain.interactor.WeatherInteractor;
import com.aleksandersh.weather.features.weather.domain.service.CurrentWeatherService;
import com.aleksandersh.weather.features.weather.presentation.WeatherFragment;
import com.aleksandersh.weather.features.weather.storage.WeatherDao;
import com.aleksandersh.weather.network.interceptors.ApiKeyInterceptor;
import com.aleksandersh.weather.service.WeatherUpdatingJobService;
import com.aleksandersh.weather.storage.AppDatabase;
import com.aleksandersh.weather.storage.SettingsDao;
import com.aleksandersh.weather.utils.Const;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by AleksanderSh on 21.07.2017.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        ServiceModule.class,
        DataModule.class,
        WeatherModule.class,
        CityModule.class})
public interface AppComponent {

    /**
     * Expose methods
     **/

    // App

    Context getContext();

    CompositeDisposable getCompositeDisposable();

    // Data

    AppDatabase getAppDatabase();

    SettingsDao getSettingsDao();

    // Network

    GsonConverterFactory getGsonConverterFactory();

    RxJava2CallAdapterFactory getRxJava2CallAdapterFactory();

    StethoInterceptor getStethoInterceptor();

    @Named(Const.DI_API_SCOPE_CITY)
    ApiKeyInterceptor getCityApiKeyInterceptor();

    @Named(Const.DI_API_SCOPE_WEATHER)
    ApiKeyInterceptor getWeatherApiKeyInterceptor();

    @Named(Const.DI_API_SCOPE_CITY)
    OkHttpClient getCityHttpClient();

    @Named(Const.DI_API_SCOPE_WEATHER)
    OkHttpClient getWeatherHttpClient();

    @Named(Const.DI_API_SCOPE_CITY)
    Retrofit getCityRetrofit();

    @Named(Const.DI_API_SCOPE_WEATHER)
    Retrofit getWeatherRetrofit();

    // Weather

    CurrentWeatherService getCurrentWeatherService();

    WeatherDao getWeatherDao();

    WeatherDtoConverter getWeatherDtoConverter();

    WeatherInteractor getWeatherInteractor();

    // City

    CitySearchService getCitySearchService();

    CityDao getCityDao();

    CityDtoConverter getCityDtoConverter();

    CityInteractor getCityInteractor();


    /**
     * Inject methods
     **/

    void inject(App app);

    void inject(WeatherFragment fragment);

    void inject(CityChooserFragment cityChooserFragment);

    void inject(SettingsFragment fragment);

    void inject(WeatherUpdatingJobService jobService);

}
