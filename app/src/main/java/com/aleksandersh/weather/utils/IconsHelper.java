package com.aleksandersh.weather.utils;

import android.support.annotation.DrawableRes;

import com.aleksandersh.weather.R;

/**
 * Created by AleksanderSh on 21.07.2017.
 * <p>
 * Позволяет подбирать иконки в зависимости от типа погоды.
 */

public class IconsHelper {
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
        if (group.equals(GROUP_STORM)) return R.drawable.storm_icon;
        else if (group.equals(GROUP_RAIN)) return R.drawable.rain_icon;
        else if (group.equals(GROUP_SNOW)) return R.drawable.snow_icon;
        else if (group.equals(GROUP_FOG)) return R.drawable.fog_icon;
        else if (group.equals(GROUP_CLEAR_SKY)) return R.drawable.clear_icon;
        else if (group.equals(GROUP_CLOUDS)) return R.drawable.cloud_icon;
        else return 0;
    }
}
