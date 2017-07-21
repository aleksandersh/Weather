package com.aleksandersh.weather.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AleksanderSh on 21.07.2017.
 */

@Module
public class AppModule {
    private Context mAppContext;

    public AppModule(@NonNull Context appContext) {
        mAppContext = appContext;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mAppContext;
    }
}
