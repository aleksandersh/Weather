package com.aleksandersh.weather.utils;

import android.content.Intent;

/**
 * Created by AleksanderSh on 20.07.2017.
 * <p>
 * Помощник отправки локального broadcast об обновлении погоды.
 */

public class WeatherUpdateBroadcastHelper {

    public static final String WEATHER_UPDATE_ACTION = "weather_update_action";
    public static final String CITY_ID_EXTRA = "cityId";
    public static final String SUCCESSFUL_EXTRA = "successful";
    public static final String ERROR_CODE_EXTRA = "errorCode";

    /**
     * Возвращает интент, сигнализирующий об удачном обновлении погоды.
     *
     * @param cityId Идентификатор города, для которого обновилась погода.
     * @return Интент.
     */
    public static Intent getWeatherUpdateSuccessfulIntent(long cityId) {
        Intent intent = new Intent(WEATHER_UPDATE_ACTION);
        intent.putExtra(SUCCESSFUL_EXTRA, true);
        intent.putExtra(CITY_ID_EXTRA, cityId);

        return intent;
    }

    /**
     * Возвращает интент, сигнализирующий о неудачном обновлении погоды.
     *
     * @param errorCode Код ошибки, произошедщей при обновлении.
     * @return Интент.
     */
    public static Intent getWeatherUpdateUnsuccessfulIntent(long cityId, int errorCode) {
        Intent intent = new Intent(WEATHER_UPDATE_ACTION);
        intent.putExtra(SUCCESSFUL_EXTRA, false);
        intent.putExtra(CITY_ID_EXTRA, cityId);
        intent.putExtra(ERROR_CODE_EXTRA, errorCode);

        return intent;
    }
}
