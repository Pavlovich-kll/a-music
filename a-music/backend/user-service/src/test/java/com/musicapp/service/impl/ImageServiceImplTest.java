package com.musicapp.service.impl;

import com.musicapp.exception.WrongFileTypeException;
import com.musicapp.service.ImageService;
import com.musicapp.service.ImageVerificationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceImplTest {

    @Mock
    private ImageVerificationService imageVerificationServiceMock;
    private ImageService imageService;
    private MultipartFile testFile;

    @Before
    public void setUp() {
        imageService = new ImageServiceImpl(imageVerificationServiceMock, "");
        testFile = new MockMultipartFile(
                "name",
                "file.jpg",
                "application/jpg",
                new byte[0]);
    }

    @Test(expected = WrongFileTypeException.class)
    public void givenFileTypeNotSupported_whenUpload_thenThrowWrongFileTypeException() throws IOException {
        imageService.upload(testFile);
    }

    @Test
    public void whenUpload_thenOk() throws IOException {
        when(imageVerificationServiceMock.verify(any())).thenReturn(true);

        String filePath = imageService.upload(testFile);

        assertNotNull(filePath);

        Files.delete(Paths.get(filePath));
    }

    @Test(expected = FileNotFoundException.class)
    public void givenResourceDoNotExist_whenLoad_thenThrowFileNotFoundException() throws IOException {
        imageService.load(UUID.randomUUID().toString() + ".txt");
    }

    @Test
    public void givenResourceExists_whenLoad_thenReturnResource() throws IOException {
        Path fileName = Paths.get(UUID.randomUUID().toString() + "txt");
        UrlResource resource = new UrlResource(Files.createFile(fileName).toUri());

        assertEquals(resource, imageService.load(fileName.toString()));

        Files.delete(fileName);
    }
}