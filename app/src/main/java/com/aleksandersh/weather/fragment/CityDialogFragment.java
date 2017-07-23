package com.aleksandersh.weather.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;

import com.aleksandersh.weather.CityDialogView;
import com.aleksandersh.weather.R;
import com.aleksandersh.weather.domain.CityManager;
import com.aleksandersh.weather.model.city.City;
import com.aleksandersh.weather.model.city.CityResultWrapper;
import com.jakewharton.rxbinding2.widget.RxTextSwitcher;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Фрагмент с поиском города, погоду для которого нужно отображать.
 */
public class CityDialogFragment extends DialogFragment implements CityDialogView {

    public static final String TAG = "cityChooserFragment";

    private Unbinder mUnbinder;

    @Inject
    public CityManager manager;

    @BindView(R.id.textview_city_chooser_city)
    public AutoCompleteTextView textViewCity;

    private ArrayAdapter<City> citiesSuggestAdapter;

    public CityDialogFragment() {}

    public static CityDialogFragment newInstance() {
        return new CityDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_chooser_dialog, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        citiesSuggestAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_dropdown_item_1line);
        RxTextView.textChanges(textViewCity)
                .skipInitialValue()
                .filter(charSequence -> charSequence.length() >= 3)
                .debounce(250, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .subscribe(manager::onQueryUpdated);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void updateData(List<City> cities) {
        citiesSuggestAdapter.clear();
        citiesSuggestAdapter.addAll(cities);
        textViewCity.setAdapter(citiesSuggestAdapter);
    }

    @Override
    public void showError(Throwable r) {

    }
}
