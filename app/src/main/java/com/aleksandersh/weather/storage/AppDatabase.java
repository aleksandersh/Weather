package com.aleksandersh.weather.storage;


import android.arch.persistence.room.RoomDatabase;

import com.aleksandersh.weather.features.city.data.dao.CityDao;
import com.aleksandersh.weather.features.city.data.model.storable.City;


/**
 * Created by Vladimir Kondenko on 08.08.17.
 */

@android.arch.persistence.room.Database(version = 1, entities = {City.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract CityDao cityDao();

}
