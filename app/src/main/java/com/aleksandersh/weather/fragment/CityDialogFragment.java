package com.aleksandersh.weather.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aleksandersh.weather.CityView;
import com.aleksandersh.weather.R;
import com.aleksandersh.weather.WeatherApplication;
import com.aleksandersh.weather.domain.CityManager;
import com.aleksandersh.weather.network.dto.city.CityDto;
import com.aleksandersh.weather.utils.CitiesAdapter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Фрагмент с поиском города, погоду для которого нужно отображать.
 */
public class CityDialogFragment extends DialogFragment implements CityView {

    public static final String TAG = "cityChooserFragment";

    private Unbinder mUnbinder;

    @Inject
    public CityManager manager;

    @BindView(R.id.textview_city)
    public AppCompatAutoCompleteTextView textViewCity;

    private CitiesAdapter citiesSuggestAdapter;

    private CityDto selectedCity = null;

    public CityDialogFragment() {}

    public static CityDialogFragment newInstance() {
        return new CityDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_city_chooser_dialog, container, false);
        WeatherApplication.plus(this).inject(this);
        mUnbinder = ButterKnife.bind(this, rootView);
        citiesSuggestAdapter = new CitiesAdapter(getContext());
        citiesSuggestAdapter.setNotifyOnChange(true);
        textViewCity.setAdapter(citiesSuggestAdapter);
        textViewCity.setOnItemClickListener((adapterView, view, i, l) -> {
            selectItem(citiesSuggestAdapter.getItem(i));
        });
        RxTextView.textChanges(textViewCity)
                .skipInitialValue()
                .filter(charSequence -> charSequence.length() >= 2)
//                .debounce(250, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .map(String::trim)
                .subscribe(manager::onQueryUpdated);
        return rootView;
    }

    @OnClick(R.id.button_select_city)
    public void selectCity() {
        manager.onCitySelected(selectedCity);
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void updateData(List<CityDto> cities) {
        citiesSuggestAdapter.updateData(cities);
    }

    @Override
    public void showError(Throwable throwable) {
        Log.e(TAG, throwable.getMessage(), throwable);
    }

    private void selectItem(CityDto city) {
        textViewCity.setText(city.getName());
        selectedCity = city;
    }
}
