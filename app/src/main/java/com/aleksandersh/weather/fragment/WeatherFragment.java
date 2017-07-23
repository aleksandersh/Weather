package com.aleksandersh.weather.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.aleksandersh.weather.R;
import com.aleksandersh.weather.WeatherApplication;
import com.aleksandersh.weather.database.WeatherDao;
import com.aleksandersh.weather.domain.WeatherManager;
import com.aleksandersh.weather.fragment.loader.StoredWeatherLoader;
import com.aleksandersh.weather.fragment.loader.UpdateWeatherProcessor;
import com.aleksandersh.weather.model.weather.Weather;
import com.aleksandersh.weather.model.weather.WeatherStorableState;
import com.aleksandersh.weather.utils.ErrorsHelper;
import com.aleksandersh.weather.utils.IconsHelper;
import com.aleksandersh.weather.utils.WeatherUpdateBroadcastHelper;

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
        LoaderManager.LoaderCallbacks<WeatherStorableState> {
    private static final String TAG = "WeatherFragment";
    private static final long MOSCOW_ID = 524901;
    private static final int LOADER_ID = 1;

    @Inject
    WeatherDao mWeatherDao;
    @Inject
    WeatherManager mWeatherManager;

    private Unbinder mUnbinder;
    private UpdateWeatherProcessor mUpdateWeatherProcessor;
    private BroadcastReceiver mReceiver;
    private long mCityId;

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
    @BindView(R.id.error_text_view)
    TextView mErrorTextView;
    @BindView(R.id.weather_group_image_view)
    ImageView mWeatherGroupImageView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

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

        ((WeatherApplication) getActivity().getApplication()).getAppComponent().inject(this);

        mCityId = MOSCOW_ID; // Moscow hardcoded
        mReceiver = new WeatherUpdatingBroadcastReceiver();

        mUpdateWeatherProcessor = new UpdateWeatherProcessor(mWeatherManager);
        mUpdateWeatherProcessor.start();
        mUpdateWeatherProcessor.getLooper();

        getLoaderManager().initLoader(LOADER_ID, savedInstanceState, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        setHasOptionsMenu(true);

        mUnbinder = ButterKnife.bind(this, view);

        mSwipeRefreshLayout.setOnRefreshListener(this);

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
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mUpdateWeatherProcessor.quit();
    }

    @Override
    public void onRefresh() {
        mUpdateWeatherProcessor.requestUpdate(mCityId);
    }

    @Override
    public Loader<WeatherStorableState> onCreateLoader(int id, Bundle args) {
        Loader<WeatherStorableState> loader = null;
        if (id == LOADER_ID) {
            loader = new StoredWeatherLoader(getActivity(), mWeatherDao, mCityId);
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<WeatherStorableState> loader, WeatherStorableState data) {
        if (data != null) {
            updateUi(data.getWeather());
            mErrorTextView.setVisibility(View.GONE);
        } else {
            mUpdateWeatherProcessor.requestUpdate(mCityId);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<WeatherStorableState> loader) {
    }

    private void updateUi(Weather weather) {
        mTemperatureTextView.setText(String.format(Locale.US, "%.1f", weather.getTemperature()));
        mConditionTextView.setText(weather.getDescription());
        mPressureTextView.setText(String.valueOf(weather.getPressure()));
        mHumidityTextView.setText(String.valueOf(weather.getHumidity()));
        mCloudinessTextView.setText(String.valueOf(weather.getCloudiness()));
        int iconId = IconsHelper.getDrawableResourceByGroup(weather.getGroup());
        if (iconId != 0) {
            mWeatherGroupImageView.setImageDrawable(getResources().getDrawable(iconId));
        }
    }

    private void onFailedLoading(String error) {
        mErrorTextView.setText(error);
        mErrorTextView.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private class WeatherUpdatingBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mCityId == intent.getLongExtra(WeatherUpdateBroadcastHelper.CITY_ID_EXTRA, 0)) {
                if (intent.getBooleanExtra(WeatherUpdateBroadcastHelper.SUCCESSFUL_EXTRA, false)) {
                    getLoaderManager().restartLoader(LOADER_ID, null, WeatherFragment.this);
                } else {
                    String error;
                    int errorResource = ErrorsHelper.getStringResourceByErrorCode(
                            intent.getIntExtra(WeatherUpdateBroadcastHelper.ERROR_CODE_EXTRA, 0));
                    if (errorResource != 0) error = getString(errorResource);
                    else error = "";
                    onFailedLoading(error);
                }
            }
        }
    }
}
