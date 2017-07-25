package com.aleksandersh.weather.di.module;

import com.aleksandersh.weather.CityView;
import com.aleksandersh.weather.di.ScreenScope;
import com.aleksandersh.weather.domain.CityManager;
import com.aleksandersh.weather.network.httpClient.CityHttpClient;
import com.aleksandersh.weather.network.httpClient.GeoNamesHttpClient;
import com.aleksandersh.weather.network.httpClient.converter.CityDtoConverter;
import com.aleksandersh.weather.network.httpService.CityHttpService;
import com.aleksandersh.weather.utils.Const;
import com.aleksandersh.weather.utils.PreferencesHelper;

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
    public CityHttpClient provideClient(CityHttpService service) {
        return new GeoNamesHttpClient(service);
    }

    @Provides
    @ScreenScope
    public CityManager provideCityManager(CityView view, CityHttpClient client, CityDtoConverter dtoConverter, PreferencesHelper preferencesHelper) {
        return new CityManager(view, client, dtoConverter, preferencesHelper);
    }

}
