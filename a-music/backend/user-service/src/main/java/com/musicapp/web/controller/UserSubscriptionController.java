package com.musicapp.web.controller;

import com.musicapp.dto.UserDto;
import com.musicapp.dto.UserSubscriptionDto;
import com.musicapp.service.UserSubscriptionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user-subscriptions")
@RequiredArgsConstructor
public class UserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;


    /**
     * Метод для создания (покупки) нового новой подписки.
     *
     * @param userSubscriptionDto - объект подписки
     */
    @ApiOperation("Метод для создания (покупки) новой подписки.")
    @PostMapping("/buy")
    public ResponseEntity<UserSubscriptionDto> buySubscription(
            @RequestBody UserSubscriptionDto userSubscriptionDto) {
        return ResponseEntity.ok(userSubscriptionService.buySubscription(userSubscriptionDto));
    }

    /**
     * Метод добавляет пользователя в существующую подписку.
     *
     * @param principal      - авторизованный пользователь.
     * @param invitedUsersId - список id выбранных пользователей.
     */
    @ApiOperation("Метод добавляет пользователя в существующую подписку.")
    @PostMapping("/add-user")
    public ResponseEntity<UserSubscriptionDto> addUserToSubscription(Principal principal,
                                                                     @RequestBody List<Long> invitedUsersId) {
        UserSubscriptionDto userSubscriptionDto = userSubscriptionService.addUsersToSubscription(principal, invitedUsersId);
        return ResponseEntity.ok(userSubscriptionDto);
    }

    /**
     * Метод для позволяет получить список пользователей включенных в подписку hostUser.
     *
     * @param hostUserId - id пользователя оплатившего подписку.
     */
    @ApiOperation("Метод позволяет получить список пользователей включенных в подписку hostUser.")
    @GetMapping(value = "/get-all-by-hostUser/{id}")
    public ResponseEntity<List<UserDto>> getAllUsersByHostUserId(
            @PathVariable("id") Long hostUserId) {
        List<UserDto> usersByHostUserIdDto = userSubscriptionService.getAllUsersByHostUserId(hostUserId);
        return ResponseEntity.ok(usersByHostUserIdDto);
    }

    /**
     * Мето удаления пользователя из подписки.
     *
     * @param principal - авторизованный пользователь.
     * @param userId    - id удаляемого пользователя.
     */
    @ApiOperation("Метод удаления пользователя из подписки.")
    @DeleteMapping("users/{userId}/subscription")
    public ResponseEntity<Void> deleteUserFromSubscription(Principal principal, @PathVariable Long userId) {
        userSubscriptionService.deleteUserFromSubscription(principal, userId);
        return ResponseEntity.ok().build();
    }
}