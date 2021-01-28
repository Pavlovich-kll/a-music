package com.musicapp.web.controller;

import com.musicapp.domain.Subscription;
import com.musicapp.mapper.SubscriptionMapper;
import com.musicapp.mapper.SubscriptionMapperImpl;
import com.musicapp.service.SubscriptionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SubscriptionController.class)
public class SubscriptionControllerTest extends AbstractControllerTest {

    @TestConfiguration
    static class Configuration {

        @Bean
        public SubscriptionMapper subscriptionMapper() {
            return new SubscriptionMapperImpl();
        }
    }

    private static final String SUBSCRIPTION_GET_BY_ID = "/subscriptions/1";
    private static final String SUBSCRIPTION_GET_ALL = "/subscriptions";

    @MockBean
    SubscriptionService subscriptionServiceMock;
    @Autowired
    private MockMvc mockMvc;
    private Subscription testSubscription;
    private List<Subscription> testSubscriptionList;

    @Before
    public void setUp() {
        testSubscriptionList = LongStream.rangeClosed(1, 3)
                .mapToObj(id -> new Subscription().setId(id))
                .collect(Collectors.toList());
        testSubscription = testSubscriptionList.iterator().next();
    }

    @Test
    public void getAllSubscription() throws Exception {
        when(subscriptionServiceMock.getAllSubscriptions()).thenReturn(testSubscriptionList);

        performGet(SUBSCRIPTION_GET_ALL).andExpect(status().isOk());
    }

    @Test
    public void getSubscriptionById() throws Exception {
        when(subscriptionServiceMock.getSubscriptionById(testSubscription.getId()))
                .thenReturn(java.util.Optional.ofNullable(testSubscription));

        mockMvc.perform(get(SUBSCRIPTION_GET_BY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(is(testSubscription.getId()), Long.class));

    }
}
