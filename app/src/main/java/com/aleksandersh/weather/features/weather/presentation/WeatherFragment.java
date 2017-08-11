package com.aleksandersh.weather.features.weather.presentation;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleksandersh.weather.App;
import com.aleksandersh.weather.R;
import com.aleksandersh.weather.features.city.presentation.CityChooserFragment;
import com.aleksandersh.weather.features.weather.data.model.storable.Weather;
import com.aleksandersh.weather.utils.IconMapper;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Фрагмент, содержащий данные о погоде.
 */
public class WeatherFragment extends Fragment implements WeatherView {

    private Unbinder unbinder;

    @Inject
    WeatherPresenter presenter;

    @BindView(R.id.weather_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.weather_textview_city)
    TextView textViewCity;

    @BindView(R.id.weather_textview_temperature)
    TextView textViewTemperature;

    @BindView(R.id.weather_textview_condition)
    TextView conditionTextView;

    @BindView(R.id.weather_info_value_pressure)
    TextView textViewPressure;

    @BindView(R.id.weather_info_value_humidity)
    TextView textViewHumidity;

    @BindView(R.id.weather_info_value_cloudiness)
    TextView textViewCloudiness;

    @BindView(R.id.weather_imageview_condition)
    ImageView imageViewCondition;

    @BindView(R.id.weather_bottomsheet)
    View bottomSheet;

    @BindView(R.id.weather_bottomsheet_layout_current_city)
    View bottomSheetCurrentCityLayout;

    BottomSheetBehavior behavior;

    CityChooserFragment fragmentCityChooser;

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        App.getAppComponent().inject(this);
        unbinder = ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(presenter::onUpdate);

        fragmentCityChooser = (CityChooserFragment) getChildFragmentManager().findFragmentById(R.id.weather_bottomsheet_fragment_citychooser);
        fragmentCityChooser.setOnCitySelectedListener(city -> presenter.onUpdate());

        ViewCompat.setElevation(bottomSheet, getResources().getDimension(R.dimen.weather_elevation_bottomsheet));
        setBottomSheetLayoutExpanded(false);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i) {
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        setBottomSheetLayoutExpanded(false);
                        break;
                    }
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        setBottomSheetLayoutExpanded(true);
                        break;
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        bottomSheet.setOnClickListener(v -> behavior.setState(BottomSheetBehavior.STATE_EXPANDED));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showCurrentCity(String name) {
        textViewCity.setText(name);
    }

    @Override
    public void showCurrentWeather(Weather weather) {
        textViewTemperature.setText(String.format(Locale.US, "%.1f", weather.getTemperature()));
        conditionTextView.setText(weather.getDescription());
        textViewPressure.setText(String.valueOf(weather.getPressure()));
        textViewHumidity.setText(String.valueOf(weather.getHumidity()));
        textViewCloudiness.setText(String.valueOf(weather.getCloudiness()));
        int iconId = IconMapper.getDrawableResourceByGroup(weather.getGroup());
        if (iconId != 0) {
            imageViewCondition.setImageDrawable(ContextCompat.getDrawable(getActivity(), iconId));
        }
    }

    @Override
    public void showLoading(boolean loading) {
        swipeRefreshLayout.setRefreshing(loading);
    }

    @Override
    public void showError(Throwable throwable) {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void setBottomSheetLayoutExpanded(boolean expanded) {
        bottomSheetCurrentCityLayout.setVisibility(expanded ? View.GONE : View.VISIBLE);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (expanded) transaction.show(fragmentCityChooser);
        else transaction.hide(fragmentCityChooser);
        transaction.commit();
    }

}
