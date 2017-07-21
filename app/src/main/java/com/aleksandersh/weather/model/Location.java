package com.aleksandersh.weather.model;

/**
 * Created by AleksanderSh on 14.07.2017.
 */

public class Location {
    private long mCityId;
    private String mCityName;
    private String mCountryCode;
    private double mLongitude;
    private double mLatitude;

    public long getCityId() {
        return mCityId;
    }

    public void setCityId(long cityId) {
        mCityId = cityId;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

    public void setCountryCode(String countryCode) {
        mCountryCode = countryCode;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }
}
