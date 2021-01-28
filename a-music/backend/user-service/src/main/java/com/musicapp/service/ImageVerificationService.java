package com.musicapp.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Интерфейс сервиса для верификации изображения.
 *
 * @author alexandrkudinov
 */
public interface ImageVerificationService {

    /**
     * Верифицирует изображение.
     *
     * @param file проверяемый файл
     * @return true файл прошел верификацию
     */
    boolean verify(MultipartFile file);

    /**
     * Парсит расширение файла.
     *
     * @param file проверяемый файл
     * @return расширение файла
     */
    String getFileType(MultipartFile file);
}
