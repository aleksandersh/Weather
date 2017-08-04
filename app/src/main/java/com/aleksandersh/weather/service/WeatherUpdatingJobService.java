package com.aleksandersh.weather.service;


import android.os.AsyncTask;

import com.aleksandersh.weather.App;
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
    WeatherPresenter weatherPresenter;

    private UpdatingWeatherTask backgroundTask;

    @Override
    public void onCreate() {
        super.onCreate();
        App.getAppComponent().inject(this);
    }

    @Override
    public boolean onStartJob(final JobParameters job) {
        backgroundTask = new UpdatingWeatherTask(job);
        backgroundTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (backgroundTask != null) {
            backgroundTask.cancel(true);
        }
        return true;
    }

    private class UpdatingWeatherTask extends AsyncTask<Void, Void, Void> {

        private JobParameters jobParameters;

        public UpdatingWeatherTask(JobParameters jobParameters) {
           this.jobParameters = jobParameters;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            weatherPresenter.updateWeather();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            jobFinished(jobParameters, false);
        }
    }
}
