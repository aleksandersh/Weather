package com.aleksandersh.weather.model;

import java.util.Date;
import java.util.List;

/**
 * Created by AleksanderSh on 14.07.2017.
 */

public class Weather {
    private Date mCalculationTime;
    private Location mLocation;
    private List<WeatherCondition> mConditions;
    private double mTemperature;
    private double mMinimumTemperature;
    private double mMaximumTemperature;
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

    public List<WeatherCondition> getConditions() {
        return mConditions;
    }

    public void setConditions(List<WeatherCondition> conditions) {
        mConditions = conditions;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getMinimumTemperature() {
        return mMinimumTemperature;
    }

    public void setMinimumTemperature(double minimumTemperature) {
        mMinimumTemperature = minimumTemperature;
    }

    public double getMaximumTemperature() {
        return mMaximumTemperature;
    }

    public void setMaximumTemperature(double maximumTemperature) {
        mMaximumTemperature = maximumTemperature;
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

    public String getConditionsDescription() {
        String conditionText = "";
        if (!mConditions.isEmpty()) {
            if (mConditions.size() == 1) {
                conditionText = mConditions.get(0).getDescription();
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                for (WeatherCondition condition : mConditions) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(condition.getDescription());
                }
                conditionText = stringBuilder.toString();
            }
        }
        return conditionText;
    }
}
