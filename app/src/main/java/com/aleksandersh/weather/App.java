package com.aleksandersh.weather;


import android.app.Application;

import com.aleksandersh.weather.di.component.AppComponent;
import com.aleksandersh.weather.di.component.DaggerAppComponent;
import com.aleksandersh.weather.di.module.AppModule;
import com.aleksandersh.weather.di.module.DataModule;
import com.aleksandersh.weather.di.module.NetworkModule;
import com.aleksandersh.weather.di.module.ServiceModule;
import com.aleksandersh.weather.features.city.di.CityModule;
import com.aleksandersh.weather.features.city.di.CitySubcomponent;
import com.aleksandersh.weather.features.weather.di.WeatherComponent;
import com.aleksandersh.weather.features.weather.di.WeatherModule;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;


/**
 * Created by AleksanderSh on 21.07.2017.
 * <p>
 * Внедрен компонент Dagger.
 */

public class App extends Application {

    private static AppComponent appComponent;

    private static CitySubcomponent citySubcomponent = null;

    private static WeatherComponent weatherSubcomponent = null;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) return;
        if (com.aleksandersh.weather.BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
            LeakCanary.install(this);
        }
        appComponent = buildAppComponent();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule())
                .networkModule(new NetworkModule())
                .serviceModule(new ServiceModule())
                .weatherModule(new WeatherModule())
                .
                .build();
    }

    /*

    public static WeatherComponent plus(WeatherView view) {
        if (weatherSubcomponent == null)
            weatherSubcomponent = appComponent.plusWeather(new WeatherModule(view));
        return weatherSubcomponent;
    }

    public static void clearWeatherSubcomponent() {
        weatherSubcomponent = null;
    }

    */

    public static CitySubcomponent plus() {
        if (citySubcomponent == null)
            citySubcomponent = appComponent.plusCity(new CityModule());
        return citySubcomponent;
    }

    public static void clearCitySubcomponent() {
        citySubcomponent = null;
    }

}
