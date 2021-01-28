package com.musicapp.security.filter;

import com.musicapp.security.AuthorizedUser;
import com.musicapp.security.context.TokenContextHolder;
import com.musicapp.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Фильтр для проверки jwt токена в заголовке запроса.
 *
 * @author evgeniycheban
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";

    private final TokenService tokenService;

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(TOKEN_PREFIX, "");
        TokenContextHolder.setAuthenticationToken(token);

        try {
            AuthorizedUser authorizedUser = tokenService.getAuthorizedUser(token);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authorizedUser, null, authorizedUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (ExpiredJwtException e) {
            log.warn("Token {} expired", token, e);
        } catch (MalformedJwtException e) {
            log.warn("Token {} incorrect", token, e);
        }

        chain.doFilter(request, response);
    }
}
