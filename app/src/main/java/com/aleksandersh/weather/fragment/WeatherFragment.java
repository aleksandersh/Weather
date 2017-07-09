package com.aleksandersh.weather.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aleksandersh.weather.R;


/**
 * Фрагмент, содержащий данные о погоде.
 */
public class WeatherFragment extends Fragment {
    /**
     * Создает новый экземпляр фрагмента {@link WeatherFragment} и задает аргументы.
     *
     * @return Новый экземпляр
     */
    public static WeatherFragment newInstance() {

        Bundle args = new Bundle();

        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public WeatherFragment() {
        // Пустой конструктор
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }
}
