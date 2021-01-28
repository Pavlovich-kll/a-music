package com.musicapp.web.controller;

import com.musicapp.domain.DatabaseFile;
import com.musicapp.domain.FileExtension;
import com.musicapp.domain.Genre;
import com.musicapp.mapper.GenreMapper;
import com.musicapp.mapper.GenreMapperImpl;
import com.musicapp.service.DatabaseFileService;
import com.musicapp.service.GenreService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContentController.class)
public class ContentControllerTest extends AbstractControllerTest {

    @TestConfiguration
    static class Configuration {

        @Bean
        public GenreMapper genreMapper() {
            return new GenreMapperImpl();
        }
    }

    private static final String FILE_BY_ID_ENDPOINT = "/content/file/1";
    private static final String GENRES_ENDPOINT = "/content/genres";

    @MockBean
    private DatabaseFileService databaseFileServiceMock;
    @MockBean
    private GenreService genreServiceMock;
    @Autowired
    private MockMvc mockMvc;
    private List<Genre> testGenres;


    @Before
    public void setUp() {
        testGenres = LongStream.rangeClosed(1, 10)
                .mapToObj(id -> new Genre().setId(id))
                .collect(Collectors.toList());
    }

    @Test
    public void whenGetAllGenres_thenReturnListGenresDto() throws Exception {
        when(genreServiceMock.getAllGenres()).thenReturn(testGenres);

        performGet(GENRES_ENDPOINT)
                .andExpect(status().isOk());
    }

    @Test
    public void givenFileDoNotExist_whenGetFile_thenStatusNotFound() throws Exception {
        performGet(FILE_BY_ID_ENDPOINT)
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenFileExists_whenGetFile_thenStatusOk() throws Exception {
        when(databaseFileServiceMock.getFile(anyLong())).thenReturn(Optional.of(getDatabaseFile()));

        performGet(FILE_BY_ID_ENDPOINT)
                .andExpect(status().isOk());
    }

    private DatabaseFile getDatabaseFile() {
        return new DatabaseFile()
                .setId(1)
                .setBytes(new byte[0])
                .setFileExtension(FileExtension.JPEG);
    }
}
