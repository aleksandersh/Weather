package com.aleksandersh.weather.features.weather.framework.loader;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.aleksandersh.weather.features.weather.presentation.WeatherPresenter;


/**
 * Created by AleksanderSh on 21.07.2017.
 * <p>
 * Класс наследует {@link HandlerThread}, принимает запросы на обновление погоды и передает их
 * в background-поток.
 */

public class UpdateWeatherProcessor extends HandlerThread {

    private static final String TAG = "UpdateWeatherProcessor";

    private static final int MESSAGE_UPDATE = 0;

    private Handler handler;

    private WeatherPresenter weatherPresenter;

    public UpdateWeatherProcessor(WeatherPresenter weatherPresenter) {
        super(TAG);
        this.weatherPresenter = weatherPresenter;
    }

    @Override
    protected void onLooperPrepared() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_UPDATE) {
                    long cityId = (long) msg.obj;
                    weatherPresenter.updateWeather(cityId);
                }
            }
        };
    }

    /**
     * Передача в background-поток запрос на обновление погоды.
     *
     * @param cityId Идентификатор города, для которого обновляется погода.
     */
    public void requestUpdate(long cityId) {
        handler.obtainMessage(MESSAGE_UPDATE, cityId).sendToTarget();
    }

}
