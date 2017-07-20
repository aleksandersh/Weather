package com.aleksandersh.weather.database;

import android.provider.BaseColumns;

/**
 * Created by AleksanderSh on 20.07.2017.
 */

public class WeatherDbSchema {
    public static final class CurrentWeatherTable {
        public static final String NAME = "current_weather";

        public static final class Cols implements BaseColumns {
            public static final String CITY_ID = "city_id";
            public static final String UPDATE_TIME = "update_time";
            public static final String LANGUAGE = "language";
            public static final String UNITS = "units";
            public static final String TEMPERATURE = "temperature";
            public static final String PRESSURE = "pressure";
            public static final String HUMIDITY = "humidity";
            public static final String CLOUDINESS = "cloudiness";
            public static final String WIND_SPEED = "wind_speed";
            public static final String WIND_DIRECTION = "wind_direction";
            public static final String DESCRIPTION = "description";
            public static final String GROUP = "weather_group";
        }
    }
}
