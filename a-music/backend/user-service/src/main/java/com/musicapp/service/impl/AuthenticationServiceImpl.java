package com.musicapp.service.impl;

import com.musicapp.security.AuthorizedUser;
import com.musicapp.service.AuthenticationService;
import com.musicapp.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса аутентификации.
 *
 * @author evgeniycheban
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public String authenticate(String username, String password) {
        Authentication token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(token);
        log.info("User " + username + " has been authenticated");
        return tokenService.generate((AuthorizedUser) authentication.getPrincipal());
    }
}
