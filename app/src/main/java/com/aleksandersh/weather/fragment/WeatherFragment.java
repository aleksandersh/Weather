package com.aleksandersh.weather.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aleksandersh.weather.R;
import com.aleksandersh.weather.WeatherProvider;
import com.aleksandersh.weather.model.Weather;
import com.aleksandersh.weather.storage.WeatherStorableState;

import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Фрагмент, содержащий данные о погоде.
 */
public class WeatherFragment extends Fragment implements WeatherProvider.WeatherSubscriber {
    private static final String TAG = "WeatherFragment";
    private static final long MOSCOW_ID = 524901;

    private WeatherProvider mWeatherProvider;
    private Unbinder mUnbinder;
    private boolean mFilled;
    private Date mLastUpdateDate;

    @BindView(R.id.temperature_text_view)
    TextView mTemperatureTextView;
    @BindView(R.id.weather_condition_text_view)
    TextView mConditionTextView;
    @BindView(R.id.pressure_text_view)
    TextView mPressureTextView;
    @BindView(R.id.humidity_text_view)
    TextView mHumidityTextView;
    @BindView(R.id.cloudiness_text_view)
    TextView mCloudinessTextView;

    /**
     * Создает новый экземпляр фрагмента {@link WeatherFragment}.
     *
     * @return Новый экземпляр.
     */
    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeatherProvider = WeatherProvider.get(getContext().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        mUnbinder = ButterKnife.bind(this, view);
        mFilled = false;

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mUnbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWeatherProvider.subscribe(this);
        if (!mFilled) {
            mWeatherProvider.requestStoredState();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mWeatherProvider.unsubscribe(this);
    }

    @Override
    public void onWeatherUpdated(WeatherStorableState storableState) {
        updateUi(storableState.getWeather());
        mLastUpdateDate = storableState.getDate();
        mFilled = true;
    }

    @Override
    public void onStoredStateLoaded(WeatherStorableState storableState) {
        if (!mFilled) {
            updateUi(storableState.getWeather());
            mFilled = true;
            mLastUpdateDate = storableState.getDate();
        }
    }

    @Override
    public void onErrorUpdating(String errorDescription) {
        // TODO: 16.07.2017 Вывести ошибку
    }

    @Override
    public void onErrorStoredStateLoading() {
        if (!mFilled) {
            mWeatherProvider.requestWeather(MOSCOW_ID);
        }
    }

    private void updateUi(Weather weather) {
        mTemperatureTextView.setText(String.format(Locale.US, "%.1f", weather.getTemperature()));
        mConditionTextView.setText(weather.getConditionsDescription());
        mPressureTextView.setText(weather.getPressure());
        mHumidityTextView.setText(weather.getHumidity());
        mCloudinessTextView.setText(weather.getCloudiness());
    }
}
