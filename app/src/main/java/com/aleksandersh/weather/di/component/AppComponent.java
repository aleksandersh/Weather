package com.aleksandersh.weather.di.component;

import com.aleksandersh.weather.di.module.AppModule;
import com.aleksandersh.weather.di.module.DomainModule;
import com.aleksandersh.weather.di.module.NetworkModule;
import com.aleksandersh.weather.di.module.ServiceModule;
import com.aleksandersh.weather.fragment.SettingsFragment;
import com.aleksandersh.weather.fragment.WeatherFragment;
import com.aleksandersh.weather.service.WeatherUpdatingJobService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by AleksanderSh on 21.07.2017.
 */

@Component(modules = {AppModule.class, NetworkModule.class, ServiceModule.class, DomainModule.class})
@Singleton
public interface AppComponent {
    void inject(WeatherFragment fragment);

    void inject(SettingsFragment fragment);

    void inject(WeatherUpdatingJobService jobService);
}
