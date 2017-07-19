package com.aleksandersh.weather.service;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by AleksanderSh on 18.07.2017.
 * <p>
 * Реализация планировщика задачи по обновлению погоды с помощью Firebase Job Dispatcher.
 */

public class WeatherServiceScheduler implements ServiceScheduler {
    private static final String TAG = "WeatherServiceScheduler";

    private Context mContext;
    private FirebaseJobDispatcher mDispatcher;

    public WeatherServiceScheduler(Context context) {
        mContext = context;
        mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
    }

    /**
     * Запускает сервис с заданным интервалом.
     *
     * @param interval Интервал (в секундах).
     */
    @Override
    public void startService(int interval) {
        Job job = mDispatcher.newJobBuilder()
                .setService(WeatherUpdatingJobService.class)
                .setTag(WeatherUpdatingJobService.TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setReplaceCurrent(true)
                .setTrigger(Trigger.executionWindow(interval, interval))
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();

        mDispatcher.mustSchedule(job);
    }

    @Override
    public void stopService() {
        mDispatcher.cancel(WeatherUpdatingJobService.TAG);
    }
}
