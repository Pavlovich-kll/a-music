package com.musicapp.security;

import com.musicapp.domain.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;

/**
 * Авторизованный пользователь.
 *
 * @author evgeniycheban
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class AuthorizedUser extends User {

    private final Long id;
    private final String phone;
    private final String email;

    public AuthorizedUser(Long id, String username, Collection<Role> roles, String phone, String email) {
        this(id, username, "", roles, phone, email);
    }

    public AuthorizedUser(com.musicapp.domain.User user) {
        this(user.getId(), user.getUsername(), user.getPassword(), Collections.singletonList(user.getRole()), user.getPhone(), user.getEmail());
    }

    private AuthorizedUser(Long id, String username, String password, Collection<Role> roles, String phone, String email) {
        super(username, password, roles);
        this.id = id;
        this.phone = phone;
        this.email = email;
    }
}
