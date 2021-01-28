package com.musicapp.repository;

import com.musicapp.domain.FriendInvite;
import com.musicapp.domain.FriendStatus;
import com.musicapp.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Репозиторий для работы с таблицей пользователей.
 *
 * @author a.nagovicyn
 */
public interface FriendInviteRepository extends JpaRepository<FriendInvite, Long> {

    /**
     * Возваращает список пользователей
     *
     * @param id       полльзователя, список друзей которого просматривается
     * @param status   статус заявок друзей
     * @param pageable параметр пагинации
     * @return постраничный вывод друзей
     */
    @Query("select f.userAcceptor from FriendInvite f where f.userInitiator.id = :id and f.status = :status")
    Page<User> findAllFriends(@Param("id") Long id, @Param("status") FriendStatus status, Pageable pageable);

    /**
     * Возвращает запрос в друзья от initiator к acceptor
     *
     * @param initiator отправитель запроса
     * @param acceptor  получатель запроса
     * @return запрос в друзья
     */
    Optional<FriendInvite> findByUserInitiatorAndUserAcceptor(User initiator, User acceptor);

    /**
     * Возвращает true, если запрос в друзья от initiator к acceptor существует
     *
     * @param initiator отправитель запроса
     * @param acceptor  получатель запроса
     * @return запрос в друзья
     */
    boolean existsByUserInitiatorAndUserAcceptor(User initiator, User acceptor);
}
