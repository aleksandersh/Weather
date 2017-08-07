package com.aleksandersh.weather.features.weather.presentation;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
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
import com.aleksandersh.weather.features.weather.data.model.storable.WeatherStorableState;
import com.aleksandersh.weather.features.weather.framework.WeatherUpdateBroadcastHelper;
import com.aleksandersh.weather.features.weather.framework.loader.StoredWeatherLoader;
import com.aleksandersh.weather.features.weather.framework.loader.UpdateWeatherProcessor;
import com.aleksandersh.weather.features.weather.storage.WeatherDao;
import com.aleksandersh.weather.network.ErrorMapper;
import com.aleksandersh.weather.utils.IconMapper;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Фрагмент, содержащий данные о погоде.
 */
public class WeatherFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener,
        LoaderManager.LoaderCallbacks<WeatherStorableState>,
        WeatherView {

    private static final String TAG = "WeatherFragment";

    private static final long MOSCOW_ID = 524901;

    private static final int LOADER_ID = 1;

    @Inject
    WeatherDao weatherDao;

    @Inject
    WeatherPresenter weatherPresenter;

    private Unbinder unbinder;

    private BroadcastReceiver receiver;

    private UpdateWeatherProcessor updateWeatherProcessor;

    private long mCityId;

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

    @BindView(R.id.error_text_view)
    TextView textViewError;

    @BindView(R.id.weather_imageview_condition)
    ImageView imageViewCondition;

    BottomSheetBehavior behavior;

    @BindView(R.id.weather_bottomsheet)
    View bottomSheet;

    @BindView(R.id.weather_bottomsheet_layout_current_city)
    View bottomSheetCurrentCityLayout;

    CityChooserFragment fragmentCityChooser;


    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
        mCityId = weatherPresenter.getCity().getId();
        receiver = new WeatherUpdatingBroadcastReceiver();
        updateWeatherProcessor = new UpdateWeatherProcessor(weatherPresenter);
        updateWeatherProcessor.start();
        updateWeatherProcessor.getLooper();
        getLoaderManager().initLoader(LOADER_ID, savedInstanceState, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        unbinder = ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);

        fragmentCityChooser = (CityChooserFragment) getChildFragmentManager().findFragmentById(R.id.weather_bottomsheet_fragment_citychooser);
        fragmentCityChooser.setOnCitySelectedListener(city -> {
            mCityId = city.getCityId();
            textViewCity.setText(city.getName());
            updateWeatherProcessor.requestUpdate(mCityId);
        });

        textViewCity.setText(weatherPresenter.getCity().getName());

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

        bottomSheet.setOnClickListener(v -> {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(WeatherUpdateBroadcastHelper.WEATHER_UPDATE_ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        updateWeatherProcessor.quit();
    }

    @Override
    public void onRefresh() {
        mCityId = weatherPresenter.getCity().getId();
        updateWeatherProcessor.requestUpdate(mCityId);
    }

    @Override
    public Loader<WeatherStorableState> onCreateLoader(int id, Bundle args) {
        Loader<WeatherStorableState> loader = null;
        if (id == LOADER_ID) {
            loader = new StoredWeatherLoader(getActivity(), weatherDao, mCityId);
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<WeatherStorableState> loader, WeatherStorableState data) {
        if (data != null) {
            updateUi(data.getWeather());
            textViewError.setVisibility(View.GONE);
        } else {
            updateWeatherProcessor.requestUpdate(mCityId);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<WeatherStorableState> loader) {
    }

    private void setBottomSheetLayoutExpanded(boolean expanded) {
        bottomSheetCurrentCityLayout.setVisibility(expanded ? View.GONE : View.VISIBLE);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (expanded) transaction.show(fragmentCityChooser);
        else transaction.hide(fragmentCityChooser);
        transaction.commit();
    }

    private void updateUi(Weather weather) {
        textViewTemperature.setText(String.format(Locale.US, "%.1f", weather.getTemperature()));
        conditionTextView.setText(weather.getDescription());
        textViewPressure.setText(String.valueOf(weather.getPressure()));
        textViewHumidity.setText(String.valueOf(weather.getHumidity()));
        textViewCloudiness.setText(String.valueOf(weather.getCloudiness()));
        int iconId = IconMapper.getDrawableResourceByGroup(weather.getGroup());
        if (iconId != 0) {
            imageViewCondition.setImageDrawable(getResources().getDrawable(iconId));
        }
    }

    private void onFailedLoading(String error) {
        textViewError.setText(error);
        textViewError.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    private class WeatherUpdatingBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (mCityId == intent.getLongExtra(WeatherUpdateBroadcastHelper.CITY_ID_EXTRA, 0)) {
                if (intent.getBooleanExtra(WeatherUpdateBroadcastHelper.SUCCESSFUL_EXTRA, false)) {
                    getLoaderManager().restartLoader(LOADER_ID, null, WeatherFragment.this);
                } else {
                    String error;
                    int errorResource = ErrorMapper.getStringResourceByErrorCode(
                            intent.getIntExtra(WeatherUpdateBroadcastHelper.ERROR_CODE_EXTRA, 0));
                    if (errorResource != 0) error = getString(errorResource);
                    else error = "";
                    onFailedLoading(error);
                }
            }
        }
    }
}
