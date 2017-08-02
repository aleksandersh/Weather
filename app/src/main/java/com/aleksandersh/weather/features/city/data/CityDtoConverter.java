package com.aleksandersh.weather.features.city.data;


import com.aleksandersh.weather.features.city.data.storable.City;
import com.aleksandersh.weather.features.city.data.transferable.CityDto;
import com.aleksandersh.weather.storage.DtoConverter;


/**
 * Created by Vladimir Kondenko on 25.07.17.
 */

public class CityDtoConverter implements DtoConverter<CityDto, City> {

    @Override
    public City convert(CityDto dto) {
        double lng = Double.parseDouble(dto.getLng());
        double lat = Double.parseDouble(dto.getLat());
        return new City(dto.getCityId(), dto.getName(), dto.getCountryName(), lng, lat);
    }

}
