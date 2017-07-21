package com.aleksandersh.weather.model;

/**
 * Created by AleksanderSh on 14.07.2017.
 */

public class WeatherCondition {
    private int mId;
    private String mGroup;
    private String mDescription;
    private String mIcon;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String group) {
        mGroup = group;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }
}
