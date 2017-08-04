package com.aleksandersh.weather.features.weather.di;


import android.content.Context;

import com.aleksandersh.weather.features.weather.data.model.WeatherDtoConverter;
import com.aleksandersh.weather.features.weather.data.model.storable.Weather;
import com.aleksandersh.weather.features.weather.data.model.transferable.CurrentWeatherDto;
import com.aleksandersh.weather.features.weather.data.repository.CurrentWeatherRepositoryImpl;
import com.aleksandersh.weather.features.weather.domain.repository.CurrentWeatherRepository;
import com.aleksandersh.weather.features.weather.domain.service.CurrentWeatherHttpService;
import com.aleksandersh.weather.features.weather.presentation.WeatherPresenter;
import com.aleksandersh.weather.features.weather.storage.WeatherDao;
import com.aleksandersh.weather.features.weather.storage.WeatherDatabaseDao;
import com.aleksandersh.weather.storage.Const;
import com.aleksandersh.weather.storage.DtoConverter;
import com.aleksandersh.weather.storage.PreferencesHelper;

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

//    private WeatherView view;

//    public WeatherModule(WeatherView view) {
//        this.view = view;
//    }

    @Provides
    @Singleton
    public WeatherDao provideWeatherDao(Context context) {
        return new WeatherDatabaseDao(context);
    }

    @Provides
    @Singleton
    public DtoConverter<CurrentWeatherDto, Weather> provideDtoConverter() {
        return new WeatherDtoConverter();
    }

    @Provides
    @Singleton
    public CurrentWeatherHttpService provideCurrentWeatherHttpService(@Named(Const.DI_API_SCOPE_WEATHER) Retrofit retrofit) {
        return retrofit.create(CurrentWeatherHttpService.class);
    }

    @Provides
    @Singleton
    public CurrentWeatherRepository provideWeatherRepository(CurrentWeatherHttpService service, DtoConverter<CurrentWeatherDto, Weather> converter) {
        return new CurrentWeatherRepositoryImpl(service, converter);
    }

    @Provides
    @Singleton
    public WeatherPresenter provideWeatherPresenter(
            Context context,
            PreferencesHelper preferencesHelper,
            CurrentWeatherRepository httpClient,
            WeatherDao weatherDao) {
        return new WeatherPresenter(context, preferencesHelper, httpClient, weatherDao);
    }

}
