package com.aleksandersh.weather.fragment;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.aleksandersh.weather.R;

/**
 * Фрагмент, содержащий настройки приложения.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    /**
     * Создает новый экземпляр {@link SettingsFragment}.
     *
     * @return Новый экземпляр.
     */
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
