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

    private boolean selected;

    public City(int id, String name, String countryName, double lng, double lat, boolean selected) {
        this.id = id;
        this.name = name;
        this.countryName = countryName;
        this.lng = lng;
        this.lat = lat;
        this.selected = selected;
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

    public boolean isSelected() {
        return selected;
    }

}