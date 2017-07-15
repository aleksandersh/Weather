package com.aleksandersh.weather.network.httpClient.converter;

import com.aleksandersh.weather.model.Location;
import com.aleksandersh.weather.model.Weather;
import com.aleksandersh.weather.model.WeatherCondition;
import com.aleksandersh.weather.network.dto.currentWeather.CloudsDto;
import com.aleksandersh.weather.network.dto.currentWeather.CurrentWeatherDto;
import com.aleksandersh.weather.network.dto.currentWeather.GeneralDto;
import com.aleksandersh.weather.network.dto.currentWeather.LocationDto;
import com.aleksandersh.weather.network.dto.currentWeather.SystemDto;
import com.aleksandersh.weather.network.dto.currentWeather.WeatherConditionDto;
import com.aleksandersh.weather.network.dto.currentWeather.WindDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by AleksanderSh on 15.07.2017.
 * <p>
 * Конвертирует объект передачи данных сервиса Open weather map в модель данных,
 * <p>
 * используемую в приложении.
 */

public class OpenWeatherMapDtoConverter implements DtoConverter<Weather, CurrentWeatherDto> {
    @Override
    public Weather convert(CurrentWeatherDto dto) {
        Weather weather = new Weather();

        weather.setCalculationTime(new Date(dto.getTime()));

        GeneralDto generalDto = dto.getGeneral();
        if (generalDto != null) {
            weather.setTemperature(generalDto.getTemperature());
            weather.setMinimumTemperature(generalDto.getMinimumTemperature());
            weather.setMaximumTemperature(generalDto.getMaximumTemperature());
            weather.setPressure(generalDto.getPressure());
            weather.setHumidity(generalDto.getHumidity());
        }

        CloudsDto cloudsDto = dto.getClouds();
        if (cloudsDto != null) {
            weather.setCloudiness(cloudsDto.getCloudiness());
        }

        WindDto windDto = dto.getWind();
        if (windDto != null) {
            weather.setWindSpeed(windDto.getSpeed());
            weather.setWindDirection(windDto.getDirection());
        }

        Location location = new Location();
        location.setCityId(dto.getCityId());
        location.setCityName(dto.getCityName());

        LocationDto locationDto = dto.getLocation();
        if (locationDto != null) {
            location.setLatitude(locationDto.getLatitude());
            location.setLongitude(locationDto.getLongitude());
        }

        SystemDto systemDto = dto.getSystem();
        if (systemDto != null) {
            location.setCountryCode(systemDto.getCountryCode());
        }

        weather.setLocation(location);

        List<WeatherCondition> weatherConditions;
        List<WeatherConditionDto> weatherConditionDtoList = dto.getWeatherConditions();
        if (weatherConditionDtoList != null) {
            weatherConditions = new ArrayList<>(weatherConditionDtoList.size());
            for (WeatherConditionDto conditionDto : weatherConditionDtoList) {
                WeatherCondition weatherCondition = new WeatherCondition();
                weatherCondition.setId(conditionDto.getId());
                weatherCondition.setGroup(conditionDto.getGroup());
                weatherCondition.setDescription(conditionDto.getDescription());
                weatherCondition.setIcon(conditionDto.getIcon());

            }
        } else {
            weatherConditions = Collections.emptyList();
        }

        weather.setConditions(weatherConditions);

        return weather;
    }
}
