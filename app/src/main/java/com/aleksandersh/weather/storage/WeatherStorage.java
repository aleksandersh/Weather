package com.aleksandersh.weather.storage;

/**
 * Created by AleksanderSh on 16.07.2017.
 * <p>
 * Интерфейс хранилища данных о погоде.
 */

public interface WeatherStorage {
    void saveState(WeatherStorableState storableState);

    WeatherStorableState loadState();
}
