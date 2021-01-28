package com.musicapp.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.domain.Notification;
import com.musicapp.domain.NotificationMetadata;
import com.musicapp.dto.NotificationDto;
import com.musicapp.dto.NotificationMetadataDto;
import com.musicapp.mapper.NotificationMapper;
import com.musicapp.mapper.NotificationMapperImpl;
import com.musicapp.mapper.NotificationMetadataMapper;
import com.musicapp.mapper.NotificationMetadataMapperImpl;
import com.musicapp.service.NotificationMetadataService;
import com.musicapp.service.NotificationService;
import com.musicapp.web.config.MockSpringSecurityTestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NotificationController.class)
@Import(MockSpringSecurityTestConfiguration.class)
@WithMockUser
public class NotificationControllerTest {

    @TestConfiguration
    static class Configuration {

        @Bean
        public NotificationMapper notificationMapper() {
            return new NotificationMapperImpl();
        }

        @Bean
        public NotificationMetadataMapper notificationMetadataMapper() {
            return new NotificationMetadataMapperImpl();
        }
    }

    private static final String NOTIFICATIONS_ENDPOINT = "/notifications";

    @MockBean
    private NotificationService notificationServiceMock;
    @MockBean
    private NotificationMetadataService notificationMetadataServiceMock;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenRoleUser_whenCreateNotification_thenStatusForbidden() throws Exception {
        mockMvc.perform(post(NOTIFICATIONS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NotificationMetadataDto.builder().build())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenRoleAdmin_whenCreateNotification_thenStatusOk() throws Exception {
        mockMvc.perform(post(NOTIFICATIONS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NotificationMetadataDto.builder().build())))
                .andExpect(status().isOk());

        verify(notificationMetadataServiceMock).createNotification(any());
    }

    @Test
    public void givenRoleUser_whenUpdateNotification_thenStatusForbidden() throws Exception {
        mockMvc.perform(put(NOTIFICATIONS_ENDPOINT + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NotificationMetadataDto.builder().build())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenRoleAdmin_whenUpdateNotification_thenStatusOk() throws Exception {
        mockMvc.perform(put(NOTIFICATIONS_ENDPOINT + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NotificationMetadataDto.builder().build())))
                .andExpect(status().isOk());

        verify(notificationMetadataServiceMock).updateNotification(anyLong(), any());
    }

    @Test
    public void whenSetUserNotifications_thenStatusOk() throws Exception {
        mockMvc.perform(put(NOTIFICATIONS_ENDPOINT + "/user-notifications/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NotificationDto.builder().build())))
                .andExpect(status().isOk());

        verify(notificationServiceMock).setUserNotifications(anyLong(), any());
    }

    @Test
    public void getAllNotifications() throws Exception {
        List<NotificationMetadata> listNotificationMetadata = LongStream.rangeClosed(1, 10)
                .mapToObj(x -> new NotificationMetadata())
                .collect(Collectors.toList());

        when(notificationMetadataServiceMock.getAllMetadata()).thenReturn(listNotificationMetadata);

        mockMvc.perform(get(NOTIFICATIONS_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(listNotificationMetadata.size())));
    }

    @Test
    public void getUserNotifications() throws Exception {
        List<Notification> listNotifications = LongStream.rangeClosed(1, 12)
                .mapToObj(x -> new Notification())
                .collect(Collectors.toList());

        when(notificationServiceMock.getUserNotifications(1L)).thenReturn(listNotifications);

        mockMvc.perform(get(NOTIFICATIONS_ENDPOINT + "/user-notification/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(listNotifications.size())));
    }
}