package com.aleksandersh.weather.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aleksandersh.weather.ServiceScheduler;

public class WeatherBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "WeatherBrReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        ServiceScheduler scheduler = new ServiceScheduler(context);
        scheduler.scheduleService();
        Log.d(TAG, "onReceive: Service started.");
    }
}
