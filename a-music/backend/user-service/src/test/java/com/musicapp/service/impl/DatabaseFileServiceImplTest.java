package com.musicapp.service.impl;

import com.musicapp.domain.DatabaseFile;
import com.musicapp.domain.FileExtension;
import com.musicapp.repository.DatabaseFileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseFileServiceImplTest {

    @Mock
    private DatabaseFileRepository databaseFileRepositoryMock;
    @InjectMocks
    private DatabaseFileServiceImpl databaseFileService;

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidFile_whenSaveFile_thenThrowIllegalArgumentException() throws IOException {
        databaseFileService.saveFile(getMultipartFile("invalid content type"));
    }

    @Test
    public void givenValidFile_whenSaveFile_thenRepositoryInvoked() throws IOException {
        databaseFileService.saveFile(getMultipartFile());

        verify(databaseFileRepositoryMock).save(any());
    }

    @Test
    public void givenFileExists_whenGetFile_thenFileIsPresent() {
        when(databaseFileRepositoryMock.findById(anyLong())).thenReturn(Optional.of(getDatabaseFile()));

        Optional<DatabaseFile> fileOptional = databaseFileService.getFile(1);

        assertThat(fileOptional.isPresent()).isTrue();
        assertThat(fileOptional.get().getId()).isEqualTo(1);
    }

    @Test
    public void givenFileDoNotExist_whenGetFile_thenOptionalIsEmpty() {
        Optional<DatabaseFile> fileOptional = databaseFileService.getFile(1);

        assertThat(fileOptional.isPresent()).isFalse();
    }

    private MultipartFile getMultipartFile() {
        return getMultipartFile(FileExtension.JPEG.getMediaTypeString());
    }

    private MultipartFile getMultipartFile(String contentType) {
        return new MockMultipartFile("file", null, contentType, new byte[0]);
    }

    private DatabaseFile getDatabaseFile() {
        return new DatabaseFile()
                .setId(1)
                .setBytes(new byte[0])
                .setFileExtension(FileExtension.JPEG);
    }
}