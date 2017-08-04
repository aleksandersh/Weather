package com.aleksandersh.weather.features.weather.data.model.storable;


/**
 * Created by AleksanderSh on 16.07.2017.
 * <p>
 * Запрос на обновление погоды по идентификатору города.
 */

public class WeatherRequest {

    private long openWeatherCityId;

    private String units;

    private String lang;

    public WeatherRequest(String units, String lang, long cityId) {
        this.units = units;
        this.lang = lang;
        openWeatherCityId = cityId;
    }

    public long getCityId() {
        return openWeatherCityId;
    }

    public String getUnits() {
        return units;
    }

    public String getLang() {
        return lang;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherRequest that = (WeatherRequest) o;

        if (openWeatherCityId != that.openWeatherCityId) return false;
        if (!units.equals(that.units)) return false;
        return lang.equals(that.lang);
    }

    @Override
    public int hashCode() {
        int result = units.hashCode();
        result = 31 * result + lang.hashCode();
        result = 31 * result + (int) (openWeatherCityId ^ (openWeatherCityId >>> 32));
        return result;
    }
}
