package com.musicapp.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Сервис по работе с изображениями
 */
public interface ImageService {

    /**
     * Метод позволяет загрузить изображение пользователю
     *
     * @param file файл для загрузки
     * @return имя загруженного файла
     */
    String upload(MultipartFile file) throws IOException;

    /**
     * Метод позволяет получить изображение пользователю
     *
     * @param fileName имя файла
     * @return требуемое изображение
     */
    Resource load(String fileName) throws MalformedURLException, FileNotFoundException;
}
