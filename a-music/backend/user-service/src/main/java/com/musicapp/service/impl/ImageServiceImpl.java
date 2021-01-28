package com.musicapp.service.impl;

import com.musicapp.exception.WrongFileTypeException;
import com.musicapp.service.ImageService;
import com.musicapp.service.ImageVerificationService;
import com.musicapp.util.constants.MessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Реализация сервиса по работе с изображениями
 */
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final ImageVerificationService imageVerificationService;
    private final String directoryPath;

    @Override
    public String upload(MultipartFile file) throws IOException {
        if (!imageVerificationService.verify(file)) {
            throw new WrongFileTypeException(MessageConstants.FILE_TYPE_NOT_SUPPORTED, "file");
        }

        String avatarName = UUID.randomUUID().toString() + imageVerificationService.getFileType(file);
        byte[] avatarBytes = file.getBytes();
        Path path = Paths.get(directoryPath + avatarName);
        Files.write(path, avatarBytes);
        log.info("File has been uploaded.");
        return avatarName;
    }

    @Override
    public Resource load(String fileName) throws MalformedURLException, FileNotFoundException {
        Path path = Paths.get(directoryPath + fileName);
        UrlResource resource = new UrlResource(path.toUri());

        if (resource.exists() || resource.isReadable()) {
            log.info("File has been loaded.");
            return resource;
        } else {
            log.error("File has not been found.");
            throw new FileNotFoundException(fileName + " is not found");
        }
    }
}