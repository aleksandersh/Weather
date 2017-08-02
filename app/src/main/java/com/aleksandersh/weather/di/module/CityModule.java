package com.aleksandersh.weather.di.module;


import com.aleksandersh.weather.di.ScreenScope;
import com.aleksandersh.weather.features.city.data.CityDtoConverter;
import com.aleksandersh.weather.features.city.domain.repository.CityRepository;
import com.aleksandersh.weather.features.city.domain.repository.CityRepositoryImpl;
import com.aleksandersh.weather.features.city.domain.service.CityHttpService;
import com.aleksandersh.weather.features.city.presentation.CityPresenter;
import com.aleksandersh.weather.features.city.presentation.CityView;
import com.aleksandersh.weather.storage.Const;
import com.aleksandersh.weather.storage.PreferencesHelper;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


/**
 * Created by Vladimir Kondenko on 23.07.17.
 */
@Module
public class CityModule {

    private CityView cityView;

    public CityModule(CityView cityView) {
        this.cityView = cityView;
    }

    @Provides
    @ScreenScope
    public CityDtoConverter provideCityDtoConverter() {
        return new CityDtoConverter();
    }

    @Provides
    @ScreenScope
    public CityView provideCityDialogView() {
        return cityView;
    }

    @Provides
    @ScreenScope
    public CityHttpService provideService(@Named(Const.DI_API_SCOPE_CITY) Retrofit retrofit) {
        return retrofit.create(CityHttpService.class);
    }

    @Provides
    @ScreenScope
    public CityRepository provideClient(CityHttpService service) {
        return new CityRepositoryImpl(service);
    }

    @Provides
    @ScreenScope
    public CityPresenter provideCityManager(CityView view, CityRepository client, CityDtoConverter dtoConverter, PreferencesHelper preferencesHelper) {
        return new CityPresenter(client, preferencesHelper);
    }

}
