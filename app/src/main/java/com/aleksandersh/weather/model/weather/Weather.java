package com.aleksandersh.weather.model.weather;

/**
 * Created by AleksanderSh on 14.07.2017.
 */

public class Weather {
    private long mCityId;
    private double mTemperature;
    private int mPressure;
    private int mHumidity;
    private int mCloudiness;
    private double mWindSpeed;
    private int mWindDirection;
    private String mDescription;
    private String mGroup;

    public long getCityId() {
        return mCityId;
    }

    public void setCityId(long cityId) {
        mCityId = cityId;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public int getPressure() {
        return mPressure;
    }

    public void setPressure(int pressure) {
        mPressure = pressure;
    }

    public int getHumidity() {
        return mHumidity;
    }

    public void setHumidity(int humidity) {
        mHumidity = humidity;
    }

    public int getCloudiness() {
        return mCloudiness;
    }

    public void setCloudiness(int cloudiness) {
        mCloudiness = cloudiness;
    }

    public double getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        mWindSpeed = windSpeed;
    }

    public int getWindDirection() {
        return mWindDirection;
    }

    public void setWindDirection(int windDirection) {
        mWindDirection = windDirection;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String group) {
        mGroup = group;
    }
}
