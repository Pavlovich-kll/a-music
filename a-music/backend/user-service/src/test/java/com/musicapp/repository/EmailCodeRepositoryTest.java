package com.musicapp.repository;

import com.musicapp.domain.EmailCode;
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
        @Sql("/db/sql/email_codesInsert.sql")
})
public class EmailCodeRepositoryTest extends ContainerDB {

    @Autowired
    EmailCodeRepository emailCodeRepository;

    @Test
    public void findByCode() {
        Optional<EmailCode> someCode = emailCodeRepository.findByCode("someCode");
        assertTrue(someCode.isPresent());
        assertEquals(someCode.get(),getActualCode());
    }

    private EmailCode getActualCode() {
        EmailCode emailCode = new EmailCode();
        emailCode.setId(1L);
        emailCode.setCode("someCode");
        emailCode.setEmail("user@mail.com");
        return emailCode;
    }
}