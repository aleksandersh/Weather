package com.aleksandersh.weather;

import android.app.Application;

import com.aleksandersh.weather.di.component.AppComponent;
import com.aleksandersh.weather.di.component.DaggerAppComponent;
import com.aleksandersh.weather.di.module.AppModule;
import com.aleksandersh.weather.di.module.DomainModule;
import com.aleksandersh.weather.di.module.NetworkModule;
import com.aleksandersh.weather.di.module.ServiceModule;

/**
 * Created by AleksanderSh on 21.07.2017.
 * <p>
 * Внедрен компонент Dagger.
 */

public class WeatherApplication extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = buildAppComponent();
    }

    protected AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .domainModule(new DomainModule())
                .networkModule(new NetworkModule("http://api.openweathermap.org/data/2.5/"))
                .serviceModule(new ServiceModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
