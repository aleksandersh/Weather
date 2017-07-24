
package com.aleksandersh.weather.model.city;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vladimir Kondenko on 22.07.17.
 */

public class CityResultWrapper implements Parcelable
{

    @SerializedName("totalResultsCount")
    @Expose
    private int totalResultsCount;
    @SerializedName("geonames")
    @Expose
    private List<City> cities = null;

    public final static Parcelable.Creator<CityResultWrapper> CREATOR = new Creator<CityResultWrapper>() {

        @SuppressWarnings({
                "unchecked"
        })
        public CityResultWrapper createFromParcel(Parcel in) {
            CityResultWrapper instance = new CityResultWrapper();
            instance.totalResultsCount = ((int) in.readValue((int.class.getClassLoader())));
            in.readList(instance.cities, (City.class.getClassLoader()));
            return instance;
        }

        public CityResultWrapper[] newArray(int size) {
            return (new CityResultWrapper[size]);
        }

    }
            ;

    public int getTotalResultsCount() {
        return totalResultsCount;
    }

    public void setTotalResultsCount(int totalResultsCount) {
        this.totalResultsCount = totalResultsCount;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(totalResultsCount);
        dest.writeList(cities);
    }

    public int describeContents() {
        return 0;
    }

}