package com.aleksandersh.weather.features.weather.data.transferable;


import com.aleksandersh.weather.storage.Dto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by AleksanderSh on 14.07.2017.
 */

public class CurrentWeatherDto extends Dto {

    @SerializedName("coord")
    @Expose
    private LocationDto mLocationDto;

    @SerializedName("weather")
    @Expose
    private List<WeatherConditionDto> mWeatherConditionDtoList = null;

    @SerializedName("base")
    @Expose
    private String mBase;

    @SerializedName("main")
    @Expose
    private GeneralDto mGeneralDto;

    @SerializedName("visibility")
    @Expose
    private Integer mVisibility;

    @SerializedName("wind")
    @Expose
    private WindDto mWindDto;

    @SerializedName("clouds")
    @Expose
    private CloudsDto mCloudsDto;

    @SerializedName("dt")
    @Expose
    private Integer mTime;

    @SerializedName("sys")
    @Expose
    private SystemDto mSystemDto;

    @SerializedName("id")
    @Expose
    private Integer mCityId;

    @SerializedName("name")
    @Expose
    private String mCityName;

    @SerializedName("cod")
    @Expose
    private Integer mCod;

    public LocationDto getLocation() {
        return mLocationDto;
    }

    public List<WeatherConditionDto> getWeatherConditions() {
        return mWeatherConditionDtoList;
    }

    public String getBase() {
        return mBase;
    }

    public GeneralDto getGeneral() {
        return mGeneralDto;
    }

    public Integer getVisibility() {
        return mVisibility;
    }

    public WindDto getWind() {
        return mWindDto;
    }

    public CloudsDto getClouds() {
        return mCloudsDto;
    }

    public Integer getTime() {
        return mTime;
    }

    public SystemDto getSystem() {
        return mSystemDto;
    }

    public Integer getCityId() {
        return mCityId;
    }

    public String getCityName() {
        return mCityName;
    }

    public Integer getCod() {
        return mCod;
    }
}