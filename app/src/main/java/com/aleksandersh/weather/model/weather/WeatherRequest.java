package com.aleksandersh.weather.model.weather;

/**
 * Created by AleksanderSh on 16.07.2017.
 * <p>
 * Запрос на обновление погоды по идентификатору города.
 */

public class WeatherRequest {
    private String mUnits;
    private String mLang;
    private long mCityId;

    public WeatherRequest(String units, String lang, long cityId) {
        mUnits = units;
        mLang = lang;
        mCityId = cityId;
    }

    public String getUnits() {
        return mUnits;
    }

    public String getLang() {
        return mLang;
    }

    public long getCityId() {
        return mCityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherRequest that = (WeatherRequest) o;

        if (mCityId != that.mCityId) return false;
        if (!mUnits.equals(that.mUnits)) return false;
        return mLang.equals(that.mLang);
    }

    @Override
    public int hashCode() {
        int result = mUnits.hashCode();
        result = 31 * result + mLang.hashCode();
        result = 31 * result + (int) (mCityId ^ (mCityId >>> 32));
        return result;
    }
}
