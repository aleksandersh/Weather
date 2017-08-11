package com.aleksandersh.weather.features.weather.data.model;


import com.aleksandersh.weather.features.weather.data.model.storable.Weather;
import com.aleksandersh.weather.features.weather.data.model.transferable.current.CloudsDto;
import com.aleksandersh.weather.features.weather.data.model.transferable.current.CurrentWeatherDto;
import com.aleksandersh.weather.features.weather.data.model.transferable.current.GeneralDto;
import com.aleksandersh.weather.features.weather.data.model.transferable.current.WeatherConditionDto;
import com.aleksandersh.weather.features.weather.data.model.transferable.current.WindDto;
import com.aleksandersh.weather.storage.DtoConverter;
import com.aleksandersh.weather.utils.IconMapper;

import java.util.List;


/**
 * Created by AleksanderSh on 15.07.2017.
 * <p>
 * Конвертирует объект передачи данных сервиса Open weather map в модель данных,
 * используемую в приложении.
 */

public class WeatherDtoConverter implements DtoConverter<CurrentWeatherDto, Weather> {

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
            group = IconMapper.GROUP_STORM;
        } else if (300 <= weatherId && weatherId < 600) {
            group = IconMapper.GROUP_RAIN;
        } else if (600 <= weatherId && weatherId < 700) {
            group = IconMapper.GROUP_SNOW;
        } else if (700 <= weatherId && weatherId < 800) {
            group = IconMapper.GROUP_FOG;
        } else if (weatherId == 800) {
            group = IconMapper.GROUP_CLEAR_SKY;
        } else if (801 <= weatherId && weatherId < 900) {
            group = IconMapper.GROUP_CLOUDS;
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
