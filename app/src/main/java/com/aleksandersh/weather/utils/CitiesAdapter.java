package com.aleksandersh.weather.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aleksandersh.weather.model.city.City;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CitiesAdapter extends ArrayAdapter<City> {

    private static final String TAG = "CitiesAdapter";

    private static final int LAYOUT_ID = android.R.layout.two_line_list_item;

    private List<City> data;
    private LayoutInflater inflater;

    public CitiesAdapter(@NonNull Context context) {
        super(context, LAYOUT_ID);
        inflater = LayoutInflater.from(context);
        Log.i(TAG, "CitiesAdapter: constructor without data");
    }

    public CitiesAdapter(Context context, List<City> cities) {
        super(context, LAYOUT_ID, cities);
        inflater = LayoutInflater.from(context);
        data = cities;
        Log.i(TAG, "CitiesAdapter: constructor with data");
    }

    public void updateData(List<City> data) {
        Log.i(TAG, "updateData: updating with " + data.toString());
        this.data = data;
        clear();
        addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            Log.i(TAG, "getView: creating view holder");
            view = inflater.inflate(LAYOUT_ID, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);

        } else {
            Log.i(TAG, "getView: reusing view holder");
            holder = (ViewHolder) view.getTag();
        }

        City city = getItem(position);
        if (city != null) holder.setCity(city);

        return view;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public City getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getPosition(@Nullable City item) {
        return data.indexOf(item);
    }

    static class ViewHolder {

        @BindView(android.R.id.text1)
        TextView textViewCity;

        @BindView(android.R.id.text2)
        TextView textViewCountry;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            Log.i(TAG, "ViewHolder: constructor");
        }

        public void setCity(City city) {
            Log.i(TAG, "setCity: setting city in the view holder");
            textViewCity.setText(city.getName());
            textViewCountry.setText(city.getCountryName());
        }

    }

}
