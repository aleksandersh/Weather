package com.aleksandersh.weather.di.component;

import com.aleksandersh.weather.di.ScreenScope;
import com.aleksandersh.weather.di.module.CityModule;
import com.aleksandersh.weather.ui.CityDialogFragment;

import dagger.Subcomponent;

/**
 * Created by Vladimir Kondenko on 23.07.17.
 */
@Subcomponent(modules = {CityModule.class})
@ScreenScope
public interface CitySubcomponent {

    void inject(CityDialogFragment fragment);

}
