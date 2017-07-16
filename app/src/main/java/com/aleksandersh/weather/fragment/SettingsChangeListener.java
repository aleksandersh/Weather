package com.aleksandersh.weather.fragment;

import android.content.Context;
import android.content.SharedPreferences;

import com.aleksandersh.weather.R;
import com.aleksandersh.weather.ServiceScheduler;

/**
 * Created by AleksanderSh on 16.07.2017.
 */

public class SettingsChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {
    private String mServiceEnabledKey;
    private String mServiceIntervalKey;
    private Context mContext;
    private ServiceScheduler mServiceScheduler;

    public SettingsChangeListener(Context context) {
        mContext = context;
        mServiceEnabledKey = context.getString(R.string.pref_service_enabled_key);
        mServiceIntervalKey = context.getString(R.string.pref_service_interval_key);
        mServiceScheduler = new ServiceScheduler(context);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(mServiceEnabledKey) || key.equals(mServiceIntervalKey)) {
            mServiceScheduler.scheduleService();
        }
    }
}
