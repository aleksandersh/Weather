package com.aleksandersh.weather.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aleksandersh.weather.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Фрагмент с поиском города, погоду для которого нужно отображать.
 */
public class CityChooserDialogFragment extends DialogFragment {

    public static final String TAG = "cityChooserFragment";

    private Unbinder mUnbinder;

    public CityChooserDialogFragment() {}

    public static CityChooserDialogFragment newInstance() {
        return new CityChooserDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_chooser_dialog, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mUnbinder.unbind();
    }
}
