package com.aleksandersh.weather.model.city;

/**
 * Created by Vladimir Kondenko on 25.07.17.
 */

public class City {

    private int id;
    private String name;
    private String countryName;
    private String lng;
    private String lat;

    public City(int id, String name, String countryName, String lng, String lat) {
        this.id = id;
        this.name = name;
        this.countryName = countryName;
        this.lng = lng;
        this.lat = lat;
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

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }
}
