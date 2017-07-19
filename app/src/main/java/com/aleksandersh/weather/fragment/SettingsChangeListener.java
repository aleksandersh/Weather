package com.aleksandersh.weather.fragment;

import android.content.Context;
import android.content.SharedPreferences;

import com.aleksandersh.weather.R;
import com.aleksandersh.weather.service.ServiceScheduler;
import com.aleksandersh.weather.service.WeatherServiceScheduler;

/**
 * Created by AleksanderSh on 16.07.2017.
 * <p>
 * Обработчик изменения настроек приложения.
 */

public class SettingsChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {
    private String mServiceEnabledKey;
    private String mServiceIntervalKey;
    private String mServiceIntervalDefault;
    private Context mContext;
    private ServiceScheduler mServiceScheduler;

    public SettingsChangeListener(Context context) {
        mContext = context;

        mServiceEnabledKey = context.getString(R.string.pref_service_enabled_key);
        mServiceIntervalKey = context.getString(R.string.pref_service_interval_key);
        mServiceIntervalDefault = context.getString(R.string.pref_service_interval_default);
        mServiceScheduler = new WeatherServiceScheduler(context);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(mServiceEnabledKey) || key.equals(mServiceIntervalKey)) {
            if (sharedPreferences.getBoolean(mServiceEnabledKey, false)) {
                int interval = Integer.parseInt(sharedPreferences.getString(mServiceIntervalKey,
                        mServiceIntervalDefault)) * 60;
                mServiceScheduler.startService(interval);
            } else {
                if (key.equals(mServiceEnabledKey)) {
                    mServiceScheduler.stopService();
                }
            }
        }
    }
}
