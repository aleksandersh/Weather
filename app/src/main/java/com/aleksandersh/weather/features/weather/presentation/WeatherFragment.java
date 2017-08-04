package com.aleksandersh.weather.features.weather.presentation;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleksandersh.weather.App;
import com.aleksandersh.weather.R;
import com.aleksandersh.weather.features.city.data.model.storable.City;
import com.aleksandersh.weather.features.city.presentation.CityDialogFragment;
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

    private UpdateWeatherProcessor updateWeatherProcessor;

    private BroadcastReceiver receiver;

    private long mCityId;

    @BindView(R.id.city_text_view)
    TextView textViewCity;

    @BindView(R.id.temperature_text_view)
    TextView temperatureTextView;

    @BindView(R.id.weather_condition_text_view)
    TextView conditionTextView;

    @BindView(R.id.pressure_text_view)
    TextView pressureTextView;

    @BindView(R.id.humidity_text_view)
    TextView humidityTextView;

    @BindView(R.id.cloudiness_text_view)
    TextView cloudinessTextView;

    @BindView(R.id.error_text_view)
    TextView errorTextView;

    @BindView(R.id.weather_group_image_view)
    ImageView weatherGroupImageView;

    @BindView(R.id.weather_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Создает новый экземпляр фрагмента {@link WeatherFragment}.
     *
     * @return Новый экземпляр.
     */
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

        setHasOptionsMenu(true);

        unbinder = ButterKnife.bind(this, view);

        textViewCity.setText(weatherPresenter.getCity().getName());

        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.weather_toolbar, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_weather_toolbar_change_city: {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                CityDialogFragment cityChooserDialogFragment = CityDialogFragment.newInstance();
                cityChooserDialogFragment.show(fragmentManager, CityDialogFragment.TAG);
                cityChooserDialogFragment.setOnCitySelectedListener(city -> {
                    mCityId = city.getCityId();
                    textViewCity.setText(city.getName());
                    updateWeatherProcessor.requestUpdate(mCityId);
                });
                return true;
            }
            default: {
                throw new IllegalArgumentException("Unknown menu id");
            }
        }
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
            errorTextView.setVisibility(View.GONE);
        } else {
            updateWeatherProcessor.requestUpdate(mCityId);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<WeatherStorableState> loader) {
    }

    private void onCityUpdated(City city) {

    }

    private void updateUi(Weather weather) {
        temperatureTextView.setText(String.format(Locale.US, "%.1f", weather.getTemperature()));
        conditionTextView.setText(weather.getDescription());
        pressureTextView.setText(String.valueOf(weather.getPressure()));
        humidityTextView.setText(String.valueOf(weather.getHumidity()));
        cloudinessTextView.setText(String.valueOf(weather.getCloudiness()));
        int iconId = IconMapper.getDrawableResourceByGroup(weather.getGroup());
        if (iconId != 0) {
            weatherGroupImageView.setImageDrawable(getResources().getDrawable(iconId));
        }
    }

    private void onFailedLoading(String error) {
        errorTextView.setText(error);
        errorTextView.setVisibility(View.VISIBLE);
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
