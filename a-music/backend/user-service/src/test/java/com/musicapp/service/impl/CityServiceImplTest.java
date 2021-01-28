package com.musicapp.service.impl;

import com.musicapp.domain.City;
import com.musicapp.exception.NotFoundException;
import com.musicapp.repository.CityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceImplTest {

    @Mock
    private CityRepository cityRepositoryMock;
    @InjectMocks
    private CityServiceImpl cityService;
    private List<City> testCities;
    private City firstTestCity;

    @Before
    public void setUp() {
        testCities = LongStream.iterate(1, seed -> seed + 1)
                .limit(10)
                .mapToObj(id -> new City().setId(id))
                .collect(Collectors.toList());

        firstTestCity = testCities.iterator().next();
    }

    @Test
    public void getAllCities() {
        when(cityRepositoryMock.findAll()).thenReturn(testCities);

        assertEquals(testCities, cityService.getAllCities());
    }

    @Test(expected = NotFoundException.class)
    public void givenCityDoNotExist_whenGetById_thenThrowNotFoundException() {
        cityService.getById(firstTestCity.getId());
    }

    @Test
    public void givenCityExists_whenGetById_thenReturnTestCity() {
        when(cityRepositoryMock.findById(anyLong())).thenReturn(Optional.of(firstTestCity));

        assertEquals(firstTestCity, cityService.getById(firstTestCity.getId()));
    }
}
