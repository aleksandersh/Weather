package com.aleksandersh.weather;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class WeatherService extends Service {
    private static final String TAG = "WeatherService";

    private WeatherProvider mWeatherProvider;

    public static Intent newIntent(Context context) {
        return new Intent(context, WeatherService.class);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: 1");
        mWeatherProvider = WeatherProvider.get(getApplicationContext());
        Log.d(TAG, "onCreate: 2");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: start");
        mWeatherProvider.requestWeatherUpdate();
        Log.d(TAG, "onStartCommand: finish");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
