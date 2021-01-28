package com.musicapp.service.impl;

import com.musicapp.domain.Album;
import com.musicapp.domain.DatabaseFile;
import com.musicapp.domain.FileExtension;
import com.musicapp.dto.AlbumCreationDto;
import com.musicapp.dto.AlbumViewDto;
import com.musicapp.mapper.AlbumMapperImpl;
import com.musicapp.repository.AlbumRepository;
import com.musicapp.service.AlbumService;
import com.musicapp.service.DatabaseFileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AlbumServiceImplTest {

    @Mock
    private AlbumRepository albumRepositoryMock;
    @Mock
    private DatabaseFileService databaseFileServiceMock;
    private AlbumService albumService;
    private Album testAlbum;
    private AlbumViewDto testAlbumViewDto;

    @Before
    public void setUp() {
        albumService = new AlbumServiceImpl(albumRepositoryMock, databaseFileServiceMock, new AlbumMapperImpl());

        testAlbum = new Album()
                .setId(1)
                .setTitle("title")
                .setReleaseDate(LocalDate.MIN)
                .setCover(getDatabaseFile());

        testAlbumViewDto = AlbumViewDto.builder()
                .withId(1)
                .withTitle("title")
                .withReleaseDate(LocalDate.MIN)
                .withCoverId(1L)
                .build();
    }

    @Test
    public void givenAlbumDoNotExist_whenGetAlbum_thenReturnEmptyOptional() {
        assertThat(albumService.getAlbum(testAlbumViewDto.getId()).isPresent()).isFalse();
    }

    @Test
    public void givenAlbumExists_whenGetAlbum_thenReturnTestAlbum() {
        when(albumRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(testAlbum));

        Optional<Album> albumDtoOptional = albumService.getAlbum(testAlbumViewDto.getId());

        assertThat(albumDtoOptional.isPresent()).isTrue();
        assertThat(albumDtoOptional.get()).isEqualTo(testAlbum);
    }

    @Test
    public void givenAlbumCreationDtoHasCover_whenCreateAlbum_thenReturnAlbumViewDto() throws IOException {
        when(databaseFileServiceMock.saveFile(any())).thenReturn(getDatabaseFile());
        when(albumRepositoryMock.save(any())).thenReturn(testAlbum);

        AlbumViewDto albumViewDto = albumService.createAlbum(getAlbumCreationDto(getMultipartFile()));

        assertThat(albumViewDto.getId()).isEqualTo(testAlbum.getId());
    }

    @Test
    public void givenAlbumCreationDtoDoNotHaveCover_whenCreateAlbum_thenReturnAlbumViewDto() throws IOException {
        when(albumRepositoryMock.save(any())).thenReturn(testAlbum);

        AlbumViewDto albumViewDto = albumService.createAlbum(getAlbumCreationDto(null));

        assertThat(albumViewDto.getId()).isEqualTo(testAlbum.getId());
    }

    private AlbumCreationDto getAlbumCreationDto(MultipartFile cover) {
        return new AlbumCreationDto()
                .setTitle("title")
                .setReleaseDate(LocalDate.MIN)
                .setCover(cover);
    }

    private MultipartFile getMultipartFile() {
        return new MockMultipartFile("file", null, FileExtension.JPEG.getMediaTypeString(),
                new byte[0]);
    }

    private DatabaseFile getDatabaseFile() {
        return new DatabaseFile()
                .setId(1)
                .setBytes(new byte[0])
                .setFileExtension(FileExtension.JPEG);
    }
}