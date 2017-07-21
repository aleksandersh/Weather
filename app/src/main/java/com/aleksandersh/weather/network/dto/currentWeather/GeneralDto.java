package com.aleksandersh.weather.network.dto.currentWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AleksanderSh on 14.07.2017.
 */

public class GeneralDto {
    @SerializedName("temp")
    @Expose
    private Double mTemperature;
    @SerializedName("pressure")
    @Expose
    private Integer mPressure;
    @SerializedName("humidity")
    @Expose
    private Integer mHumidity;
    @SerializedName("temp_min")
    @Expose
    private Double mMinimumTemperature;
    @SerializedName("temp_max")
    @Expose
    private Double mMaximumTemperature;

    public Double getTemperature() {
        return mTemperature;
    }

    public Integer getPressure() {
        return mPressure;
    }

    public Integer getHumidity() {
        return mHumidity;
    }

    public Double getMinimumTemperature() {
        return mMinimumTemperature;
    }

    public Double getMaximumTemperature() {
        return mMaximumTemperature;
    }
}