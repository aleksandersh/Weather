package com.aleksandersh.weather.features.weather.di;


import com.aleksandersh.weather.features.city.domain.interactor.CityInteractor;
import com.aleksandersh.weather.features.weather.data.model.WeatherDtoConverter;
import com.aleksandersh.weather.features.weather.domain.interactor.WeatherInteractor;
import com.aleksandersh.weather.features.weather.domain.service.CurrentWeatherService;
import com.aleksandersh.weather.features.weather.presentation.WeatherPresenter;
import com.aleksandersh.weather.features.weather.storage.WeatherDao;
import com.aleksandersh.weather.storage.AppDatabase;
import com.aleksandersh.weather.storage.SettingsDao;
import com.aleksandersh.weather.utils.Const;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


/**
 * Created by Vladimir Kondenko on 02.08.17.
 */

@Module
public class WeatherModule {

    @Provides
    @Singleton
    public WeatherDao provideWeatherDao(AppDatabase database) {
        return database.weatherDao();
    }

    @Provides
    @Singleton
    public WeatherDtoConverter provideDtoConverter() {
        return new WeatherDtoConverter();
    }

    @Provides
    @Singleton
    public CurrentWeatherService provideCurrentWeatherHttpService(@Named(Const.DI_API_SCOPE_WEATHER) Retrofit retrofit) {
        return retrofit.create(CurrentWeatherService.class);
    }

    @Provides
    @Singleton
    public WeatherInteractor provideInteractor(
            CurrentWeatherService currentWeatherService,
            SettingsDao settingsDao,
            WeatherDao weatherDao,
            WeatherDtoConverter dtoConverter) {
        return new WeatherInteractor(currentWeatherService, settingsDao, weatherDao, dtoConverter);
    }


    @Provides
    public WeatherPresenter provideWeatherPresenter(WeatherInteractor weatherInteractor, CityInteractor cityInteractor) {
        return new WeatherPresenter(weatherInteractor, cityInteractor);
    }

}
