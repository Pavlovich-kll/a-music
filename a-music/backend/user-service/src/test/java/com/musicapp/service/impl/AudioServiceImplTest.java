package com.musicapp.service.impl;

import com.musicapp.domain.*;
import com.musicapp.dto.AudioStubCreationDto;
import com.musicapp.dto.AudioViewDto;
import com.musicapp.mapper.AudioMapperImpl;
import com.musicapp.repository.AudioRepository;
import com.musicapp.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AudioServiceImplTest {

    @Mock
    private AudioRepository audioRepositoryMock;
    @Mock
    private UserService userServiceMock;
    @Mock
    private AuthorService authorServiceMock;
    @Mock
    private GenreService genreServiceMock;
    @Mock
    private AlbumService albumServiceMock;
    @Mock
    private DatabaseFileService databaseFileServiceMock;
    private AudioService audioService;
    private List<Audio> testAudios;
    private Audio testAudio;

    @Before
    public void setUp() {
        audioService = new AudioServiceImpl(new AudioMapperImpl(), albumServiceMock, genreServiceMock, authorServiceMock,
                audioRepositoryMock, userServiceMock, databaseFileServiceMock);

        testAudios = LongStream.rangeClosed(1, 10)
                .mapToObj(id -> new Audio()
                        .setId(id)
                        .setTitle("" + id)
                        .setAuthor(new Author().setName("author"))
                        .setAlbum(new Album().setTitle("album"))
                        .setGenre(new Genre().setTitle("genre"))
                        .setUserLikes(new HashSet<>()))
                .collect(Collectors.toList());

        testAudio = testAudios.iterator().next();
    }

    @Test
    public void givenAudioExists_whenGetAudio_thenReturnTestAudio() {
        when(audioRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testAudio));

        Optional<Audio> audioViewDtoOptional = audioService.getAudio(testAudio.getId());

        assertThat(audioViewDtoOptional.isPresent()).isTrue();
        assertThat(audioViewDtoOptional.get()).isEqualTo(testAudio);
    }

    @Test
    public void givenAudioDoNotExist_whenGetAudio_thenReturnEmptyOptional() {
        assertThat(audioService.getAudio(testAudio.getId()).isPresent()).isFalse();
    }

    @Test
    public void givenAudioExists_whenGetAudioViewDto_thenReturnTestAudio() {
        when(audioRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testAudio));

        Optional<AudioViewDto> audioViewDtoOptional = audioService.getAudioViewDto(testAudio.getId());

        assertThat(audioViewDtoOptional.isPresent()).isTrue();
        assertThat(audioViewDtoOptional.get().getTitle()).isEqualTo(testAudio.getTitle());
    }

    @Test
    public void givenAudioDoNotExist_whenGetAudioViewDto_thenReturnEmptyOptional() {
        assertThat(audioService.getAudioViewDto(testAudio.getId()).isPresent()).isFalse();
    }

    @Test
    public void whenGetAllAudios_thenReturnTestAudios() {
        when(audioRepositoryMock.findAll()).thenReturn(testAudios);

        assertThat(audioService.getAllAudios().size()).isEqualTo(testAudios.size());
    }

    @Test
    public void whenGetAudiosByAudioType_thenReturnTestAudios() {
        when(audioRepositoryMock.findAllByType(any())).thenReturn(testAudios);

        assertThat(audioService.getAudiosByAudioType(AudioType.MUSIC).size()).isEqualTo(testAudios.size());
    }

    @Test
    public void givenAuthorDoNotExist_whenCreateAudio_thenReturnEmptyOptional() {
        assertThat(audioService.createAudio(getAudioStubCreateDto()).isPresent()).isFalse();
    }

    @Test
    public void givenGenreDoNotExist_whenCreateAudio_thenReturnEmptyOptional() {
        when(authorServiceMock.getAuthorByName(anyString())).thenReturn(Optional.of(new Author()));

        assertThat(audioService.createAudio(getAudioStubCreateDto()).isPresent()).isFalse();
    }

    @Test
    public void givenAlbumDoNotExist_whenCreateAudio_thenReturnEmptyOptional() {
        when(authorServiceMock.getAuthorByName(anyString())).thenReturn(Optional.of(new Author()));
        when(genreServiceMock.getGenreByTitle(anyString())).thenReturn(Optional.of(new Genre()));

        assertThat(audioService.createAudio(getAudioStubCreateDto()).isPresent()).isFalse();
    }

    @Test
    public void givenFileWasNotSaved_whenCreateAudio_thenReturnEmptyOptional() {
        when(authorServiceMock.getAuthorByName(anyString())).thenReturn(Optional.of(new Author()));
        when(genreServiceMock.getGenreByTitle(anyString())).thenReturn(Optional.of(new Genre()));
        when(albumServiceMock.getAlbumByTitle(anyString())).thenReturn(Optional.of(new Album()));
        when(databaseFileServiceMock.saveMultipartFile(any())).thenReturn(Optional.empty());

        assertThat(audioService.createAudio(getAudioStubCreateDto()).isPresent()).isFalse();
    }

    @Test
    public void givenCoverWasNotSaved_whenCreateAudio_thenReturnEmptyOptional() {
        when(authorServiceMock.getAuthorByName(anyString())).thenReturn(Optional.of(new Author()));
        when(genreServiceMock.getGenreByTitle(anyString())).thenReturn(Optional.of(new Genre()));
        when(albumServiceMock.getAlbumByTitle(anyString())).thenReturn(Optional.of(new Album()));
        when(databaseFileServiceMock.saveMultipartFile(any()))
                .thenReturn(Optional.of(new DatabaseFile()))
                .thenReturn(Optional.empty());

        assertThat(audioService.createAudio(getAudioStubCreateDto()).isPresent()).isFalse();
    }

    @Test
    public void givenCoverIsNull_whenCreateAudio_thenReturnTestAudio() {
        when(authorServiceMock.getAuthorByName(anyString())).thenReturn(Optional.of(new Author()));
        when(genreServiceMock.getGenreByTitle(anyString())).thenReturn(Optional.of(new Genre()));
        when(albumServiceMock.getAlbumByTitle(anyString())).thenReturn(Optional.of(new Album()));
        when(databaseFileServiceMock.saveMultipartFile(any())).thenReturn(Optional.of(new DatabaseFile()));
        when(audioRepositoryMock.save(any())).thenReturn(testAudio);

        Optional<AudioViewDto> audioViewDtoOptional = audioService.createAudio(getAudioStubCreateDto(null));

        assertThat(audioViewDtoOptional.isPresent()).isTrue();
        assertThat(audioViewDtoOptional.get().getTitle()).isEqualTo(testAudio.getTitle());
    }

    @Test
    public void whenCreateAudio_thenReturnTestAudio() {
        when(authorServiceMock.getAuthorByName(anyString())).thenReturn(Optional.of(new Author()));
        when(genreServiceMock.getGenreByTitle(anyString())).thenReturn(Optional.of(new Genre()));
        when(albumServiceMock.getAlbumByTitle(anyString())).thenReturn(Optional.of(new Album()));
        when(databaseFileServiceMock.saveMultipartFile(any())).thenReturn(Optional.of(new DatabaseFile()));
        when(audioRepositoryMock.save(any())).thenReturn(testAudio);

        Optional<AudioViewDto> audioViewDtoOptional = audioService.createAudio(getAudioStubCreateDto());

        assertThat(audioViewDtoOptional.isPresent()).isTrue();
        assertThat(audioViewDtoOptional.get().getTitle()).isEqualTo(testAudio.getTitle());
    }

    @Test
    public void whenCreateLikeAudio_thenReturnTestPlaylistDto() {

        when(audioRepositoryMock.save(any())).thenReturn(testAudio);
        when(audioRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(testAudio));
        when(userServiceMock.getById(anyLong())).thenReturn(new User());

        Optional<AudioViewDto> audioViewDto = audioService.setAudioLike(1L, 1L);

        assertThat(audioViewDto.isPresent()).isTrue();
        verify(audioRepositoryMock).save(any());
    }

    @Test
    public void whenGetFavoriteAudio_thenReturnTestAudioListDto() {
        User user = new User();
        user.setAudioLikes(new HashSet<>(testAudios));
        when(userServiceMock.getById(anyLong())).thenReturn(user);
        assertEquals(audioService.getAudioWithLike(new HashSet<>(testAudios)), audioService.getFavoritesByAuthUser(1L));
    }

    private AudioStubCreationDto getAudioStubCreateDto() {
        return getAudioStubCreateDto(getMultipartFile("cover"));
    }

    private AudioStubCreationDto getAudioStubCreateDto(MultipartFile cover) {
        return new AudioStubCreationDto()
                .setAuthorName("")
                .setGenreName(Collections.singletonList("genre"))
                .setAlbumName("")
                .setTrack(new MockMultipartFile("track", "filename", "", new byte[0]))
                .setCover(cover);
    }

    private MultipartFile getMultipartFile(String name) {
        return new MockMultipartFile(name, null, "", new byte[0]);
    }
}