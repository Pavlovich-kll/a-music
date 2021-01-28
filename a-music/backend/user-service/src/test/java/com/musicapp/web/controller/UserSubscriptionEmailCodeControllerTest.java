package com.musicapp.web.controller;

import com.musicapp.domain.UserSubscriptionEmailCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserSubscriptionEmailCodeController.class)
public class UserSubscriptionEmailCodeControllerTest extends AbstractControllerTest {

    @MockBean
    UserSubscriptionEmailCodeController userSubscriptionEmailCodeControllerMock;
    private UserSubscriptionEmailCode testUserSubscriptionEmailCode;
    private List<UserSubscriptionEmailCode> testUserSubscriptionEmailCodeList;

    private static final String USER_SUBSCRIPTION_EMAIL_CODE_GET_BY_CODE
            = "/user-subscription-email-code/a641a2c1-2443-45da-861a-1362bce241d9";

    @Before
    public void setUp() {
        testUserSubscriptionEmailCodeList = LongStream.rangeClosed(1, 3)
                .mapToObj(id -> new UserSubscriptionEmailCode().setId(id))
                .collect(Collectors.toList());
        testUserSubscriptionEmailCode = testUserSubscriptionEmailCodeList.iterator().next();
    }

    @Test
    public void checkEmailCode() {
        doThrow(new RuntimeException()).when(userSubscriptionEmailCodeControllerMock).checkEmailCode(testUserSubscriptionEmailCode.getCode());
        userSubscriptionEmailCodeControllerMock.checkEmailCode(USER_SUBSCRIPTION_EMAIL_CODE_GET_BY_CODE);
        verify(userSubscriptionEmailCodeControllerMock, times(1)).checkEmailCode(USER_SUBSCRIPTION_EMAIL_CODE_GET_BY_CODE);
    }
}
