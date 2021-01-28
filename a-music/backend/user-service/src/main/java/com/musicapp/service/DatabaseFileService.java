package com.musicapp.service;

import com.musicapp.domain.DatabaseFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Сервис по работе с файлами в базе данных
 */
public interface DatabaseFileService {

    /**
     * Сохранение файла
     *
     * @param multipartFile файл для сохранения
     * @return сущность файла в базе данных
     * @throws IOException если метод {@link MultipartFile#getBytes()} выбрасывает исключение
     */
    DatabaseFile saveFile(MultipartFile multipartFile) throws IOException;

    /**
     * Получение файла
     *
     * @param id ID требуемого файла
     * @return сущность файла в базе данных
     */
    Optional<DatabaseFile> getFile(long id);

    Optional<DatabaseFile> getByName(String fileName);

    Optional<DatabaseFile> saveMultipartFile(MultipartFile file);
}