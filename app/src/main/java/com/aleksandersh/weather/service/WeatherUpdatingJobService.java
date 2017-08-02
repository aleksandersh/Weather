package com.aleksandersh.weather.service;


import android.os.AsyncTask;

import com.aleksandersh.weather.WeatherApplication;
import com.aleksandersh.weather.features.weather.presentation.WeatherPresenter;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import javax.inject.Inject;


/**
 * Created by AleksanderSh on 18.07.2017.
 * <p>
 * Сервис для запуска задачи обновления погоды.
 */

public class WeatherUpdatingJobService extends JobService {

    static final String TAG = "WeatherUpdatingJobS";

    @Inject
    WeatherPresenter mWeatherPresenter;

    private UpdatingWeatherTask mBackgroundTask;

    @Override
    public void onCreate() {
        super.onCreate();
        ((WeatherApplication) getApplication()).getAppComponent().inject(this);
    }

    @Override
    public boolean onStartJob(final JobParameters job) {
        mBackgroundTask = new UpdatingWeatherTask(job);
        mBackgroundTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mBackgroundTask != null) {
            mBackgroundTask.cancel(true);
        }
        return true;
    }

    private class UpdatingWeatherTask extends AsyncTask<Void, Void, Void> {

        private JobParameters mJobParameters;

        public UpdatingWeatherTask(JobParameters jobParameters) {
            mJobParameters = jobParameters;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mWeatherPresenter.updateWeather();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            jobFinished(mJobParameters, false);
        }
    }
}
