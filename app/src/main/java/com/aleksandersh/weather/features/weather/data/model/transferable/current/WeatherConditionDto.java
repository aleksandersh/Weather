package com.aleksandersh.weather.features.weather.data.model.transferable.current;


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
    private String group;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("icon")
    @Expose
    private String icon;

    public Integer getId() {
        return mId;
    }

    public String getGroup() {
        return group;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
