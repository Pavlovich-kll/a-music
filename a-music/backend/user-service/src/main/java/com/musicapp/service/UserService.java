package com.musicapp.service;

import com.musicapp.domain.User;
import com.musicapp.dto.*;
import com.musicapp.exception.NotFoundException;
import com.musicapp.validation.UniqueValueChecker;

import java.util.List;
import java.util.function.Consumer;

/**
 * Интерфейс сервиса для управления пользователями.
 *
 * @author evgeniycheban
 */
public interface UserService extends UniqueValueChecker {

    /**
     * Создаёт нового пользователя.
     *
     * @param userDto      новый пользователь
     * @param userConsumer callback с пользователем
     * @return профиль пользователя
     */
    UserDto createUser(UserCreateDto userDto, Consumer<User> userConsumer);

    /**
     * Проверяет существование пользователя по номеру телефона.
     *
     * @param phone номер телефона пользователя
     * @return true если пользователь существует
     */
    boolean checkPhone(String phone);

    boolean checkUser(Long id);

    boolean checkPasswords(Long id, UserPasswordPatchDto userPasswordPatchDto);

    /**
     * Возвращает профиль пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return профиля  пользователя
     * @throws NotFoundException в случае если пользователь не найден
     */
    UserDto getProfile(Long id);

    UserDto updateUser(Long id, UserPatchDto userPatchDto);

    /**
     * Обновляет номер телефона существующего пользователя.
     *
     * @param id       идентификатор пользователя
     * @param phoneDto DTO нового номера телефона
     * @return DTO профиля пользователя с обновленным номером телефона
     * @throws NotFoundException в случае если пользователь не найден
     * @author alexandrkudinov
     */
    UserDto updateUser(Long id, PhoneDto phoneDto);

    /**
     * Метод обновляет пароль существующего пользователя.
     *
     * @param id                   идентификатор пользователя
     * @param userPasswordPatchDto DTO сущности пароля пользователя с новыми данными
     * @return DTO профиля пользователя с обновленным паролем
     * @throws NotFoundException в случае если пользователь не найден
     * @author r.zamoiski
     */
    UserDto updateUser(Long id, UserPasswordPatchDto userPasswordPatchDto);

    /**
     * Возвращает прфоиль пользователя по e-mail
     *
     * @param email e-mail пользователя
     * @return true если пользователь существует
     */
    boolean checkEmail(String email);

    /**
     * Возвращает пользователя по id
     *
     * @param id пользователя
     * @return Пользователь
     * @throws NotFoundException, если пользователь не найден
     */
    User getById(Long id);

    /**
     * Блокирует пользователя по id
     *
     * @param id пользователя
     */
    void blockUser(Long id);

    /**
     * Разблоикрет пользователя по id
     *
     * @param id пользователя
     */
    void unblockUser(Long id);

    /**
     * Удаляет пользователя по id
     *
     * @param id пользователя
     */
    void deleteUser(Long id);

    /**
     * Ищет и возвращает список всех пользователей
     * @return - список всех пользователей
     */
    List<User> findAll();

    /**
     * Ищет всех пользователей по id userSubscription
     * @param id - userSubscription id
     * @return - список всех пользователей
     */
    List<User> findAllByUserSubscriptionId(Long id);

    /**
     * Поиск объекта пользователя по имени пользователя
     * @param userName - имя пользователя
     * @return - объект пользователя
     */
    User findByUsername(String userName);

    /**
     * Находит список пользователей по списку id пользователей.
     * @param userIds - список id пользователей
     * @return - список пользователей
     */
    List<User> findByUserIds(List<Long> userIds);
}
