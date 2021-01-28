package com.musicapp.web.controller;

import com.musicapp.domain.City;
import com.musicapp.domain.Country;
import com.musicapp.mapper.CityMapper;
import com.musicapp.mapper.CityMapperImpl;
import com.musicapp.mapper.CountryMapper;
import com.musicapp.mapper.CountryMapperImpl;
import com.musicapp.service.impl.CityServiceImpl;
import com.musicapp.web.config.MockSpringSecurityTestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
@Import(MockSpringSecurityTestConfiguration.class)
@WithMockUser
public class CityControllerTest {
    @TestConfiguration
    static class Configuration {

        @Bean
        public CountryMapper countryMapper() {
            return new CountryMapperImpl();
        }

        @Bean
        public CityMapper cityMapper() {
            return new CityMapperImpl();
        }
    }

    private static final String CITY_ENDPOINT = "/cities";


    private List<City> cityList;

    @MockBean
    private CityServiceImpl cityService;

    @Autowired
    private MockMvc mockMvc;

    private Country countryTest;

    private City cityTest;


    @Before
    public void setUp() {
        cityTest = new City()
                .setId(1L)
                .setCityName("Odessa")
                .setCountry(countryTest);
        countryTest = new Country()
                .setId(1L)
                .setCountryName("Ukraine");
        cityList = new ArrayList<>();
        cityList.add(cityTest);
    }


    @Test
    public void getAllCities() throws Exception {
        when(cityService.getAllCities()).thenReturn(cityList);
        mockMvc.perform(get(CITY_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(cityList.size())));
    }
}
