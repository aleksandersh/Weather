package com.aleksandersh.weather.fragment;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
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
import com.jakewharton.rxbinding2.widget.RxAutoCompleteTextView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

;

/**
 * Фрагмент с поиском города, погоду для которого нужно отображать.
 */
public class CityDialogFragment extends BottomSheetDialogFragment implements CityView {

    public static final String TAG = "cityChooserFragment";

    private OnCitySelectedListener onCitySelectedListener;

    private Unbinder mUnbinder;

    @Inject
    public CityManager manager;

    @BindView(R.id.textview_city)
    public AppCompatAutoCompleteTextView textViewCity;

    private CompositeDisposable compositeDisposable;

    private CitiesAdapter citiesSuggestAdapter = null;

    public CityDialogFragment() {
    }

    public static CityDialogFragment newInstance() {
        return new CityDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_city_chooser_dialog, container, false);

        WeatherApplication.plus(this).inject(this);
        mUnbinder = ButterKnife.bind(this, rootView);
        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(RxAutoCompleteTextView.itemClickEvents(textViewCity).subscribe(adapterViewItemClickEvent -> {
            selectItem(citiesSuggestAdapter.getItem(adapterViewItemClickEvent.position()));
        }));

        textViewCity.setThreshold(1);
        compositeDisposable.add(RxTextView.textChanges(textViewCity)
                .filter(charSequence -> charSequence.length() > 0)
                .debounce(250, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .map(String::trim)
                .subscribe(manager::onQueryUpdated));

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        compositeDisposable.dispose();
        WeatherApplication.clearCitySubcomponent();
    }

    @Override
    public void updateData(List<CityDto> cities) {
        if (citiesSuggestAdapter == null) {
            citiesSuggestAdapter = new CitiesAdapter(this.getContext(), cities);
            citiesSuggestAdapter.setNotifyOnChange(true);
            textViewCity.setAdapter(citiesSuggestAdapter);
        } else {
            citiesSuggestAdapter.updateData(cities);
        }
    }

    @Override
    public void showError(Throwable throwable) {
        Log.e(TAG, throwable.getMessage(), throwable);
    }

    public void setOnCitySelectedListener(OnCitySelectedListener listener) {
        onCitySelectedListener = listener;
    }

    private void selectItem(CityDto city) {
        if (city != null) {
            manager.onCitySelected(city);
            if (onCitySelectedListener != null) onCitySelectedListener.onSelected(city);
        }
        dismiss();
    }

    public interface OnCitySelectedListener {
        void onSelected(CityDto city);
    }

}
