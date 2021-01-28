package com.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class CityDto {

    Long id;
    String cityName;
}

