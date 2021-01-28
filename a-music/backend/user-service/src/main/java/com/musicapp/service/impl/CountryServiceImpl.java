package com.musicapp.service.impl;

import com.musicapp.domain.Country;
import com.musicapp.exception.NotFoundException;
import com.musicapp.repository.CountryRepository;
import com.musicapp.service.CountryService;
import com.musicapp.util.constants.MessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;


    @Override
    @Transactional
    public List<Country> getAllCountries() {
        log.info("Sending list with all countries");
        return countryRepository.findAll();
    }

    @Override
    public Country getById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> {
                            log.error("Country with id " + id + " has not been found");
                            return new NotFoundException(MessageConstants.COUNTRY_ID_NOT_FOUND, "id");
                        }
                );
    }
}

