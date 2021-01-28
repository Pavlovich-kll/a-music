package com.musicapp.web.controller;

import com.musicapp.dto.CityDto;
import com.musicapp.mapper.CityMapper;
import com.musicapp.service.CityService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;
    private final CityMapper cityMapper;


    @ApiOperation("Возвращает перечень всех существующих городов")
    @GetMapping
    public ResponseEntity<List<CityDto>> getAllCities() {
        return ResponseEntity.ok(cityMapper.map(cityService.getAllCities()));
    }
}
