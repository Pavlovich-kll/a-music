package com.musicapp.service.impl;

import com.musicapp.domain.*;
import com.musicapp.dto.PlaylistCreateDto;
import com.musicapp.dto.PlaylistDto;
import com.musicapp.mapper.AudioMapperImpl;
import com.musicapp.mapper.PlaylistMapperImpl;
import com.musicapp.repository.AudioRepository;
import com.musicapp.repository.PlaylistRepository;
import com.musicapp.service.AudioService;
import com.musicapp.service.DatabaseFileService;
import com.musicapp.service.PlaylistService;
import com.musicapp.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlaylistServiceImplTest {

    @Mock
    private PlaylistRepository playlistRepositoryMock;
    @Mock
    private DatabaseFileService databaseFileServiceMock;
    @Mock
    private AudioService audioServiceMock;
    @Mock
    private UserService userServiceMock;
    @Mock
    private AudioRepository audioRepositoryMock;

    private PlaylistService playlistService;

    private Playlist testPlaylist;
    private List<Playlist> playlists;
    private PlaylistCreateDto playlistCreateDto;
    private Set<Audio> audios;

    @Before
    public void setUp() {
        playlistService = new PlaylistServiceImpl(playlistRepositoryMock, databaseFileServiceMock, new PlaylistMapperImpl(),
                audioServiceMock, userServiceMock, new AudioMapperImpl(), audioRepositoryMock);

        audios = LongStream.rangeClosed(1, 5)
                .mapToObj(id -> new Audio()
                        .setId(id)
                        .setTitle("" + id)
                        .setAuthor(new Author().setName("author"))
                        .setAlbum(new Album().setTitle("album"))
                        .setGenre(new Genre().setTitle("genre")))
                .collect(Collectors.toSet());

        playlists = LongStream.rangeClosed(1, 10)
                .mapToObj(id -> new Playlist()
                        .setId(id)
                        .setTitle("title")
                        .setDescription("description")
                        .setCover(getDatabaseFile())
                        .setAudios(audios)
                        .setLikeUsers(new HashSet<>()))
                .collect(Collectors.toList());

        testPlaylist = playlists.iterator().next();

        playlistCreateDto = new PlaylistCreateDto()
                .setTitle("title")
                .setDescription("description")
                .setPic(new MockMultipartFile("cover", null, "", new byte[0]))
                .setTracks(Collections.singleton("1"));
    }

    @Test
    public void whenGetPlaylist_thenReturnTestPlaylistDto() {
        when(playlistRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testPlaylist));
        when(playlistRepositoryMock.findCountLike(anyLong())).thenReturn(1);
        when(playlistRepositoryMock.findTrackCount(anyLong())).thenReturn(1);
        when(audioRepositoryMock.countLikeById(anyLong())).thenReturn(1L);

        Optional<PlaylistDto> playlistDto = playlistService.getPlaylist("1");

        assertThat(playlistDto.isPresent()).isTrue();
    }

    @Test
    public void whenGetAllPlaylist_thenReturnTestPlaylistDtoList() {

        when(playlistRepositoryMock.findAll()).thenReturn(playlists);

        List<PlaylistDto> playlistDtoList = playlistService.getAllPlaylists();

        assertThat(playlists.size()).isEqualTo(playlistDtoList.size());

        verify(playlistRepositoryMock).findAll();
    }

    @Test
    public void givenIOException_whenCreatePlaylist_thenReturnEmptyOptional() throws IOException {

        when(databaseFileServiceMock.saveFile(any())).thenThrow(IOException.class);

        Optional<PlaylistDto> createdPlaylistDto = playlistService.createPlaylist(playlistCreateDto);

        assertThat(createdPlaylistDto.isPresent()).isFalse();
    }

    @Test
    public void whenCreatePlaylist_thenReturnOptional() throws IOException {

        when(databaseFileServiceMock.saveFile(any())).thenReturn(getDatabaseFile());

        Optional<PlaylistDto> createdPlaylistDto = playlistService.createPlaylist(playlistCreateDto);

        assertThat(createdPlaylistDto.isPresent()).isFalse();
    }

    @Test
    public void givenNotExistedPlaylist_whenAddedAudio_thenReturnEmptyOptional() {

        when(playlistRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        Optional<PlaylistDto> emptyPlaylistOptional = playlistService.addNewAudio("1", Collections.emptySet());

        assertThat(emptyPlaylistOptional.isPresent()).isFalse();
    }

    @Test
    public void givenNotExistedAudio_whenAddedAudio_thenReturnEmptyOptional() {

        when(audioServiceMock.getAudio(anyLong())).thenReturn(Optional.empty());
        when(playlistRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testPlaylist));

        Optional<PlaylistDto> emptyPlaylistOptional = playlistService.addNewAudio("1", Collections.singleton("100"));

        assertThat(emptyPlaylistOptional.isPresent()).isFalse();

        verify(playlistRepositoryMock, times(0)).addNewTrackInPlaylist(anyLong(), anyLong());
    }

    @Test
    public void givenExistedPlaylist_whenAddedAudio_thenReturnTestPlaylistDto() {

        when(audioServiceMock.getAudio(anyLong())).thenReturn(Optional.of(new Audio()));
        when(playlistRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testPlaylist));

        Optional<PlaylistDto> playlistOptional = playlistService.addNewAudio("1", Collections.singleton("1"));

        assertThat(playlistOptional.isPresent()).isFalse();

        verify(playlistRepositoryMock).addNewTrackInPlaylist(anyLong(), anyLong());
    }

    @Test
    public void whenCreatePlaylistLike_thenReturnTestPlaylistDto() {

        when(playlistRepositoryMock.save(any())).thenReturn(testPlaylist);
        when(playlistRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(testPlaylist));
        when(userServiceMock.getById(anyLong())).thenReturn(new User());

        Optional<PlaylistDto> createdPlaylistDto = playlistService.setLikeToPlaylist(1L, 1L);

        assertThat(createdPlaylistDto.isPresent()).isTrue();
        verify(playlistRepositoryMock).save(any());
    }

    private DatabaseFile getDatabaseFile() {
        return new DatabaseFile()
                .setId(1)
                .setBytes(new byte[0])
                .setFileExtension(FileExtension.JPEG);
    }
}
