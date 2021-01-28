package com.musicapp.service;

import com.musicapp.domain.City;

import java.util.List;

public interface CityService {

    List<City> getAllCities();

    City getById(Long id);
}
