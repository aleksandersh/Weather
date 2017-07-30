package com.aleksandersh.weather.domain;

import com.aleksandersh.weather.CityView;
import com.aleksandersh.weather.network.dto.city.CityDto;
import com.aleksandersh.weather.network.httpClient.CityHttpClient;
import com.aleksandersh.weather.utils.PreferencesHelper;

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
public class CityManagerTest {

    private CityView cityView;
    private CityHttpClient client;
    private PreferencesHelper preferencesHelper;
    private CityManager cityManager;

    @Before
    public void setUp() throws Exception {
        cityView = mock(CityView.class);
        client = mock(CityHttpClient.class);
        preferencesHelper = mock(PreferencesHelper.class);
        cityManager = new CityManager(client, preferencesHelper);
        cityManager.onAttach(cityView);
    }

    @After
    public void tearDown() throws Exception {
        cityManager.onDetach();
    }

    @Test
    public void onQueryUpdated() throws Exception {
        when(client.getCity(anyString())).thenReturn(Single.just(new ArrayList<>()));
        cityManager.onQueryUpdated("Moscow");
        verify(cityView).updateData(anyList());
    }

    @Test
    public void onCitySelected() throws Exception {
        CityDto city = mock(CityDto.class);
        cityManager.onCitySelected(city);
        verify(preferencesHelper).saveCity(city);
    }

}