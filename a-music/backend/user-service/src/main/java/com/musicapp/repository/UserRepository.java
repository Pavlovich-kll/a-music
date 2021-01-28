package com.musicapp.repository;

import com.musicapp.domain.Social;
import com.musicapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Репоизторий для работы с таблицей пользователей.
 *
 * @author evgeniycheban
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Возвращает пользователя по номеру телефона.
     *
     * @param phone номер телефона пользователя
     * @return Optional пользователь
     */
    Optional<User> findByPhone(String phone);

    /**
     * Проверяет существование пользователя по номеру телефона.
     *
     * @param phone номер телефона пользователя
     * @return true если пользователь существует
     */
    boolean existsByPhone(String phone);

    /**
     * Проверяет существование пользователя по номеру телефона.
     *
     * @param email e-mail пользователя
     * @return true если пользователь существует
     */
    boolean existsByEmail(String email);

    /**
     * Возвращает Optional пользователя по email
     *
     * @param email пользователя
     * @return Optional пользователь
     */
    Optional<User> findByEmail(String email);

    /**
     * Возвращает Optional пользователя по номеру телефона
     *
     * @param phone номер телефона пользователя
     * @return Optional пользователя
     */
    Optional<User> findUserByPhone(String phone);

    /**
     * Проверяет существование пользователя по email и сервису авторизации
     *
     * @param email  e-mail пользователя
     * @param social сервис авторизации
     * @return true, если существует
     */
    boolean existsByEmailAndSocial(String email, Social social);

    /**
     * Проверяет существование пользователя по имени пользователя и сервису авторизации
     *
     * @param username имя пользователя
     * @param social   сервис авторизации
     * @return true, если существует
     */
    boolean existsByUsernameAndSocial(String username, Social social);

    /**
     * Проверяет существование пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return true если пользователь существует
     */
    boolean existsByUsername(String username);

    /**
     * Ищет по имени пользователя
     *
     * @param userName - имя пользователя
     * @return - объект пользователя
     */
    User findByUsername(String userName);

    /**
     * Ищет всех пользователей по id подписки
     *
     * @param id - id подписки
     * @return - список пользователей
     */
    List<User> findAllByUserSubscriptionId(Long id);

    /**
     * Находит список пользователей по списку id пользователей.
     *
     * @param userIds - список id пользователей
     * @return - список пользователей
     */
    @Query(value = "select u from User u where u.id in :userIds")
    List<User> findByUserIds(@Param("userIds") Collection<Long> userIds);
}
