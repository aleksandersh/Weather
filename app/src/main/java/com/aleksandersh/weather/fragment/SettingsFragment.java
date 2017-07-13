package com.aleksandersh.weather.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aleksandersh.weather.R;


/**
 * Фрагмент, содержащий настройки приложения.
 */
public class SettingsFragment extends Fragment {
    /**
     * Создает новый экземпляр {@link SettingsFragment} и задает аргументы.
     *
     * @return Новый экземпляр
     */
    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public SettingsFragment() {
        // Пустой конструктор
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}
