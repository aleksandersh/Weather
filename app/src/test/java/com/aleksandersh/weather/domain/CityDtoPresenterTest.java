package com.aleksandersh.weather.domain;


import com.aleksandersh.weather.features.city.data.model.transferable.CityDto;
import com.aleksandersh.weather.features.city.domain.interactor.CityInteractor;
import com.aleksandersh.weather.features.city.presentation.CityPresenter;
import com.aleksandersh.weather.features.city.presentation.CityView;
import com.aleksandersh.weather.storage.SettingsDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Vladimir Kondenko on 30.07.17.
 */
public class CityDtoPresenterTest {

    private CityView cityView;
    private CityInteractor client;
    private SettingsDao preferencesHelper;
    private CityPresenter cityPresenter;

    @Before
    public void setUp() throws Exception {
        cityView = mock(CityView.class);
        client = mock(CityInteractor.class);
        preferencesHelper = mock(SettingsDao.class);
        cityPresenter = new CityPresenter(client);
        cityPresenter.onAttach(cityView);
    }

    @After
    public void tearDown() throws Exception {
        cityPresenter.onDetach();
    }

    @Test
    public void onQueryUpdated() throws Exception {
        when(client.getCity(anyString())).thenReturn(Single.just(new ArrayList<>()));
        cityPresenter.onSearchQueryUpdated("Moscow");
        verify(cityView).showSuggestions(anyList());
    }

    @Test
    public void onCitySelected() throws Exception {
        CityDto city = mock(CityDto.class);
        cityPresenter.onSuggestionClick(city);
        verify(preferencesHelper).saveCity(city);
    }

}