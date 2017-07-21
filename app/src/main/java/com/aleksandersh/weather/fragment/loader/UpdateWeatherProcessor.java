package com.aleksandersh.weather.fragment.loader;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.aleksandersh.weather.domain.WeatherManager;

/**
 * Created by AleksanderSh on 21.07.2017.
 * <p>
 * Класс наследует {@link HandlerThread}, принимает запросы на обновление погоды и передает их
 * в background-поток.
 */

public class UpdateWeatherProcessor extends HandlerThread {
    private static final String TAG = "UpdateWeatherProcessor";
    private static final int MESSAGE_UPDATE = 0;

    private Handler mHandler;
    private WeatherManager mWeatherManager;

    public UpdateWeatherProcessor(Context context) {
        super(TAG);

        mWeatherManager = new WeatherManager(context);
    }

    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_UPDATE) {
                    long cityId = (long) msg.obj;
                    mWeatherManager.updateWeather(cityId);
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
        mHandler.obtainMessage(MESSAGE_UPDATE, cityId).sendToTarget();
    }
}
