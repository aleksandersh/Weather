package com.aleksandersh.weather.features.city.data.model.storable;


/**
 * Created by Vladimir Kondenko on 25.07.17.
 */

public class City {

    private int id;

    private String name;

    private String countryName;

    private double lng;

    private double lat;

    public City(int id, String name, String countryName, double lng, double lat) {
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

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }
}
