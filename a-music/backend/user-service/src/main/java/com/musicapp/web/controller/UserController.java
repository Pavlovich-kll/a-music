package com.musicapp.web.controller;

import com.musicapp.domain.DomainEvent;
import com.musicapp.domain.DomainEventType;
import com.musicapp.domain.User;
import com.musicapp.dto.*;
import com.musicapp.exception.NotFoundException;
import com.musicapp.security.AuthorizedUser;
import com.musicapp.service.UserService;
import com.musicapp.service.UserStreamService;
import com.musicapp.util.constants.MessageConstants;
import com.musicapp.validation.group.UsernameNotBlankGroup;
import com.musicapp.validation.sequence.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Методы для работы с пользователями.
 *
 * @author evgeniycheban
 */
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin
@Api
public class UserController {

    private final UserService userService;
    private final UserStreamService streamService;

    /**
     * Метод валидирует и создаёт нового пользователя с отправкой в message broker.
     *
     * @param userCreateDto dto сущности пользователя
     * @return DTO профиля пользователя
     */
    @ApiOperation(" Метод для создания нового пользователя через номер телефона.")
    @PostMapping("/create-by-phone")
    public ResponseEntity<UserDto> createByPhone(@Validated({
            PhoneVerifiedSequence.class,
            UsernameNotBlankGroup.class,
            SamePasswordSequence.class
    }) @RequestBody UserCreateDto userCreateDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(userCreateDto, user -> {
                    Message<DomainEvent<User>> message = MessageBuilder
                            .withPayload(new DomainEvent<>(user, DomainEventType.USER_CREATED))
                            .build();
                    streamService.output().send(message);
                }));
    }

    /**
     * Метод валидирует и создаёт нового пользователя по e-mail с отправкой в message broker.
     *
     * @param userCreateDto dto сущности пользователя
     */
    @ApiOperation(" Метод для создания нового пользователя через почту.")
    @PostMapping("/create-by-email")
    public ResponseEntity<UserDto> createByEmail(@Validated({
            EmailVerifiedSequence.class,
            UsernameNotBlankGroup.class,
            SamePasswordSequence.class
    }) @RequestBody UserCreateDto userCreateDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(userCreateDto, user -> {
                    Message<DomainEvent<User>> message = MessageBuilder
                            .withPayload(new DomainEvent<>(user, DomainEventType.USER_CREATED))
                            .build();
                    streamService.output().send(message);
                }));
    }

    /**
     * Метод валидирует и проверяет существование пользователя по e-mail
     *
     * @param emailDto dto email пользователя
     * @return ответ с признаком существования пользователя
     */
    @PostMapping("/check-email")
    public ResponseEntity<SimpleResponse> checkEmail(@Validated(EmailFormatSequence.class) @RequestBody EmailDto emailDto) {
        return ResponseEntity.ok(SimpleResponse.builder()
                .withPropertyName("exists")
                .withPropertyValue(userService.checkEmail(emailDto.getEmail()))
                .build());
    }

    /**
     * Метод валидирует и проверяет существование пользователя по номеру телефона.
     *
     * @param phoneDto dto номера телефона пользователя
     * @return ответ с признаком существования пользователя
     */
    @PostMapping("/check-phone")
    public ResponseEntity<SimpleResponse> checkPhone(@Validated(PhoneFormatSequence.class) @RequestBody PhoneDto phoneDto) {
        return ResponseEntity.ok(SimpleResponse.builder()
                .withPropertyName("exists")
                .withPropertyValue(userService.checkPhone(phoneDto.getPhone()))
                .build());
    }

    /**
     * Метод возвращает профиль пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return DTO профиля пользователя
     */
    @GetMapping("/{id}")
    @ApiOperation("Метод для получения пользователя по id.")
    public ResponseEntity<UserDto> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getProfile(id));
    }

    /**
     * Метод обновляет данные существующего пользователя.
     *
     * @param id           идентификатор пользователя
     * @param userPatchDto dto сущности пользователя с новыми данными
     * @return DTO профиля пользователя с обновленными данными
     */
    @ApiOperation("Метод для обновления данных существующего пользователя.")
    @PatchMapping("/update/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserPatchDto userPatchDto) {
        return ResponseEntity.ok(userService.updateUser(id, userPatchDto));
    }

    /**
     * Метод обновляет пароль существующего пользователя.
     *
     * @param userPasswordPatchDto dto сущности паролей пользователя с новыми данными
     * @param authorizedUser       авторизованный пользователь
     * @return DTO профиля пользователя с обновленным паролем
     * @author r.zamoiski
     */
    @ApiOperation("Метод для обновления пароля авторизованного пользователя.")
    @PatchMapping("/update-password")
    public ResponseEntity<UserDto> updatePassword(@RequestBody @Validated UserPasswordPatchDto userPasswordPatchDto, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Long userId = authorizedUser.getId();
        if (userService.checkPasswords(userId, userPasswordPatchDto)) {
            return ResponseEntity.ok(userService.updateUser(userId, userPasswordPatchDto));
        } else {
            throw new NotFoundException(MessageConstants.USER_REPEAT_PASSWORD_NOT_MATCH, "oldPassword");
        }
    }

    /**
     * Метод обновляет номер телефона существующего пользователя.
     *
     * @param phoneDto       dto номера телефона с новыми данными
     * @param authorizedUser авторизованный пользователь
     * @return DTO профиля пользователя с обновленным номером телефона
     * @author alexandrkudinov
     */
    @ApiOperation("Метод для обновления номера телефона авторизованного пользователя.")
    @PatchMapping("/update-phone")
    public ResponseEntity<UserDto> updatePhone(
            @RequestBody @Validated(PhoneVerifiedSequence.class) PhoneDto phoneDto,
            @AuthenticationPrincipal AuthorizedUser authorizedUser) {

        Long userId = authorizedUser.getId();

        return ResponseEntity.ok(userService.updateUser(userId, phoneDto));
    }

    /**
     * Метод блокирует (не удаляет) пользователя
     *
     * @param id пользователя
     */
    @ApiOperation("Метод для блокиировки пользователя.")
    @PostMapping("/block-user")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Void> blockUser(@RequestBody Long id) {
        userService.blockUser(id);

        return ResponseEntity.ok().build();
    }

    /**
     * Метод разблокирует пользователя
     *
     * @param id пользователя
     */
    @ApiOperation("Метод для разблокиировки пользователя.")
    @PostMapping("/unblock-user")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Void> unBlockUser(@RequestBody Long id) {
        userService.unblockUser(id);

        return ResponseEntity.ok().build();
    }

    /**
     * Метод удаляет существующего пользователя.
     *
     * @param userId пользователя
     */
    @ApiOperation("Метод для удаления пользователя.")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.ok().build();
    }
}
