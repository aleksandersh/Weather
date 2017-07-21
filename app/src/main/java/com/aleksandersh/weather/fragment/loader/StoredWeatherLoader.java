package com.aleksandersh.weather.fragment.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.aleksandersh.weather.database.WeatherDao;
import com.aleksandersh.weather.model.WeatherStorableState;

/**
 * Created by AleksanderSh on 20.07.2017.
 * <p>
 * Класс наследуется от {@link AsyncTaskLoader}, позволяет загружать погоду из хранилища
 * и передавать инициатору.
 */

public class StoredWeatherLoader extends AsyncTaskLoader<WeatherStorableState> {
    private WeatherDao mWeatherDao;
    private long mCityId;

    public StoredWeatherLoader(Context context, long cityId) {
        super(context);

        mCityId = cityId;
        mWeatherDao = new WeatherDao(context);
    }

    @Override
    public WeatherStorableState loadInBackground() {
        return mWeatherDao.getWeatherByCityId(mCityId);
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
