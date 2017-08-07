package com.aleksandersh.weather.features.city.di;


import com.aleksandersh.weather.di.ScreenScope;
import com.aleksandersh.weather.features.city.presentation.CityChooserFragment;

import dagger.Subcomponent;


/**
 * Created by Vladimir Kondenko on 23.07.17.
 */
@Subcomponent(modules = {CityModule.class})
@ScreenScope
public interface CitySubcomponent {

    void inject(CityChooserFragment fragment);

}
