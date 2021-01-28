package com.musicapp.service.impl;

import com.musicapp.domain.User;
import com.musicapp.dto.UserPasswordPatchDto;
import com.musicapp.exception.NotFoundException;
import com.musicapp.repository.UserRepository;
import com.musicapp.service.ResetPasswordService;
import com.musicapp.service.TokenService;
import com.musicapp.util.constants.ClaimConstants;
import com.musicapp.util.constants.MessageConstants;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса для восстановления пароля.
 *
 * @author a.nagovicyn
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder encoder;

    @Override
    public void changePassword(String token, UserPasswordPatchDto newPasswordDto) {
        User user = checkUser(token);
        String encodedPassword = encoder.encode(newPasswordDto.getNewPassword());
        user.setPassword(encodedPassword);
        log.info("Password has been changed");
        userRepository.save(user);
    }

    /**
     * Метод для провреки наличия пользователя
     *
     * @param token jwt токен
     * @return Возвращает пользователя, если такой существует
     * @throws NotFoundException если пользователь не найден
     */
    private User checkUser(String token) {
        Claims claims = tokenService.getClaims(token);
        long id = claims.get(ClaimConstants.ID, Long.class);
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageConstants.USER_ID_NOT_FOUND, "id"));
    }
}
