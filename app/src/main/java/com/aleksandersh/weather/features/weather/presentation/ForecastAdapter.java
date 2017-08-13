package com.aleksandersh.weather.features.weather.presentation;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleksandersh.weather.R;
import com.aleksandersh.weather.features.weather.data.model.storable.Weather;
import com.aleksandersh.weather.utils.BaseRxAdapter;
import com.aleksandersh.weather.utils.ConditionMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;


/**
 * Created by Vladimir Kondenko on 12.08.17.
 */

public class ForecastAdapter extends BaseRxAdapter<Weather, ForecastAdapter.ViewHolder> {

    public ForecastAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_weather_forecast, viewGroup, false);
        return new ForecastAdapter.ViewHolder(view);
    }

    static class ViewHolder extends BaseRxAdapter.BaseViewHolder<Weather> {

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
        public void bindItem(Weather item) {
            super.bindItem(item);
            int drawableRes = ConditionMapper.getDrawableResourceByGroup(item.getGroup());
            condition.setImageDrawable(itemView.getContext().getResources().getDrawable(drawableRes));

            temperature.setText(String.valueOf(item.getTemperature()));

            try {
                SimpleDateFormat currentFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                SimpleDateFormat displayFormat = new SimpleDateFormat("EEE, MMM d, HH:mm");
                Date currentDate = currentFormat.parse(item.getDateReadable());
                String displayDate = displayFormat.format(currentDate);
                date.setText(displayDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}
