package com.aleksandersh.weather.network.dto.currentWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AleksanderSh on 14.07.2017.
 */

public class SystemDto {
    @SerializedName("type")
    @Expose
    private Integer mType;
    @SerializedName("id")
    @Expose
    private Integer mId;
    @SerializedName("message")
    @Expose
    private Double mMessage;
    @SerializedName("country")
    @Expose
    private String mCountryCode;
    @SerializedName("sunrise")
    @Expose
    private Integer mSunriseTime;
    @SerializedName("sunset")
    @Expose
    private Integer mSunsetTime;

    public Integer getType() {
        return mType;
    }

    public Integer getId() {
        return mId;
    }

    public Double getMessage() {
        return mMessage;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

    public Integer getSunriseTime() {
        return mSunriseTime;
    }

    public Integer getSunsetTime() {
        return mSunsetTime;
    }
}
