package com.musicapp.service.impl;


import com.musicapp.domain.Country;
import com.musicapp.repository.CountryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryServiceImpl countryService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        List<Country> countries = new ArrayList<>();
        when(countryRepository.findAll()).thenReturn(countries);
    }


    @Test
    public void getAllCountries() {
        List<Country> countries = new ArrayList<>();
        List<Country> returned = countryService.getAllCountries();
        assertEquals(countries, returned);
    }
}
