package com.musicapp.service.impl;

import com.musicapp.domain.City;
import com.musicapp.exception.NotFoundException;
import com.musicapp.repository.CityRepository;
import com.musicapp.service.CityService;
import com.musicapp.util.constants.MessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;


    @Override
    @Transactional
    public List<City> getAllCities() {
        log.info("List of cities has been sent");
        return cityRepository.findAll();
    }

    @Override
    public City getById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> {
                            log.error("City with id " + id + " has not been found");
                            return new NotFoundException(MessageConstants.CITY_ID_NOT_FOUND, "id");
                        }
                );
    }
}
