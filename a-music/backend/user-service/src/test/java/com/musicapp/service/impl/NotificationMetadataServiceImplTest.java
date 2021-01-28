package com.musicapp.service.impl;

import com.musicapp.domain.NotificationMetadata;
import com.musicapp.dto.NotificationMetadataDto;
import com.musicapp.exception.NotFoundException;
import com.musicapp.mapper.NotificationMetadataMapperImpl;
import com.musicapp.repository.NotificationMetadataRepository;
import com.musicapp.service.NotificationMetadataService;
import com.musicapp.util.constants.MessageConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NotificationMetadataServiceImplTest {

    @Mock
    private NotificationMetadataRepository notificationMetadataRepositoryMock;
    private NotificationMetadataService notificationMetadataService;
    private NotificationMetadata notificationMetadata;

    @Before
    public void setUp() {
        notificationMetadataService = new NotificationMetadataServiceImpl(notificationMetadataRepositoryMock,
                new NotificationMetadataMapperImpl());
        notificationMetadata = new NotificationMetadata();
    }

    @Test
    public void createNotification() {
        notificationMetadataService.createNotification(NotificationMetadataDto.builder().build());
        verify(notificationMetadataRepositoryMock, times(1)).save(notificationMetadata);
    }

    @Test(expected = NotFoundException.class)
    public void exceptionForUpdateNotification() {
        NotFoundException exception = new NotFoundException(MessageConstants.USER_ID_NOT_FOUND, "id");
        when(notificationMetadataRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new NotificationMetadata()));
        when(notificationMetadataRepositoryMock.findById(0L)).thenThrow(exception);
        notificationMetadataService.updateNotification(0L, NotificationMetadataDto.builder().build());
    }

    @Test
    public void findForUpdateNotification() {
        when(notificationMetadataRepositoryMock.findById(1L)).thenReturn(Optional.of(notificationMetadata));
        notificationMetadataService.updateNotification(1L, NotificationMetadataDto.builder().build());
        verify(notificationMetadataRepositoryMock, times(1)).save(notificationMetadata);
    }

    @Test
    public void getAllMetadata() {
        List<NotificationMetadata> listNotificationMetadata = LongStream.rangeClosed(1, 12)
                .mapToObj(x -> new NotificationMetadata())
                .collect(Collectors.toList());

        when(notificationMetadataRepositoryMock.findAll()).thenReturn(listNotificationMetadata);
        assertEquals(listNotificationMetadata, notificationMetadataService.getAllMetadata());
        verify(notificationMetadataRepositoryMock, times(1)).findAll();
    }
}