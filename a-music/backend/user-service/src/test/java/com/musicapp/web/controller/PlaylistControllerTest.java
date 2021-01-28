package com.musicapp.web.controller;


import com.musicapp.dto.PlaylistDto;
import com.musicapp.dto.PlaylistPutDto;
import com.musicapp.service.PlaylistService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(PlaylistController.class)
public class PlaylistControllerTest extends AbstractControllerTest {

    private static final String PLAYLIST_ENDPOINT = "/playlist";
    private static final String PLAYLIST_PUT_ENDPOINT = "/playlist/new";
    private static final String PLAYLIST_BY_ID_ENDPOINT = "/playlist/1";

    @MockBean
    private PlaylistService playlistServiceMock;

    @Autowired
    private MockMvc mockMvc;

    private List<PlaylistDto> testPlaylistDtoList;
    private PlaylistDto testPlaylistDto;

    @Before
    public void setUp() {

        testPlaylistDtoList = LongStream.rangeClosed(1, 10)
                .mapToObj(id -> PlaylistDto.builder().withId("id").build())
                .collect(Collectors.toList());

        testPlaylistDto = testPlaylistDtoList.iterator().next();
    }

    @Test
    public void whenGetAllPlaylist_thenReturnTestPlaylistDtoList() throws Exception {

        when(playlistServiceMock.getAllPlaylists()).thenReturn(testPlaylistDtoList);

        performGet(PLAYLIST_ENDPOINT)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(testPlaylistDtoList.size())));
    }

    @Test
    public void whenGetPlaylist_thenReturnTestPlaylistDto() throws Exception {

        when(playlistServiceMock.getPlaylist(anyString())).thenReturn(java.util.Optional.ofNullable(testPlaylistDto));

        performGet(PLAYLIST_BY_ID_ENDPOINT)
                .andExpect(status().isOk());
    }

    @Test
    public void whenCreatedPlaylist_thenReturnTestPlaylistDto() throws Exception {

        when(playlistServiceMock.createPlaylist(ArgumentMatchers.any())).thenReturn(java.util.Optional.ofNullable(testPlaylistDto));

        mockMvc.perform(MockMvcRequestBuilders.multipart(PLAYLIST_ENDPOINT)
                .file(getMultipartFile())
                .param("title", "title")
                .param("description", "description"))
                .andExpect(status().isCreated());
    }

    @Test
    public void GivenPlaylistWasUpdated_whenAddedNewAudios_thenReturnTestPlaylistDto() throws Exception {

        when(playlistServiceMock.addNewAudio(anyString(), anySet())).thenReturn(Optional.of(testPlaylistDto));

        mockMvc.perform(MockMvcRequestBuilders.put(PLAYLIST_PUT_ENDPOINT)
                .param("id", "1L")
                .param("tracks", "1"))
                .andExpect(status().isOk());
    }

    private MockMultipartFile getMultipartFile() {
        return new MockMultipartFile("cover",
                null,
                null,
                new byte[0]);
    }
}
