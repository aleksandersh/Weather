package com.aleksandersh.weather.di.module;


import android.content.Context;
import android.content.SharedPreferences;

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
public class DataModule {

    @Provides
    @Singleton
    public PreferencesHelper providePreferencesHelper(Context context) {
        return new PreferencesHelper(context);
    }

    @Provides
    @Singleton
    public SharedPreferences.OnSharedPreferenceChangeListener provideSharedPreferenceChangeListener(
            Context context,
            WeatherServiceScheduler serviceScheduler) {
        return new SettingsChangeListener(context, serviceScheduler);
    }
}
