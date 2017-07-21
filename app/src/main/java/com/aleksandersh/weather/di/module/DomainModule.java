package com.aleksandersh.weather.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.aleksandersh.weather.database.WeatherDao;
import com.aleksandersh.weather.database.WeatherDatabaseDao;
import com.aleksandersh.weather.domain.WeatherManager;
import com.aleksandersh.weather.fragment.SettingsChangeListener;
import com.aleksandersh.weather.network.httpClient.WeatherHttpClient;
import com.aleksandersh.weather.service.WeatherServiceScheduler;
import com.aleksandersh.weather.utils.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AleksanderSh on 21.07.2017.
 */

@Module
public class DomainModule {
    @Provides
    @Singleton
    public PreferencesHelper providePreferencesHelper(Context context) {
        return new PreferencesHelper(context);
    }

    @Provides
    @Singleton
    public WeatherDao provideWeatherDao(Context context) {
        return new WeatherDatabaseDao(context);
    }

    @Provides
    @Singleton
    public WeatherManager provideWeatherManager(
            Context context,
            PreferencesHelper preferencesHelper,
            WeatherHttpClient httpClient,
            WeatherDao weatherDao) {
        return new WeatherManager(context, preferencesHelper, httpClient, weatherDao);
    }

    @Provides
    @Singleton
    public SharedPreferences.OnSharedPreferenceChangeListener provideSharedPreferenceChangeListener(
            Context context,
            WeatherServiceScheduler serviceScheduler) {
        return new SettingsChangeListener(context, serviceScheduler);
    }
}
