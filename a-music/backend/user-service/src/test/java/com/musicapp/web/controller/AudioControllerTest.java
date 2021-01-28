package com.musicapp.web.controller;

import com.musicapp.domain.AudioType;
import com.musicapp.dto.AudioViewDto;
import com.musicapp.service.AudioService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AudioController.class)
public class AudioControllerTest extends AbstractControllerTest {

    private static final String FILE_ENDPOINT = "/file";
    private static final String AUDIO_TYPE_FILE_ENDPOINT = "/MUSIC/file";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AudioService audioServiceMock;
    private List<AudioViewDto> testAudioViewDtoList;
    private AudioViewDto testAudioViewDto;

    @Before
    public void setUp() {
        testAudioViewDtoList = LongStream.rangeClosed(1, 10)
                .mapToObj(id -> AudioViewDto.builder()
                        .withId("id")
                        .build())
                .collect(Collectors.toList());

        testAudioViewDto = testAudioViewDtoList.iterator().next();
    }

    @Test
    public void whenGetAllAudios_thenReturnTestAudioViewDtoList() throws Exception {
        when(audioServiceMock.getAllAudios()).thenReturn(testAudioViewDtoList);

        performGet(FILE_ENDPOINT)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(testAudioViewDtoList.size())));
    }

    @Test
    public void whenGetAudiosByAudioType_thenReturnTestAudioViewDtoList() throws Exception {
        when(audioServiceMock.getAudiosByAudioType(any())).thenReturn(testAudioViewDtoList);

        performGet(AUDIO_TYPE_FILE_ENDPOINT)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(testAudioViewDtoList.size())));
    }

    @Test
    public void givenAudioWasNotCreated_whenCreateAudio_thenStatusBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart(FILE_ENDPOINT)
                .file(getMultipartFile("file"))
                .file(getMultipartFile("cover"))
                .param("title", "title")
                .param("type", AudioType.MUSIC.name())
                .param("authorId", "1")
                .param("genreId", "1")
                .param("albumId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenAudioIsPresent_whenCreateAudio_thenStatusCreated() throws Exception {
        when(audioServiceMock.createAudio(any())).thenReturn(Optional.of(testAudioViewDto));

        mockMvc.perform(MockMvcRequestBuilders.multipart(FILE_ENDPOINT)
                .file(getMultipartFile("track"))
                .file(getMultipartFile("cover"))
                .param("title", "title")
                .param("type", AudioType.MUSIC.name())
                .param("authorName", "1")
                .param("genreName", "1")
                .param("albumName", "1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(is(testAudioViewDto.getId()), String.class));
    }

    private MockMultipartFile getMultipartFile(String name) {
        return new MockMultipartFile(name, null, "", new byte[0]);
    }
}