package com.musicapp.service;

import com.musicapp.domain.FriendInvite;
import com.musicapp.domain.User;
import com.musicapp.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Интерфейс сервиса для управления списком друзей.
 *
 * @author a.nagovicyn
 */
public interface FriendService {

    /**
     * Метод для отправки запроса в друзья
     *
     * @param initiator пользователь, инициирующий запрос
     * @param acceptor  пользователь, в отношении которого инциирован запрос
     * @return сущность заявки в друзья
     */
    FriendInvite makeInvite(User initiator, User acceptor);

    /**
     * Метод для вывода списка всех друзей по-странично
     *
     * @param user     пользователь, друзей которого необходимо получить
     * @param pageable bean, определяющий сортировку, количество записей на странице и т.д.
     * @return список всех друзей пользователя
     */
    Page<UserDto> getAllFriends(User user, Pageable pageable);

    /**
     * Метод для подтверждения заявки в друзья. При подтверждении меняется статус
     * заявки. Также формируется новая заявка, в которой пользователь ранее принимащий заявку
     * выступает в роли иницатора, а отправитель в роли аксептора (создается зеркальная запись в бд).
     * Новая заявка автоматически подтверждается.
     *
     * @param acceptor  пользователь, подтверждающий заявку
     * @param initiator пользователь, отправивший заявку
     * @return сущность подтвержденной заявки в друзья
     */
    FriendInvite acceptFriend(User acceptor, User initiator);

    /**
     * Методя для удаления пользователя из друзей
     *
     * @param initiator пользователь, удаляющий другого пользователя
     * @param deleting  пользователь, которого удаляют из друзей
     * @return заявка со стороны удаляемого пользователя
     */
    FriendInvite deleteFriend(User initiator, User deleting);
}
