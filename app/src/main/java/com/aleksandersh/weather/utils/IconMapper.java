package com.aleksandersh.weather.utils;


import android.support.annotation.DrawableRes;

import com.aleksandersh.weather.R;


/**
 * Created by AleksanderSh on 21.07.2017.
 * <p>
 * Позволяет подбирать иконки в зависимости от типа погоды.
 */

public class IconMapper {

    public static final String GROUP_STORM = "storm";

    public static final String GROUP_RAIN = "rain";

    public static final String GROUP_SNOW = "snow";

    public static final String GROUP_FOG = "fog";

    public static final String GROUP_CLEAR_SKY = "clear_sky";

    public static final String GROUP_CLOUDS = "clouds";

    /**
     * Получает ресурс drawable для отображения состояния погоды.
     *
     * @param group Группа состояний погоды.
     * @return Идентификатор Drawable ресурса. {@code 0}, если ресурс не определен.
     */
    @DrawableRes
    public static int getDrawableResourceByGroup(String group) {
        switch (group) {
            case GROUP_STORM:
                return R.drawable.ic_all_storm;
            case GROUP_RAIN:
                return R.drawable.ic_all_rain;
            case GROUP_SNOW:
                return R.drawable.ic_all_snow;
            case GROUP_FOG:
                return R.drawable.ic_all_fog;
            case GROUP_CLEAR_SKY:
                return R.drawable.ic_all_clear;
            case GROUP_CLOUDS:
                return R.drawable.ic_all_cloudy;
            default:
                return 0;
        }
    }
}
