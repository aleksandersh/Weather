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
 * используемую в приложении.
 */

public class OpenWeatherMapDtoConverter implements DtoConverter<CurrentWeatherDto> {
    @Override
    public Weather convert(CurrentWeatherDto dto) {
        Weather weather = new Weather();

        weather.setCityId(dto.getCityId());

        GeneralDto generalDto = dto.getGeneral();
        if (generalDto != null) {
            weather.setTemperature(generalDto.getTemperature());
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

        String description;
        String group;
        List<WeatherConditionDto> weatherConditionDtoList = dto.getWeatherConditions();
        if (weatherConditionDtoList != null && !weatherConditionDtoList.isEmpty()) {
            group = getGroupByServiceWeatherId(weatherConditionDtoList.get(0).getId());
            description = getDescriptionFromWeatherConditions(weatherConditionDtoList);
        } else {
            description = "";
            group = "";
        }

        weather.setDescription(description);
        weather.setGroup(group);

        return weather;
    }

    private String getGroupByServiceWeatherId(int weatherId) {
        String group;
        if (200 <= weatherId && weatherId < 300) {
            group = "storm";
        } else if (300 <= weatherId && weatherId < 600) {
            group = "rain";
        } else if (600 <= weatherId && weatherId < 700) {
            group = "snow";
        } else if (700 <= weatherId && weatherId < 800) {
            group = "fog";
        } else if (weatherId == 800) {
            group = "clear_sky";
        } else if (801 <= weatherId && weatherId < 900) {
            group = "clouds";
        } else {
            group = "";
        }

        return group;
    }

    private String getDescriptionFromWeatherConditions(List<WeatherConditionDto> conditions) {
        String description;

        if (conditions.size() == 1) {
            description = conditions.get(0).getDescription();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (WeatherConditionDto conditionDto : conditions) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(conditionDto.getDescription());
            }
            description = stringBuilder.toString();
        }

        return description;
    }
}
