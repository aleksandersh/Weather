package com.aleksandersh.weather.service;

import android.util.Log;

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

    private WeatherProvider mWeatherProvider = WeatherProvider.get(getApplicationContext());

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d(TAG, "onStartJob: " + Thread.currentThread().toString());
        mWeatherProvider.requestWeatherUpdate();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d(TAG, "onStopJob: " + Thread.currentThread().toString());
        return false;
    }
}
