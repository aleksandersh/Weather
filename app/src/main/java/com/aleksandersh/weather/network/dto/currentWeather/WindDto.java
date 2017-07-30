package com.aleksandersh.weather.network.dto.currentWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AleksanderSh on 14.07.2017.
 */

public class WindDto {
    @SerializedName("speed")
    @Expose
    private Double mSpeed;
    @SerializedName("deg")
    @Expose
    private Double mDirection;

    public Double getSpeed() {
        return mSpeed;
    }

    public Double getDirection() {
        return mDirection;
    }
}
