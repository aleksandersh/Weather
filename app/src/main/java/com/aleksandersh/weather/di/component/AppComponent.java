package com.aleksandersh.weather.di.component;

import com.aleksandersh.weather.WeatherApplication;
import com.aleksandersh.weather.di.module.AppModule;
import com.aleksandersh.weather.di.module.CityModule;
import com.aleksandersh.weather.di.module.DomainModule;
import com.aleksandersh.weather.di.module.NetworkModule;
import com.aleksandersh.weather.di.module.ServiceModule;
import com.aleksandersh.weather.fragment.SettingsFragment;
import com.aleksandersh.weather.fragment.WeatherFragment;
import com.aleksandersh.weather.service.WeatherUpdatingJobService;
import com.aleksandersh.weather.utils.ApiKeyInterceptor;
import com.aleksandersh.weather.utils.Const;
import com.aleksandersh.weather.utils.PreferencesHelper;

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

    void inject(WeatherApplication app);

    void inject(WeatherFragment fragment);

    void inject(SettingsFragment fragment);

    void inject(WeatherUpdatingJobService jobService);

    CitySubcomponent plusCity(CityModule cityModule);

    PreferencesHelper getPreferencesHelper();

    GsonConverterFactory getGsonConverterFactory();

    @Named(Const.DI_API_SCOPE_CITY)
    ApiKeyInterceptor getApiKeyInterceptor();

    OkHttpClient getHttpClient();

    @Named(Const.DI_API_SCOPE_CITY)
    Retrofit getCityRetrofit();

}
