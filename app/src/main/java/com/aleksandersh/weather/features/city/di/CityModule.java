package com.aleksandersh.weather.features.city.di;


import com.aleksandersh.weather.di.ScreenScope;
import com.aleksandersh.weather.features.city.data.dao.CityDao;
import com.aleksandersh.weather.features.city.data.model.CityDtoConverter;
import com.aleksandersh.weather.features.city.data.repository.CityInteractorImpl;
import com.aleksandersh.weather.features.city.domain.interactor.CityInteractor;
import com.aleksandersh.weather.features.city.domain.service.CityHttpService;
import com.aleksandersh.weather.features.city.presentation.CityPresenter;
import com.aleksandersh.weather.utils.Const;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
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
    public CityInteractor provideInteractor(CityHttpService service, CityDao dao, CityDtoConverter dtoConverter) {
        return new CityInteractorImpl(service, dao, dtoConverter);
    }

    @Provides
    @ScreenScope
    public CityPresenter provideCityPresenter(CityInteractor interactor, CompositeDisposable compositeDisposable) {
        return new CityPresenter(interactor, compositeDisposable);
    }

}
