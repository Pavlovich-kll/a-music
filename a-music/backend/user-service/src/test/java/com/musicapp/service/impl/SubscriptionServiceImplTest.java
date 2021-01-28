package com.musicapp.service.impl;

import com.musicapp.domain.Subscription;
import com.musicapp.exception.NotFoundException;
import com.musicapp.repository.SubscriptionRepository;
import com.musicapp.service.SubscriptionService;
import com.musicapp.util.constants.MessageConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepositoryMock;
    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;
    private List<Subscription> testSubscriptionList;
    private Subscription testSubscription;

    @Before
    public void setUp() {
        testSubscriptionList = LongStream.rangeClosed(1, 3)
                .mapToObj(id -> {
                    Subscription subscription = new Subscription();
                    subscription.setId(id);
                    return subscription;
                })
                .collect(Collectors.toList());

        testSubscription = testSubscriptionList.iterator().next();
    }

    @Test
    public void getAllSubscriptions() {
        when(subscriptionRepositoryMock.findAll()).thenReturn(testSubscriptionList);

        assertEquals(testSubscriptionList, subscriptionService.getAllSubscriptions());
    }

    @Test
    public void getSubscriptionById() {
        when(subscriptionRepositoryMock.findById(testSubscription.getId())).thenReturn(Optional.of(testSubscription));

        Subscription subscription = subscriptionService.getSubscriptionById(testSubscription.getId()).orElseThrow(() ->
                new NotFoundException(MessageConstants.SUBSCRIPTION_ID_NOT_FOUND, ""));

        assertEquals(testSubscription, subscription);
    }
}