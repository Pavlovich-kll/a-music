package com.musicapp.web.controller;

import com.musicapp.dto.CountryDto;
import com.musicapp.mapper.CountryMapper;
import com.musicapp.service.CountryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {


    private final CountryMapper countryMapper;
    private final CountryService countryService;

    @ApiOperation("Возвращает перечень всех существующих стран")
    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        return ResponseEntity.ok(countryMapper.map(countryService.getAllCountries()));
    }
}
