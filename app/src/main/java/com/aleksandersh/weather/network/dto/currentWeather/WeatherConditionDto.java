package com.aleksandersh.weather.network.dto.currentWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AleksanderSh on 14.07.2017.
 */

public class WeatherConditionDto {
    @SerializedName("id")
    @Expose
    private Integer mId;
    @SerializedName("main")
    @Expose
    private String mGroup;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("icon")
    @Expose
    private String mIcon;

    public Integer getId() {
        return mId;
    }

    public String getGroup() {
        return mGroup;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getIcon() {
        return mIcon;
    }
}
