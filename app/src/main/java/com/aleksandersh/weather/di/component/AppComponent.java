package com.aleksandersh.weather.di.component;


import com.aleksandersh.weather.App;
import com.aleksandersh.weather.di.module.AppModule;
import com.aleksandersh.weather.di.module.DataModule;
import com.aleksandersh.weather.di.module.NetworkModule;
import com.aleksandersh.weather.di.module.ServiceModule;
import com.aleksandersh.weather.features.city.di.CityModule;
import com.aleksandersh.weather.features.city.presentation.CityChooserFragment;
import com.aleksandersh.weather.features.settings.SettingsFragment;
import com.aleksandersh.weather.features.weather.di.WeatherModule;
import com.aleksandersh.weather.features.weather.presentation.WeatherFragment;
import com.aleksandersh.weather.service.WeatherUpdatingJobService;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by AleksanderSh on 21.07.2017.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        ServiceModule.class,
        DataModule.class,
        WeatherModule.class,
        CityModule.class})
public interface AppComponent {

    void inject(App app);

    void inject(WeatherFragment fragment);

    void inject(CityChooserFragment cityChooserFragment);

    void inject(SettingsFragment fragment);

    void inject(WeatherUpdatingJobService jobService);

}
