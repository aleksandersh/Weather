package com.aleksandersh.weather.dto.currentWeather;

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

    public void setTemperature(Double temperature) {
        this.mTemperature = temperature;
    }

    public Integer getPressure() {
        return mPressure;
    }

    public void setPressure(Integer pressure) {
        this.mPressure = pressure;
    }

    public Integer getHumidity() {
        return mHumidity;
    }

    public void setHumidity(Integer humidity) {
        this.mHumidity = humidity;
    }

    public Double getMinimumTemperature() {
        return mMinimumTemperature;
    }

    public void setMinimumTemperature(Double minimumTemperature) {
        this.mMinimumTemperature = minimumTemperature;
    }

    public Double getMaximumTemperature() {
        return mMaximumTemperature;
    }

    public void setMaximumTemperature(Double maximumTemperature) {
        this.mMaximumTemperature = maximumTemperature;
    }
}