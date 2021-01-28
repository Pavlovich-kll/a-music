package com.musicapp.repository;

import com.musicapp.domain.User;
import com.musicapp.repository.initializer.ContainerDB;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Optional;

import static org.junit.Assert.*;

@SqlGroup({
        @Sql("/db/sql/countriesInsert.sql"),
        @Sql("/db/sql/citiesInsert.sql"),
        @Sql("/db/sql/userInsert.sql"),
        @Sql("/db/sql/concertsInsert.sql"),
        @Sql("/db/sql/ticketInsert.sql")
})
public class UserRepositoryTest extends ContainerDB {

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByPhone() {
        Optional<User> userExpected = userRepository.findByPhone("89211111111");
        assertTrue(userExpected.isPresent());
        User phoneActual = userRepository.getOne(1L);
        assertEquals(userExpected.get(), phoneActual);
    }

    @Test
    public void findByEmail() {
        Optional<User> userExpected = userRepository.findByEmail("user@mail.com");
        assertTrue(userExpected.isPresent());
        assertEquals(userExpected.get(), getActualUser());
    }

    @Test
    public void existsByEmailAndSocial() {
        assertTrue(userRepository.existsByEmailAndSocial("user@mail.com", null));
    }

    @Test
    public void existsByUsernameAndSocial() {
        assertTrue(userRepository.existsByUsernameAndSocial("User", null));
    }

    @Test
    public void existsByUsername() {
        assertTrue(userRepository.existsByUsername("User"));
    }

    private User getActualUser() {
        return userRepository.getOne(1L);
    }
}