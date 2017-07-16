package com.aleksandersh.weather;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by AleksanderSh on 16.07.2017.
 * <p>
 * Занимается планировкой сервиса.
 */

public class ServiceScheduler {
    private static final String TAG = "ServiceScheduler";

    private Context mContext;

    public ServiceScheduler(Context context) {
        mContext = context;
    }

    /**
     * Перепланировка сервиса на основе настроек приложения.
     */
    public void scheduleService() {
        Intent intent = WeatherService.newIntent(mContext);
        PendingIntent pendingIntent = PendingIntent.getService(
                mContext,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        if (serviceEnabled()) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(),
                    getServiceIntervalInMinutes() * 60 * 1000,
                    pendingIntent);
            Log.d(TAG, "scheduleService: Service scheduled.");
        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            Log.d(TAG, "scheduleService: Service cancelled.");
        }
    }

    private boolean serviceEnabled() {
        String enabledKey = mContext.getString(R.string.pref_service_enabled_key);
        return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(enabledKey, true);
    }

    private long getServiceIntervalInMinutes() {
        String intervalKey = mContext.getString(R.string.pref_service_interval_key);
        String intervalDefault = mContext.getString(R.string.pref_service_interval_default);
        return Long.parseLong(PreferenceManager.getDefaultSharedPreferences(mContext)
                .getString(intervalKey, intervalDefault));
    }
}
