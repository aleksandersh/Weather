package com.aleksandersh.weather.features.weather.data.transferable;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by AleksanderSh on 14.07.2017.
 */

public class CloudsDto {

    @SerializedName("all")
    @Expose
    private Integer mCloudiness;

    public Integer getCloudiness() {
        return mCloudiness;
    }
}