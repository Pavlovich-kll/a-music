package com.musicapp.service.impl;

import com.musicapp.domain.Subscription;
import com.musicapp.domain.User;
import com.musicapp.domain.UserSubscription;
import com.musicapp.domain.UserSubscriptionEmailCode;
import com.musicapp.dto.SubscriptionDto;
import com.musicapp.dto.UserDto;
import com.musicapp.dto.UserSubscriptionDto;
import com.musicapp.mapper.UserMapper;
import com.musicapp.mapper.UserMapperImpl;
import com.musicapp.mapper.UserSubscriptionMapper;
import com.musicapp.repository.UserRepository;
import com.musicapp.repository.UserSubscriptionRepository;
import com.musicapp.service.SubscriptionService;
import com.musicapp.service.UserService;
import com.musicapp.service.UserSubscriptionEmailCodeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserSubscriptionServiceImplTest {

    @Mock
    private UserSubscriptionRepository userSubscriptionRepositoryMock;
    @Mock
    private UserSubscriptionEmailCodeService userSubscriptionEmailCodeServiceMock;
    @Mock
    private SubscriptionService subscriptionServiceMock;
    @Mock
    private UserService userServiceMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private UserSubscriptionMapper userSubscriptionMapper;
    private Subscription subscription = new Subscription();
    @Mock
    private UserMapper userMapper = new UserMapperImpl();
    @InjectMocks
    private UserSubscriptionServiceImpl userSubscriptionService;
    private List<UserSubscription> testUserSubscriptionList;
    private List<User> testUserList;
    private List<UserDto> testUserDtoList = new ArrayList<>();
    private List<Long> testUserIdList;
    private UserSubscription testUserSubscription;
    private UserSubscriptionDto testUserSubscriptionDto;
    private UserSubscriptionEmailCode userSubscriptionEmailCode;
    private User invitedUserA;
    private User invitedUserB;
    private User hostUser;
    private LocalDate localDate;


    @Before
    public void setUp() {
        subscription.setId(1L)
                .setUserCapacity(3);

        invitedUserA = new User().setId(1L);
        invitedUserB = new User().setId(3L);
        testUserList = new ArrayList<>(Arrays.asList(new User().setId(1L), new User().setId(3L)));
        testUserIdList = new ArrayList<>(Arrays.asList(invitedUserA.getId(), invitedUserB.getId()));
        localDate = LocalDate.of(2020, 12, 3);
        SubscriptionDto subscriptionDto =
                new SubscriptionDto(1L, "Premium", 1L, "One", 1, 30);

        hostUser = new User().setId(5L);

        userSubscriptionEmailCode = new UserSubscriptionEmailCode().setId(1L)
                .setUserSubscriptionId(1L)
                .setInvitedUserId(invitedUserA.getId())
                .setCode("qwerty");

        testUserSubscriptionList = LongStream.rangeClosed(1, 3)
                .mapToObj(id -> new UserSubscription().setId(id)
                        .setValid(false)
                        .setSubscription(subscription)
                        .setHostUser(hostUser.getId())
                        .setInvitedUsers(new HashSet<>(testUserList))
                        .setPurchaseDate(LocalDate.of(2020, 12, 1))
                        .setActualPrice(123L))
                .collect(Collectors.toList());

        testUserSubscription = testUserSubscriptionList.iterator().next();
        testUserSubscriptionDto =
                new UserSubscriptionDto(12L, localDate, true, 2L, subscriptionDto, testUserIdList);
        invitedUserA.setUserSubscription(testUserSubscriptionList.get(2));
        hostUser.setUserSubscription(testUserSubscriptionList.get(1));
    }

    @Test
    public void buySubscription() {
        when(userSubscriptionMapper.map(testUserSubscriptionDto)).thenReturn(testUserSubscription);
        when(userServiceMock.getById(testUserSubscriptionDto.getHostUser())).thenReturn(hostUser);
        userSubscriptionRepositoryMock.save(testUserSubscription);
        userRepositoryMock.save(hostUser);
        when(subscriptionServiceMock.getSubscriptionById(testUserSubscriptionDto.getSubscription().getId()))
                .thenReturn(java.util.Optional.ofNullable(subscription));
        when(userServiceMock.findByUserIds(testUserSubscriptionDto.getInvitedUsersId()))
                .thenReturn(new ArrayList<>(testUserList));
        userSubscriptionEmailCodeServiceMock.addToUserSubscriptionEmailCode(subscription.getId(), invitedUserA.getId());
        userSubscriptionEmailCodeServiceMock.sendEmailToInvitedUsers(new ArrayList<>(testUserList));

        userSubscriptionService.buySubscription(testUserSubscriptionDto);

        verify(userServiceMock, times(1)).getById(testUserSubscriptionDto.getHostUser());
        verify(userSubscriptionRepositoryMock, times(2)).save(testUserSubscription);
        verify(userRepositoryMock, times(2)).save(hostUser);
        verify(subscriptionServiceMock, times(1))
                .getSubscriptionById(testUserSubscriptionDto.getSubscription().getId());
        verify(userServiceMock, times(1)).findByUserIds(testUserSubscriptionDto.getInvitedUsersId());
        verify(userSubscriptionEmailCodeServiceMock, times(2))
                .addToUserSubscriptionEmailCode(subscription.getId(), invitedUserA.getId());
        verify(userSubscriptionEmailCodeServiceMock, times(2))
                .sendEmailToInvitedUsers(new ArrayList<>(testUserList));
    }

    @Test
    public void isValidSubscription() {
        when(userSubscriptionRepositoryMock.findAll()).thenReturn(testUserSubscriptionList);

        boolean valid = testUserSubscription.isValid();
        userSubscriptionService.isValidSubscription();
        assertNotEquals(valid, testUserSubscription.isValid());
    }

    @Test
    public void getAllUsersByHostUserId() {
        when(userSubscriptionRepositoryMock.findByHostUserId(hostUser.getId()))
                .thenReturn(java.util.Optional.ofNullable(testUserSubscription));
        when(userServiceMock.findAllByUserSubscriptionId(testUserSubscription.getId()))
                .thenReturn(new ArrayList<>(testUserList));
        when(userMapper.map(testUserList)).thenReturn(testUserDtoList);

        userSubscriptionService.getAllUsersByHostUserId(hostUser.getId());
        verify(userSubscriptionRepositoryMock, times(1)).findByHostUserId(hostUser.getId());
        verify(userServiceMock, times(1)).findAllByUserSubscriptionId(testUserSubscription.getId());
    }

    @Test
    public void deleteUserFromSubscription() {
        when(userServiceMock.getById(invitedUserA.getId())).thenReturn(invitedUserA);
        Principal principal = mock(Principal.class);

        when(userServiceMock.findByUsername(principal.getName())).thenReturn(hostUser);
        when(userRepositoryMock.save(any())).thenReturn(invitedUserA);

        userRepositoryMock.save(any());
        userSubscriptionService.deleteUserFromSubscription(principal, invitedUserA.getId());

        assertNotEquals(testUserSubscription.getId(), invitedUserA.getUserSubscription().getId());
        verify(userRepositoryMock, times(1)).save(any());
    }

    @Test
    public void addUserToSubscription() {
        userSubscriptionEmailCodeServiceMock.addToUserSubscriptionEmailCode(testUserSubscription.getId(), invitedUserA.getId());
        userSubscriptionEmailCodeServiceMock.sendEmailToUser(invitedUserA);

        verify(userSubscriptionEmailCodeServiceMock, times(1)).sendEmailToUser(invitedUserA);
        verify(userSubscriptionEmailCodeServiceMock, times(1))
                .addToUserSubscriptionEmailCode(testUserSubscription.getId(), invitedUserA.getId());
    }
}
