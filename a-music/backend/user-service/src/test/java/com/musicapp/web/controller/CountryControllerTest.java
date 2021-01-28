package com.musicapp.web.controller;

import com.musicapp.domain.Country;
import com.musicapp.mapper.CityMapper;
import com.musicapp.mapper.CityMapperImpl;
import com.musicapp.mapper.CountryMapper;
import com.musicapp.mapper.CountryMapperImpl;
import com.musicapp.service.impl.CountryServiceImpl;
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
@WebMvcTest(CountryController.class)
@Import(MockSpringSecurityTestConfiguration.class)
@WithMockUser
public class CountryControllerTest {
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


    private static final String COUNTRY_ENDPOINT = "/countries";

    private List<Country> countryList;

    @MockBean
    private CountryServiceImpl countryService;

    @Autowired
    private MockMvc mockMvc;

    private Country countryTest;


    @Before
    public void setUp() {
        countryTest = new Country()
                .setId(1L)
                .setCountryName("Ukraine");
        countryList = new ArrayList<>();
        countryList.add(countryTest);
    }

    @Test
    public void getAllCountries() throws Exception {
        when(countryService.getAllCountries()).thenReturn(countryList);
        mockMvc.perform(get(COUNTRY_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(countryList.size())));

    }

}
