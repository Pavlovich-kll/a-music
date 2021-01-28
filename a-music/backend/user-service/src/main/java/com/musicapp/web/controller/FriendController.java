package com.musicapp.web.controller;

import com.musicapp.domain.FriendInvite;
import com.musicapp.domain.User;
import com.musicapp.dto.FriendInviteDto;
import com.musicapp.dto.UserDto;
import com.musicapp.security.AuthorizedUser;
import com.musicapp.service.FriendService;
import com.musicapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Методы для управления списком друзей.
 *
 * @author evgeniycheban
 */
@Controller
@RequestMapping("/friend")
@RequiredArgsConstructor
@CrossOrigin
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;

    /**
     * Метод для отправки заявки на добавление в друзья
     *
     * @param id             пользователя, которому направляется заявка
     * @param authorizedUser авторизованный пользователь
     * @return dto сущности созданной заявки
     */
    @PostMapping("/add-friend/{id}")
    public ResponseEntity<FriendInviteDto> addFriend(@PathVariable long id, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        User acceptor = userService.getById(id);
        User initiator = userService.getById(authorizedUser.getId());

        return ResponseEntity.ok(FriendInviteDto.builder()
                .withAcceptorUsername(acceptor.getUsername())
                .withInitiatorUsername(initiator.getUsername())
                .withStatus(friendService.makeInvite(initiator, acceptor).getStatus())
                .build());
    }

    /**
     * Метод для удаления пользователя из друзей
     *
     * @param id             удаляемого пользователя
     * @param authorizedUser авторизованный пользователь
     * @return dto сущноси заявки со стороны удаляемого пользователя
     */
    @DeleteMapping("/delete-friend/{id}")
    public ResponseEntity<FriendInviteDto> deleteFriend(@PathVariable long id, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        User deleting = userService.getById(id);
        User initiator = userService.getById(authorizedUser.getId());
        FriendInvite friendInvite = friendService.deleteFriend(initiator, deleting);

        return ResponseEntity.ok(FriendInviteDto.builder()
                .withAcceptorUsername(friendInvite.getUserAcceptor().getUsername())
                .withInitiatorUsername(friendInvite.getUserInitiator().getUsername())
                .withStatus(friendInvite.getStatus())
                .build());
    }

    /**
     * Метод для подтверждения заявки в друзья
     *
     * @param id             id пользователя, заявка которого подтверждается
     * @param authorizedUser авторизованный пользователь
     * @return dto сущность подтвержденной заявки в друзья
     */
    @PostMapping("/accept-friend/{id}")
    public ResponseEntity<FriendInviteDto> acceptInvite(@PathVariable long id, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        User initiator = userService.getById(id);
        User acceptor = userService.getById(authorizedUser.getId());
        FriendInvite friendInvite = friendService.acceptFriend(acceptor, initiator);

        return ResponseEntity.ok(FriendInviteDto.builder()
                .withAcceptorUsername(friendInvite.getUserAcceptor().getUsername())
                .withInitiatorUsername(friendInvite.getUserInitiator().getUsername())
                .withStatus(friendInvite.getStatus())
                .build());
    }

    /**
     * Метод для отображения списка друзей
     *
     * @param id       id пользователя, друзья которого необходимо отобразить
     * @param pageable параметр сортировки страниц с друзьями
     * @return Список профилей друзей пользователя
     */
    @Deprecated
    @PostMapping("/all-added-friends/{id}")
    public ResponseEntity<Page<UserDto>> getFriends(
            @PathVariable Long id,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {

        User user = userService.getById(id);

        return ResponseEntity.ok(friendService.getAllFriends(user, pageable));
    }

    @GetMapping("/all-added-friends/{id}")
    public ResponseEntity<Page<UserDto>> getAllAddedFriends(
            @PathVariable Long id,
            @PageableDefault Pageable pageable) {

        if (Objects.isNull(userService.getById(id))) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(friendService.getAllFriends(userService.getById(id), pageable));
    }
}
