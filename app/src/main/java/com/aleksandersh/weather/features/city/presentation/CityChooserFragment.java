package com.aleksandersh.weather.features.city.presentation;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.aleksandersh.weather.App;
import com.aleksandersh.weather.R;
import com.aleksandersh.weather.features.city.data.model.storable.City;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Фрагмент с поиском города, погоду для которого нужно отображать.
 */
public class CityChooserFragment extends Fragment implements CityView {

    public static final String TAG = "cityChooserFragment";

    private OnCitySelectedListener onCitySelectedListener;

    private Unbinder mUnbinder;

    @Inject
    public CityPresenter presenter;

    @BindView(R.id.citychooser_fab)
    FloatingActionButton fab;

    @BindView(R.id.citychooser_header_textview_title)
    TextView textViewTitle;

    @BindView(R.id.citychooser_textview_current_city)
    TextView textViewCurrentCity;

    @BindView(R.id.citychooser_textview_search_city)
    EditText editTextSearchCity;

    @BindView(R.id.citychooser_recyclerview_cities)
    RecyclerView recyclerViewCities;

    private CompositeDisposable compositeDisposable;

    private CitiesAdapter citiesSuggestAdapter = null;

    public CityChooserFragment() {
    }

    public static CityChooserFragment newInstance() {
        return new CityChooserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_city_chooser_dialog, container, false);
        App.plus().inject(this);
        presenter.onAttach(this);
        mUnbinder = ButterKnife.bind(this, rootView);
        compositeDisposable = new CompositeDisposable();

//        compositeDisposable.add(RxAutoCompleteTextView.itemClickEvents(editTextSearchCity).subscribe(adapterViewItemClickEvent -> {
//            selectItem(citiesSuggestAdapter.getItem(adapterViewItemClickEvent.position()));
//        }));

        compositeDisposable.add(RxTextView.textChanges(editTextSearchCity)
                .filter(charSequence -> charSequence.length() > 0)
                .debounce(250, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .map(String::trim)
                .subscribe(presenter::onSearchQueryUpdated));

//        Autocomplete.on(editTextSearchCity)
//                .re

        setSearchLayout(false);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
        mUnbinder.unbind();
        compositeDisposable.dispose();
        App.clearCitySubcomponent();
    }

    @Override
    public void updateData(City cities) {
        /*
        if (citiesSuggestAdapter == null) {
            citiesSuggestAdapter = new CitiesAdapter(this.getContext(), cities);
            citiesSuggestAdapter.setNotifyOnChange(true);
            editTextSearchCity.setAdapter(citiesSuggestAdapter);
        } else {
            citiesSuggestAdapter.updateData(cities);
        }
        */
    }

    @Override
    public void showError(Throwable throwable) {
        Log.e(TAG, throwable.getMessage(), throwable);
    }

    public void setOnCitySelectedListener(OnCitySelectedListener listener) {
        onCitySelectedListener = listener;
    }

    /**
     * Switches the layout elements between two states: a layout with a list of saved cities
     * and a layout with a search field.
     *
     * @param useSearchLayout whether use search or default layout
     */
    private void setSearchLayout(boolean useSearchLayout) {
        fab.setOnClickListener(v -> setSearchLayout(!useSearchLayout));
        fab.setImageResource(useSearchLayout ? R.drawable.ic_all_close_black_24dp : R.drawable.ic_all_add_black_24dp);
        textViewTitle.setText(useSearchLayout ? R.string.citychooser_header_add_city : R.string.citychooser_header_current_city);
        textViewCurrentCity.setVisibility(useSearchLayout ? View.GONE : View.VISIBLE);
        editTextSearchCity.setVisibility(useSearchLayout ? View.VISIBLE : View.GONE);
        // TODO Update recyclerview data
    }

    private void selectItem(City city) {
        if (city != null) {
            presenter.onCitySelected(city);
            if (onCitySelectedListener != null) onCitySelectedListener.onSelected(city);
        }
        // TODO Close the bottom sheet here
    }

    public interface OnCitySelectedListener {
        void onSelected(City city);
    }

}
