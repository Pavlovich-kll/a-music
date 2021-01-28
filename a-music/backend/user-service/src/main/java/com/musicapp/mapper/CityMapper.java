package com.musicapp.mapper;

import com.musicapp.domain.City;
import com.musicapp.dto.CityDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CityMapper {

    CityDto map(City city);

    List<CityDto> map(List<City> city);
}
