package com.aleksandersh.weather.service;

import com.aleksandersh.weather.WeatherProvider;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by AleksanderSh on 18.07.2017.
 * <p>
 * Сервис для запуска задачи обновления погоды.
 */

public class WeatherUpdatingJobService extends JobService {
    static final String TAG = "WeatherUpdatingJobS";

    private WeatherProvider mWeatherProvider;

    @Override
    public boolean onStartJob(JobParameters job) {
        if (mWeatherProvider == null) {
            mWeatherProvider = WeatherProvider.get(getApplicationContext());
        }

        mWeatherProvider.requestWeatherUpdate();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }
}
