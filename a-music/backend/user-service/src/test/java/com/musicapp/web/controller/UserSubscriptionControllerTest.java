package com.musicapp.web.controller;

import com.musicapp.domain.UserSubscription;
import com.musicapp.dto.UserDto;
import com.musicapp.dto.UserSubscriptionDto;
import com.musicapp.mapper.UserSubscriptionMapper;
import com.musicapp.mapper.UserSubscriptionMapperImpl;
import com.musicapp.service.UserSubscriptionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserSubscriptionController.class)
public class UserSubscriptionControllerTest extends AbstractControllerTest {

    @TestConfiguration
    static class Configuration {

        @Bean
        public UserSubscriptionMapper userSubscriptionMapper() {
            return new UserSubscriptionMapperImpl();
        }
    }

    @MockBean
    UserSubscriptionService userSubscriptionServiceMock;
    @Autowired
    private UserSubscriptionMapper userSubscriptionMapper;
    private UserSubscription testUserSubscription;
    private UserSubscriptionDto testUserSubscriptionDto;
    private List<UserSubscription> testUserSubscriptionList;
    private List<Long> invitedUsersId = new ArrayList<>();
    private List<UserDto> testUsersDtoList = new ArrayList<>();

    @Before
    public void setUp() {
        testUserSubscriptionList = LongStream.rangeClosed(1, 3)
                .mapToObj(id -> new UserSubscription().setId(id))
                .collect(Collectors.toList());
        testUserSubscription = testUserSubscriptionList.iterator().next();
        testUserSubscriptionDto = userSubscriptionMapper.map(testUserSubscription);

        invitedUsersId.add(2L);
        invitedUsersId.add(3L);
    }

    @Test
    public void buySubscription() {
        when(userSubscriptionServiceMock.buySubscription(testUserSubscriptionDto)).thenReturn(testUserSubscriptionDto);
        userSubscriptionServiceMock.buySubscription(testUserSubscriptionDto);
        verify(userSubscriptionServiceMock, times(1)).buySubscription(testUserSubscriptionDto);
    }

    @Test
    public void addUserToSubscription() {
        Principal principal = mock(Principal.class);
        when(userSubscriptionServiceMock.addUsersToSubscription(principal, invitedUsersId))
                .thenReturn(testUserSubscriptionDto);
        userSubscriptionServiceMock.addUsersToSubscription(principal, invitedUsersId);
        verify(userSubscriptionServiceMock, times(1))
                .addUsersToSubscription(principal, invitedUsersId);
    }

    @Test
    public void getAllUsersByHostUserId() {
        when(userSubscriptionServiceMock.getAllUsersByHostUserId(testUserSubscription.getHostUser()))
                .thenReturn(testUsersDtoList);
        userSubscriptionServiceMock.getAllUsersByHostUserId(testUserSubscription.getHostUser());
        verify(userSubscriptionServiceMock, times(1))
                .getAllUsersByHostUserId(testUserSubscription.getHostUser());
    }

    @Test
    public void deleteUserFromSubscription() {
        Principal principal = mock(Principal.class);
        doNothing().when(userSubscriptionServiceMock).deleteUserFromSubscription(principal, invitedUsersId.get(1));
        userSubscriptionServiceMock.deleteUserFromSubscription(principal, invitedUsersId.get(1));
        verify(userSubscriptionServiceMock, times(1))
                .deleteUserFromSubscription(principal, invitedUsersId.get(1));
    }
}