package com.musicapp.service;

import com.musicapp.domain.Country;

import java.util.List;

public interface CountryService {

    List<Country> getAllCountries();

    Country getById(Long id);
}
