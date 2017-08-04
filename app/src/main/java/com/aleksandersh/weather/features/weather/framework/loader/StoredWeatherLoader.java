package com.aleksandersh.weather.features.weather.framework.loader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.aleksandersh.weather.features.weather.data.model.storable.WeatherStorableState;
import com.aleksandersh.weather.features.weather.storage.WeatherDao;


/**
 * Created by AleksanderSh on 20.07.2017.
 * <p>
 * Класс наследуется от {@link AsyncTaskLoader}, позволяет загружать погоду из хранилища
 * и передавать инициатору.
 */

public class StoredWeatherLoader extends AsyncTaskLoader<WeatherStorableState> {

    private WeatherDao weatherDao;

    private long cityId;

    public StoredWeatherLoader(Context context, WeatherDao weatherDao, long cityId) {
        super(context);
        this.cityId = cityId;
        this.weatherDao = weatherDao;
    }

    @Override
    public WeatherStorableState loadInBackground() {
        return weatherDao.getWeatherByCityId(cityId);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

}
