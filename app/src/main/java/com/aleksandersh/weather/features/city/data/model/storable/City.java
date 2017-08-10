package com.aleksandersh.weather.features.city.data.model.storable;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


/**
 * Created by Vladimir Kondenko on 25.07.17.
 */

@Entity
public class City {

    @PrimaryKey
    private int id;

    private String name;

    private String countryName;

    private double lng;

    private double lat;

    private boolean isCurrent = false;

    public City(int id, String name, String countryName, double lng, double lat, boolean isCurrent) {
        this.id = id;
        this.name = name;
        this.countryName = countryName;
        this.lng = lng;
        this.lat = lat;
        this.isCurrent = isCurrent;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountryName() {
        return countryName;
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    @Override
    public String toString() {
        return String.format("City (%d): %s, %s, current = %b", id, name, countryName, isCurrent);
    }
}