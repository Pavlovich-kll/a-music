package com.musicapp.service.impl;

import com.musicapp.domain.Notification;
import com.musicapp.domain.User;
import com.musicapp.dto.NotificationDto;
import com.musicapp.exception.NotFoundException;
import com.musicapp.mapper.NotificationMapperImpl;
import com.musicapp.repository.NotificationRepository;
import com.musicapp.service.NotificationService;
import com.musicapp.service.UserService;
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
public class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepositoryMock;
    @Mock
    private UserService userServiceMock;
    private NotificationService notificationService;
    private User user;
    private Optional<Notification> notification;
    private NotFoundException exception;
    private NotificationDto notificationDto;

    @Before
    public void setUp() {
        user = new User();
        notification = Optional.of(new Notification());
        exception = new NotFoundException(MessageConstants.USER_ID_NOT_FOUND, "id");
        notificationDto = NotificationDto.builder()
                .withId(0L)
                .build();
        notificationService = new NotificationServiceImpl(notificationRepositoryMock, userServiceMock,
                new NotificationMapperImpl());
    }

    @Test(expected = NotFoundException.class)
    public void exceptionSetUserNotifications() {
        when(notificationRepositoryMock.findById(anyLong())).thenReturn(notification);
        when(notificationRepositoryMock.findById(0L)).thenThrow(exception);
        notificationService.setUserNotifications(anyLong(), notificationDto);
    }

    @Test
    public void setUserNotifications() {
        when(notificationRepositoryMock.findById(anyLong())).thenReturn(notification);
        notificationService.setUserNotifications(anyLong(), notificationDto);
        verify(notificationRepositoryMock, times(1)).save(any(Notification.class));
    }

    @Test(expected = NotFoundException.class)
    public void exceptionForGetUserNotifications() {
        when(userServiceMock.getById(anyLong())).thenReturn(user);
        when(userServiceMock.getById(0L)).thenThrow(exception);
        notificationService.getUserNotifications(0L);
    }

    @Test
    public void getUserNotifications() {
        List<Notification> listNotification = LongStream.rangeClosed(1, 10)
                .mapToObj(x -> new Notification())
                .collect(Collectors.toList());

        when(userServiceMock.getById(anyLong())).thenReturn(user);
        when(notificationRepositoryMock.findAllByUser(any(User.class))).thenReturn(listNotification);
        assertEquals(listNotification, notificationService.getUserNotifications(anyLong()));
        verify(userServiceMock, times(1)).getById(anyLong());
        verify(notificationRepositoryMock, times(1)).findAllByUser(any(User.class));
    }
}