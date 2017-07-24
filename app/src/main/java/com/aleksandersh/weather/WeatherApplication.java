package com.aleksandersh.weather;

import android.app.Application;

import com.aleksandersh.weather.di.component.AppComponent;
import com.aleksandersh.weather.di.component.CitySubcomponent;
import com.aleksandersh.weather.di.component.DaggerAppComponent;
import com.aleksandersh.weather.di.module.AppModule;
import com.aleksandersh.weather.di.module.CityModule;
import com.aleksandersh.weather.di.module.DomainModule;
import com.aleksandersh.weather.di.module.NetworkModule;
import com.aleksandersh.weather.di.module.ServiceModule;

/**
 * Created by AleksanderSh on 21.07.2017.
 * <p>
 * Внедрен компонент Dagger.
 */

public class WeatherApplication extends Application {

    private static AppComponent mAppComponent;

    private static CitySubcomponent citySubcomponent = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = buildAppComponent();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static CitySubcomponent plus(CityView view) {
        if (citySubcomponent == null) citySubcomponent = mAppComponent.plusCity(new CityModule(view));
        return citySubcomponent;
    }

    protected AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .domainModule(new DomainModule())
                .networkModule(new NetworkModule())
                .serviceModule(new ServiceModule())
                .build();
    }
}
