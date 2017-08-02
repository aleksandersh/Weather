package com.aleksandersh.weather.features.weather.data.transferable;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by AleksanderSh on 14.07.2017.
 */

public class LocationDto {

    @SerializedName("lon")
    @Expose
    private Double mLongitude;

    @SerializedName("lat")
    @Expose
    private Double mLatitude;

    public Double getLongitude() {
        return mLongitude;
    }

    public Double getLatitude() {
        return mLatitude;
    }
}
