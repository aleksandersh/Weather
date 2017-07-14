package com.aleksandersh.weather.dto.currentWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AleksanderSh on 14.07.2017.
 */

public class CurrentWeatherDto {
    @SerializedName("coord")
    @Expose
    private LocationDto mLocationDto;
    @SerializedName("weather")
    @Expose
    private List<WeatherConditionDto> mWeatherConditionDto = null;
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
    @SerializedName("mCod")
    @Expose
    private Integer mCod;

    public LocationDto getLocation() {
        return mLocationDto;
    }

    public void setLocation(LocationDto locationDto) {
        this.mLocationDto = locationDto;
    }

    public List<WeatherConditionDto> getWeatherCondition() {
        return mWeatherConditionDto;
    }

    public void setWeatherConditionDto(List<WeatherConditionDto> weatherConditionDto) {
        this.mWeatherConditionDto = weatherConditionDto;
    }

    public String getBase() {
        return mBase;
    }

    public void setBase(String base) {
        this.mBase = base;
    }

    public GeneralDto getGeneral() {
        return mGeneralDto;
    }

    public void setGeneral(GeneralDto generalDto) {
        this.mGeneralDto = generalDto;
    }

    public Integer getVisibility() {
        return mVisibility;
    }

    public void setVisibility(Integer visibility) {
        this.mVisibility = visibility;
    }

    public WindDto getWind() {
        return mWindDto;
    }

    public void setWind(WindDto windDto) {
        this.mWindDto = windDto;
    }

    public CloudsDto getClouds() {
        return mCloudsDto;
    }

    public void setClouds(CloudsDto cloudsDto) {
        this.mCloudsDto = cloudsDto;
    }

    public Integer getTime() {
        return mTime;
    }

    public void setTime(Integer time) {
        this.mTime = time;
    }

    public SystemDto getSystem() {
        return mSystemDto;
    }

    public void setSystem(SystemDto systemDto) {
        this.mSystemDto = systemDto;
    }

    public Integer getCityId() {
        return mCityId;
    }

    public void setCityId(Integer cityId) {
        this.mCityId = cityId;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        this.mCityName = cityName;
    }

    public Integer getCod() {
        return mCod;
    }

    public void setCod(Integer cod) {
        this.mCod = cod;
    }
}