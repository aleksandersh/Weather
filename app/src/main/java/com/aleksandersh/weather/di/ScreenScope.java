package com.aleksandersh.weather.di;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;


/**
 * Created by Vladimir Kondenko on 24.07.17.
 */

@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ScreenScope {

}
