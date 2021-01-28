package com.musicapp.service.impl;

import com.musicapp.domain.City;
import com.musicapp.domain.Role;
import com.musicapp.domain.User;
import com.musicapp.dto.*;
import com.musicapp.exception.NotFoundException;
import com.musicapp.mapper.UserMapper;
import com.musicapp.repository.UserRepository;
import com.musicapp.service.CityService;
import com.musicapp.service.UserService;
import com.musicapp.util.constants.MessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Реализация сервиса для управления пользователями.
 *
 * @author evgeniycheban
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CityService cityService;

    @Transactional
    @Override
    public UserDto createUser(UserCreateDto userDto, Consumer<User> userConsumer) {
        User user = userMapper.map(userDto);
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        City city = cityService.getById(userDto.getCityId());
        user.setCity(city);
        userConsumer.accept(userRepository.save(user));
        log.info("User has been created ");
        return userMapper.map(user);
    }

    @Transactional
    @Override
    public boolean checkPhone(String phone) {
        log.info("Checking phone");
        return userRepository.existsByPhone(phone);
    }

    @Transactional
    @Override
    public boolean checkUser(Long id) {
        log.info("Checking user");
        return userRepository.existsById(id);
    }

    @Transactional
    @Override
    public boolean checkPasswords(Long id, UserPasswordPatchDto userPasswordPatchDto) {
        log.info("Checking password");
        return userRepository.findById(id)
                .map(user -> passwordEncoder.matches(userPasswordPatchDto.getOldPassword(), user.getPassword()))
                .orElse(false);
    }

    @Transactional
    @Override
    public UserDto getProfile(Long id) {
        return userMapper.map(userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User has not been found with id : " + id);
                    return new NotFoundException(MessageConstants.USER_ID_NOT_FOUND, "id");
                }));
    }

    @Transactional
    @Override
    public UserDto updateUser(Long id, UserPatchDto userPatchDto) {
        User user = userRepository.findById(id).map(u -> userMapper.updateUser(userPatchDto, u))
                .orElseThrow(() -> {
                    log.error("User has not been found with id : " + id);
                    return new NotFoundException(MessageConstants.USER_ID_NOT_FOUND, "id");
                });
        User updatedUser = userRepository.save(user);
        log.info("User has been updated with id : " + id);
        return userMapper.map(updatedUser);
    }

    @Transactional
    @Override
    public UserDto updateUser(Long id, PhoneDto phoneDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPhone(phoneDto.getPhone());
            User updatedUser = userRepository.save(user);
            log.info("User has been updated with id : " + id);
            return userMapper.map(updatedUser);
        } else {
            log.error("User has not been found with id : " + id);
            throw new NotFoundException(MessageConstants.USER_ID_NOT_FOUND, "id");
        }
    }

    @Transactional
    @Override
    public UserDto updateUser(Long id, UserPasswordPatchDto userPasswordPatchDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(userPasswordPatchDto.getNewPassword()));
            User updatedUser = userRepository.save(user);
            log.info("User has been updated with id : " + id);
            return userMapper.map(updatedUser);
        } else {
            log.error("User has not been found with id : " + id);
            throw new NotFoundException(MessageConstants.USER_ID_NOT_FOUND, "id");
        }
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User has not been found with id : " + id);
                    return new NotFoundException(MessageConstants.USER_ID_NOT_FOUND, "id");
                });
    }

    @Transactional
    @Override
    public boolean checkEmail(String email) {
        log.info("Checking email");
        return userRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public void blockUser(Long id) {
        User user = getById(id);
        user.setEnabled(false);
        log.info("User has been blocked with id : " + id);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void unblockUser(Long id) {
        User user = getById(id);
        user.setEnabled(true);
        log.info("User has been blocked with id : " + id);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            log.info("User has been deleted with id : " + id);
        } else {
            log.error("User has not been found with id : " + id);
            throw new NotFoundException(MessageConstants.USER_ID_NOT_FOUND, "id");
        }
    }

    @Transactional
    @Override
    public boolean isUnique(Object value) {
        if (!Objects.equals(String.class, value.getClass())) {
            return false;
        }

        return Objects.equals(0L, userRepository.count(getUserByEmailOrPhoneSpecification(value)));
    }

    private Specification<User> getUserByEmailOrPhoneSpecification(Object value) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.or(criteriaBuilder.equal(root.get("email"), value),
                        criteriaBuilder.equal(root.get("phone"), value));
    }

    @Transactional
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Transactional
    @Override
    public List<User> findAllByUserSubscriptionId(Long id) {
        return userRepository.findAllByUserSubscriptionId(id);
    }

    @Transactional
    @Override
    public List<User> findByUserIds(List<Long> userIds) {
        return userRepository.findByUserIds(userIds);
    }
}
