package com.aleksandersh.weather.di.component;


import com.aleksandersh.weather.WeatherApplication;
import com.aleksandersh.weather.di.module.AppModule;
import com.aleksandersh.weather.di.module.CityModule;
import com.aleksandersh.weather.di.module.DomainModule;
import com.aleksandersh.weather.di.module.NetworkModule;
import com.aleksandersh.weather.di.module.ServiceModule;
import com.aleksandersh.weather.features.settings.SettingsFragment;
import com.aleksandersh.weather.features.weather.presentation.WeatherFragment;
import com.aleksandersh.weather.network.interceptors.ApiKeyInterceptor;
import com.aleksandersh.weather.service.WeatherUpdatingJobService;
import com.aleksandersh.weather.storage.Const;
import com.aleksandersh.weather.storage.PreferencesHelper;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by AleksanderSh on 21.07.2017.
 */

@Component(modules = {AppModule.class, NetworkModule.class, ServiceModule.class, DomainModule.class})
@Singleton
public interface AppComponent {

    CitySubcomponent plusCity(CityModule cityModule);

    PreferencesHelper getPreferencesHelper();

    GsonConverterFactory getGsonConverterFactory();

    OkHttpClient getHttpClient();

    StethoInterceptor getStethoInterceptor();

    @Named(Const.DI_API_SCOPE_CITY)
    ApiKeyInterceptor getApiKeyInterceptor();

    @Named(Const.DI_API_SCOPE_CITY)
    Retrofit getCityRetrofit();

    void inject(WeatherApplication app);

    void inject(WeatherFragment fragment);

    void inject(SettingsFragment fragment);

    void inject(WeatherUpdatingJobService jobService);

}
