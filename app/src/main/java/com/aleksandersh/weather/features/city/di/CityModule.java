package com.aleksandersh.weather.features.city.di;


import com.aleksandersh.weather.di.ScreenScope;
import com.aleksandersh.weather.features.city.data.dao.CityDao;
import com.aleksandersh.weather.features.city.data.model.CityDtoConverter;
import com.aleksandersh.weather.features.city.data.repository.CityRepositoryImpl;
import com.aleksandersh.weather.features.city.domain.interactor.CityInteractor;
import com.aleksandersh.weather.features.city.domain.interactor.CityInteractorImpl;
import com.aleksandersh.weather.features.city.domain.repository.CityRepository;
import com.aleksandersh.weather.features.city.domain.service.CityHttpService;
import com.aleksandersh.weather.features.city.presentation.CityPresenter;
import com.aleksandersh.weather.storage.AppDatabase;
import com.aleksandersh.weather.utils.Const;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


/**
 * Created by Vladimir Kondenko on 23.07.17.
 */
@Module
public class CityModule {

    @Provides
    @ScreenScope
    public CityDtoConverter provideCityDtoConverter() {
        return new CityDtoConverter();
    }

    @Provides
    @ScreenScope
    public CityHttpService provideService(@Named(Const.DI_API_SCOPE_CITY) Retrofit retrofit) {
        return retrofit.create(CityHttpService.class);
    }

    @Provides
    @ScreenScope
    public CityDao provideCityDao(AppDatabase database) {
        return database.cityDao();
    }

    @Provides
    @ScreenScope
    public CityRepository provideClient(CityHttpService service, CityDao dao, CityDtoConverter dtoConverter) {
        return new CityRepositoryImpl(service, dao, dtoConverter);
    }

    @Provides
    @ScreenScope
    public CityInteractor provideCityInteractor(CityRepository repository) {
        return new CityInteractorImpl(repository);
    }


    @Provides
    @ScreenScope
    public CityPresenter provideCityPresenter(CityInteractor interactor) {
        return new CityPresenter(interactor);
    }

}
