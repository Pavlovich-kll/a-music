package com.musicapp.mapper;

import com.musicapp.domain.Country;
import com.musicapp.dto.CountryDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CountryMapper {

    CountryDto map(Country country);

    List<CountryDto> map(List<Country> country);
}
