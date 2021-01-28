package com.musicapp.web.controller;

import com.musicapp.domain.Album;
import com.musicapp.domain.FileExtension;
import com.musicapp.dto.AlbumViewDto;
import com.musicapp.mapper.AlbumMapper;
import com.musicapp.mapper.AlbumMapperImpl;
import com.musicapp.service.AlbumService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
@WithMockUser(roles = "ADMIN")
public class AlbumControllerTest extends AbstractControllerTest {

    private static final String ALBUMS_ENDPOINT = "/albums";
    private static final String ALBUM_BY_ID_ENDPOINT = "/albums/100";

    @TestConfiguration
    static class Configuration {
        @Bean
        public AlbumMapper albumMapper() {
            return new AlbumMapperImpl();
        }
    }

    @MockBean
    private AlbumService albumServiceMock;
    private AlbumViewDto testAlbumViewDto;
    @Autowired
    private MockMvc mockMvc;
    private Album testAlbum;

    @Before
    public void setUp() {
        testAlbumViewDto = AlbumViewDto.builder()
                .withId(1)
                .withTitle("title")
                .withReleaseDate(LocalDate.MIN)
                .withCoverId(1L)
                .build();

        testAlbum = new Album().setId(1L);
    }

    @Test
    public void givenAlbumDoNotExist_whenGetAlbum_thenStatusNotFound() throws Exception {
        performGet(ALBUM_BY_ID_ENDPOINT)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenAlbumExists_whenGetAlbum_thenReturnTestAlbumViewDto() throws Exception {
        when(albumServiceMock.getAlbum(anyLong())).thenReturn(Optional.of(testAlbum));

        performGet(ALBUM_BY_ID_ENDPOINT)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(is(testAlbum.getId()), Long.class));
    }

    @Test
    public void givenIOException_whenCreateAlbum_thenStatusBadRequest() throws Exception {
        when(albumServiceMock.createAlbum(any())).thenThrow(IOException.class);

        mockMvc.perform(MockMvcRequestBuilders.multipart(ALBUMS_ENDPOINT)
                .file(getMultipartFile())
                .param("title", "title")
                .param("releaseDate", LocalDate.MIN.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenCreateAlbum_thenReturnTestAlbumViewDto() throws Exception {
        when(albumServiceMock.createAlbum(any())).thenReturn(testAlbumViewDto);

        mockMvc.perform(MockMvcRequestBuilders.multipart(ALBUMS_ENDPOINT)
                .file(getMultipartFile())
                .param("title", "title")
                .param("releaseDate", LocalDate.MIN.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(is(testAlbumViewDto.getId()), Long.class));
    }

    private MockMultipartFile getMultipartFile() {
        return new MockMultipartFile("file",
                null,
                FileExtension.JPEG.getMediaTypeString(),
                new byte[0]);
    }
}