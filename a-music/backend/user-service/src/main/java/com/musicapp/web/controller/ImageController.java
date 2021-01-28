package com.musicapp.web.controller;

import com.musicapp.dto.SimpleResponse;
import com.musicapp.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Методы для загрузки и получения изображений
 */
@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
@CrossOrigin
public class ImageController {

    private final ImageService service;

    @PostMapping
    public ResponseEntity<SimpleResponse> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(SimpleResponse.builder()
                .withPropertyName("fileName")
                .withPropertyValue(service.upload(file))
                .build());
    }

    @GetMapping(value = "/{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<Resource> load(@PathVariable String fileName) throws MalformedURLException, FileNotFoundException {
        return ResponseEntity.ok(service.load(fileName));
    }
}
