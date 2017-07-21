package com.aleksandersh.weather.service;

import android.os.AsyncTask;

import com.aleksandersh.weather.domain.WeatherManager;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by AleksanderSh on 18.07.2017.
 * <p>
 * Сервис для запуска задачи обновления погоды.
 */

public class WeatherUpdatingJobService extends JobService {
    static final String TAG = "WeatherUpdatingJobS";

    private WeatherManager mWeatherManager;
    private UpdatingWeatherTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        if (mWeatherManager == null) {
            mWeatherManager = new WeatherManager(getApplicationContext());
        }

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

    public class UpdatingWeatherTask extends AsyncTask<Void, Void, Void> {
        private JobParameters mJobParameters;

        public UpdatingWeatherTask(JobParameters jobParameters) {
            mJobParameters = jobParameters;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mWeatherManager.updateWeather();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            jobFinished(mJobParameters, false);
        }
    }
}
