package com.musicapp.web.controller;

import com.musicapp.dto.SubscriptionDto;
import com.musicapp.mapper.SubscriptionMapper;
import com.musicapp.service.SubscriptionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionService subscriptionService;

    /**
     * Метод возвращает подписку по id.
     *
     * @param id - id подписки
     * @return subscriptionDto
     */
    @ApiOperation("Метод возвращает подписку по id")
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDto> getSubscriptionById(@PathVariable("id") Long id) {
        return subscriptionService.getSubscriptionById(id)
                .map(subscription -> ResponseEntity.ok(subscriptionMapper.map(subscription)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Метод возвращает список подписок.
     *
     * @return - список SubscriptionDto
     */
    @ApiOperation("Метод возвращает список подписок")
    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> getAllSubscription() {
        List<SubscriptionDto> subscriptionsDto = subscriptionMapper.map(subscriptionService.getAllSubscriptions());
        return ResponseEntity.ok(subscriptionsDto);
    }
}