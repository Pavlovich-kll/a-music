package com.musicapp.service.impl;

import com.musicapp.domain.Role;
import com.musicapp.security.AuthorizedUser;
import com.musicapp.service.TokenService;
import com.musicapp.util.constants.ClaimConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с jwt токеном.
 *
 * @author evgeniycheban
 */
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final String secret;
    private final Long expiration;

    @Override
    public AuthorizedUser getAuthorizedUser(String token) {
        Claims claims = getClaims(token);

        Long id = claims.get(ClaimConstants.ID, Long.class);

        String username = claims.get(ClaimConstants.USERNAME, String.class);

        List<?> roles = claims.get(ClaimConstants.ROLES, List.class);
        Set<Role> authorities = roles.stream()
                .map(role -> Role.valueOf((String) role))
                .collect(Collectors.toSet());

        String phone = claims.get(ClaimConstants.VERIFIED_PHONE, String.class);
        String email = claims.get(ClaimConstants.VERIFIED_EMAIL, String.class);
        log.info("Authorizing user");
        return new AuthorizedUser(id, username, authorities, phone, email);
    }

    @Override
    public String generate(AuthorizedUser authorizedUser) {
        Claims claims = Jwts.claims();
        claims.put(ClaimConstants.ID, authorizedUser.getId());
        claims.put(ClaimConstants.USERNAME, authorizedUser.getUsername());
        claims.put(ClaimConstants.ROLES, authorizedUser.getAuthorities());
        claims.put(ClaimConstants.VERIFIED_PHONE, authorizedUser.getPhone());
        claims.put(ClaimConstants.VERIFIED_EMAIL, authorizedUser.getEmail());
        log.info("Generating token");
        return generate(claims);
    }

    @Override
    public String generate(Claims claims) {
        log.info("Generating token");
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @Override
    public String generate(OAuth2User user) {
        Claims claims = Jwts.claims();
        claims.putAll(user.getAttributes());
        log.info("Generating token");
        return generate(claims);
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
