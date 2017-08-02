package com.aleksandersh.weather.di.module;


import android.content.Context;
import android.content.SharedPreferences;

import com.aleksandersh.weather.features.weather.domain.repository.CurrentWeatherRepository;
import com.aleksandersh.weather.features.weather.presentation.WeatherPresenter;
import com.aleksandersh.weather.features.weather.storage.WeatherDao;
import com.aleksandersh.weather.features.weather.storage.WeatherDatabaseDao;
import com.aleksandersh.weather.service.WeatherServiceScheduler;
import com.aleksandersh.weather.storage.PreferencesHelper;
import com.aleksandersh.weather.storage.SettingsChangeListener;

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
    public WeatherPresenter provideWeatherManager(
            Context context,
            PreferencesHelper preferencesHelper,
            CurrentWeatherRepository httpClient,
            WeatherDao weatherDao) {
        return new WeatherPresenter(context, preferencesHelper, httpClient, weatherDao);
    }

    @Provides
    @Singleton
    public SharedPreferences.OnSharedPreferenceChangeListener provideSharedPreferenceChangeListener(
            Context context,
            WeatherServiceScheduler serviceScheduler) {
        return new SettingsChangeListener(context, serviceScheduler);
    }
}
