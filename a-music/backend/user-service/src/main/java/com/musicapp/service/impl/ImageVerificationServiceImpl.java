package com.musicapp.service.impl;

import com.musicapp.service.ImageVerificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Реализация сервиса для верификации изображения.
 *
 * @author alexandrkudinov
 */
@Service
@Slf4j
public class ImageVerificationServiceImpl implements ImageVerificationService {

    private List<String> validTypesList;

    @PostConstruct
    private void initialize() {
        validTypesList = Collections.unmodifiableList(Arrays.asList(".jpeg", ".jpg", ".png"));
    }

    @Override
    public boolean verify(MultipartFile file) {
        return validTypesList.contains(getFileType(file));
    }

    @Override
    public String getFileType(MultipartFile file) {
        return Objects.requireNonNull(file.getContentType()).replaceFirst("[a-zA-Z].+/", ".");
    }
}
