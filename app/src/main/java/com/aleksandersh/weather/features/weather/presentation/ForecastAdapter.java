package com.aleksandersh.weather.features.weather.presentation;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleksandersh.weather.R;
import com.aleksandersh.weather.features.weather.data.model.transferable.forecast.WeatherForecastDto;
import com.aleksandersh.weather.utils.BaseRxAdapter;

import butterknife.BindView;


/**
 * Created by Vladimir Kondenko on 12.08.17.
 */

public class ForecastAdapter extends BaseRxAdapter<WeatherForecastDto, ForecastAdapter.ViewHolder> {

    public ForecastAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_weather_forecast, viewGroup, false);
        return new ForecastAdapter.ViewHolder(view);
    }

    static class ViewHolder extends BaseRxAdapter.BaseViewHolder<WeatherForecastDto> {
        
        @BindView(R.id.forecast_textview_date)
        TextView date;
        
        @BindView(R.id.forecast_imageview_condition)
        ImageView condition;
        
        @BindView(R.id.forecast_textview_temperature)
        TextView temperature;

        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public void bindItem(WeatherForecastDto item) {
            super.bindItem(item);
            date.setText(item.getDtTxt());
            // TODO Set icon
            temperature.setText(String.valueOf(item.getMain().getTemp()));
        }
    }

}
