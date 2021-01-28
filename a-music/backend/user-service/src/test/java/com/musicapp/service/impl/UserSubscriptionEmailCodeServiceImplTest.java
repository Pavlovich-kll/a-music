package com.musicapp.service.impl;

import com.musicapp.domain.UserSubscriptionEmailCode;
import com.musicapp.dto.SubscriptionDto;
import com.musicapp.dto.UserDto;
import com.musicapp.dto.UserSubscriptionDto;
import com.musicapp.exception.NotFoundException;
import com.musicapp.mapper.UserMapper;
import com.musicapp.mapper.UserMapperImpl;
import com.musicapp.repository.UserSubscriptionEmailCodeRepository;
import com.musicapp.util.constants.MessageConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserSubscriptionEmailCodeServiceImplTest {

    @Mock
    private UserSubscriptionEmailCodeRepository userSubscriptionEmailCodeRepositoryMock;
    @Mock
    private JavaMailSender mailSenderMock;
    private UserMapper userMapper = new UserMapperImpl();
    @InjectMocks
    private UserSubscriptionEmailCodeServiceImpl userSubscriptionEmailCodeService;
    private UserSubscriptionEmailCode testUserSubscriptionEmailCode;
    private UserDto testUserDto;
    private UserDto testInvitedUserDto;
    private UserSubscriptionDto testUserSubscriptionDto;
    private SubscriptionDto subscriptionDto;

    @Before
    public void setUp() {
        subscriptionDto = SubscriptionDto.builder()
                .withId(1L)
                .withName("subscription_1")
                .withPrice(12L)
                .withDescription("vkvkvk")
                .withTrialPeriod(30)
                .withUserCapacity(3)
                .build();

        testInvitedUserDto = UserDto.builder()
                .withId(1L)
                .withFirstName("s")
                .withLastName("s")
                .withEmail("s@sd.com")
                .withPhone("123")
                .withUsername("Lia")
                .withDateOfBirth(LocalDate.of(2010, 2, 2))
                .withAvatar(null)
                .withCity(null)
                .withUserSubscription(testUserSubscriptionDto)
                .build();

        List<Long> invitedUsersId = new ArrayList<>();
        invitedUsersId.add(1L);

        testUserSubscriptionDto = UserSubscriptionDto.builder()
                .withActualPrice(12L)
                .withPurchaseDate(LocalDate.now())
                .withIsValid(true)
                .withSubscription(subscriptionDto)
                .withInvitedUsersId(invitedUsersId)
                .withHostUser(2L)
                .build();

        testUserDto = UserDto.builder()
                .withId(2L)
                .withFirstName("as")
                .withLastName("sa")
                .withEmail("sd@sd.com")
                .withPhone("1234")
                .withUsername("Lialia")
                .withDateOfBirth(LocalDate.of(2010, 2, 2))
                .withAvatar(null)
                .withCity(null)
                .withUserSubscription(testUserSubscriptionDto)
                .build();

        List<UserSubscriptionEmailCode> testUserSubscriptionEmailCodeList = LongStream.rangeClosed(1, 3)
                .mapToObj(id -> {
                    UserSubscriptionEmailCode userSubscriptionEmailCode = new UserSubscriptionEmailCode();
                    userSubscriptionEmailCode.setId(id);
                    userSubscriptionEmailCode.setInvitedUserId(testInvitedUserDto.getId());
                    userSubscriptionEmailCode.setCode("123");
                    userSubscriptionEmailCode.setUserSubscriptionId(subscriptionDto.getId());
                    return userSubscriptionEmailCode;
                })
                .collect(Collectors.toList());

        testUserSubscriptionEmailCode = testUserSubscriptionEmailCodeList.iterator().next();
    }

    @Test
    public void addToUserSubscriptionEmailCode() {
        userSubscriptionEmailCodeRepositoryMock.save(testUserSubscriptionEmailCode);
        verify(userSubscriptionEmailCodeRepositoryMock, times(1)).save(testUserSubscriptionEmailCode);
    }

    @Test
    public void findByInvitedUserId() {
        when(userSubscriptionEmailCodeRepositoryMock.findByInvitedUserId(testUserSubscriptionEmailCode.getInvitedUserId()))
                .thenReturn(Optional.of(testUserSubscriptionEmailCode));

        UserSubscriptionEmailCode userSubscriptionEmailCode =
                userSubscriptionEmailCodeService.findByInvitedUserId(testUserSubscriptionEmailCode.getInvitedUserId())
                        .orElseThrow(() -> new NotFoundException(MessageConstants.USER_SUBSCRIPTION_EMAIL_CODE_NOT_FOUND, "id"));

        assertEquals(testUserSubscriptionEmailCode, userSubscriptionEmailCode);
        verify(userSubscriptionEmailCodeRepositoryMock, times(1)).findByInvitedUserId(testUserSubscriptionDto.getInvitedUsersId().get(0));
    }

    @Test
    public void findByCode() {
        when(userSubscriptionEmailCodeRepositoryMock.findByCode(testUserSubscriptionEmailCode.getCode()))
                .thenReturn(Optional.of(testUserSubscriptionEmailCode));

        UserSubscriptionEmailCode userSubscriptionEmailCode =
                userSubscriptionEmailCodeService.findByCode(testUserSubscriptionEmailCode.getCode()).orElseThrow(
                        () -> new NotFoundException(MessageConstants.USER_SUBSCRIPTION_EMAIL_CODE_NOT_FOUND, "id"));

        assertEquals(testUserSubscriptionEmailCode, userSubscriptionEmailCode);
        verify(userSubscriptionEmailCodeRepositoryMock, times(1))
                .findByCode(testUserSubscriptionEmailCode.getCode());
    }

    @Test
    public void sendEmailToUser() throws MailException {
        SimpleMailMessage message = mock(SimpleMailMessage.class);
        when(userSubscriptionEmailCodeRepositoryMock.findByInvitedUserId(testInvitedUserDto.getId()))
                .thenReturn(Optional.ofNullable(testUserSubscriptionEmailCode));
        mailSenderMock.send(message);

        userSubscriptionEmailCodeService.sendEmailToUser(userMapper.map(testInvitedUserDto));
        verify(mailSenderMock, times(1)).send(message);
        verify(userSubscriptionEmailCodeRepositoryMock, times(1))
                .findByInvitedUserId(testInvitedUserDto.getId());
    }

    @Test
    public void verifyByCode() {
        userSubscriptionEmailCodeRepositoryMock.save(any());
        verify(userSubscriptionEmailCodeRepositoryMock, times(1)).save(any());
    }
}