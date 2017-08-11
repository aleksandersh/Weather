package com.aleksandersh.weather.features.weather.di;


import com.aleksandersh.weather.features.city.data.dao.CityDao;
import com.aleksandersh.weather.features.city.domain.interactor.CityInteractor;
import com.aleksandersh.weather.features.weather.data.model.WeatherDtoConverter;
import com.aleksandersh.weather.features.weather.data.model.storable.Weather;
import com.aleksandersh.weather.features.weather.data.model.transferable.CurrentWeatherDto;
import com.aleksandersh.weather.features.weather.domain.interactor.WeatherInteractor;
import com.aleksandersh.weather.features.weather.domain.service.CurrentWeatherService;
import com.aleksandersh.weather.features.weather.presentation.WeatherPresenter;
import com.aleksandersh.weather.features.weather.storage.WeatherDao;
import com.aleksandersh.weather.storage.AppDatabase;
import com.aleksandersh.weather.storage.DtoConverter;
import com.aleksandersh.weather.storage.SettingsDao;
import com.aleksandersh.weather.utils.Const;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
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
    public DtoConverter<CurrentWeatherDto, Weather> provideDtoConverter() {
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
            CityDao cityDao,
            DtoConverter<CurrentWeatherDto, Weather> dtoConverter) {
        return new WeatherInteractor(currentWeatherService, settingsDao, weatherDao, cityDao, dtoConverter);
    }


    @Provides
    @Singleton
    public WeatherPresenter provideWeatherPresenter(WeatherInteractor weatherInteractor, CityInteractor cityInteractor, CompositeDisposable disposable) {
        return new WeatherPresenter(weatherInteractor, cityInteractor,  disposable);
    }

}
