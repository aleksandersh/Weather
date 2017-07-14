package com.aleksandersh.weather.dto.currentWeather;

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

    public void setType(Integer type) {
        this.mType = type;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public Double getMessage() {
        return mMessage;
    }

    public void setMessage(Double message) {
        this.mMessage = message;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

    public void setCountryCode(String countryCode) {
        this.mCountryCode = countryCode;
    }

    public Integer getSunriseTime() {
        return mSunriseTime;
    }

    public void setSunriseTime(Integer sunriseTime) {
        this.mSunriseTime = sunriseTime;
    }

    public Integer getSunsetTime() {
        return mSunsetTime;
    }

    public void setSunsetTime(Integer sunsetTime) {
        this.mSunsetTime = sunsetTime;
    }
}
