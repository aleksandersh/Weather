package com.aleksandersh.weather.model;

import java.util.Date;

/**
 * Created by AleksanderSh on 14.07.2017.
 */

public class Weather {
    private Date mCalculationTime;
    private Location mLocation;
    private WeatherCondition mCondition;
    private double mTemperature;
    private double mTemperatureMin;
    private double mTemperatureMax;
    private int mPressure;
    private int mHumidity;
    private int mCloudiness;
    private double mWindSpeed;
    private int mWindDirection;

    public Date getCalculationTime() {
        return mCalculationTime;
    }

    public void setCalculationTime(Date calculationTime) {
        mCalculationTime = calculationTime;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public WeatherCondition getCondition() {
        return mCondition;
    }

    public void setCondition(WeatherCondition condition) {
        mCondition = condition;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getTemperatureMin() {
        return mTemperatureMin;
    }

    public void setTemperatureMin(double temperatureMin) {
        mTemperatureMin = temperatureMin;
    }

    public double getTemperatureMax() {
        return mTemperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        mTemperatureMax = temperatureMax;
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
}
