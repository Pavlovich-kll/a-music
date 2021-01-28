package com.musicapp.service.impl;

import com.musicapp.domain.DatabaseFile;
import com.musicapp.domain.FileExtension;
import com.musicapp.repository.DatabaseFileRepository;
import com.musicapp.service.DatabaseFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Реализация сервиса по работе с файлами в базе данных
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DatabaseFileServiceImpl implements DatabaseFileService {

    private final DatabaseFileRepository databaseFileRepository;

    @Transactional
    @Override
    public DatabaseFile saveFile(MultipartFile multipartFile) throws IOException {
        DatabaseFile databaseFile = new DatabaseFile()
                .setName(multipartFile.getOriginalFilename())
                .setBytes(multipartFile.getBytes())
                .setFileExtension(FileExtension.parse(multipartFile.getContentType()));

        return databaseFileRepository.save(databaseFile);
    }

    @Override
    public Optional<DatabaseFile> getFile(long id) {
        return databaseFileRepository.findById(id);
    }

    @Override
    public Optional<DatabaseFile> getByName(String fileName) {
        return databaseFileRepository.findByName(fileName);
    }

    @Override
    public Optional<DatabaseFile> saveMultipartFile(MultipartFile file) {
        try {
            return Optional.of(saveFile(file));
        } catch (IOException e) {
            log.warn("Cannot save file to database", e);
            return Optional.empty();
        }
    }
}