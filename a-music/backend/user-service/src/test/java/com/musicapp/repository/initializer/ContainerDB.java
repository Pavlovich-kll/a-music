package com.musicapp.repository.initializer;

import com.musicapp.util.constants.ProfileConstants;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Класс, где создается контейнер для каждого отдельного теста
 *
 * @author Pavlovich_Kirill
 */
@ActiveProfiles(ProfileConstants.TEST)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(value = "classpath:application-test.yml")

public abstract class ContainerDB {

    @ClassRule
    public static TestPostgresContainer postgres = TestPostgresContainer
            .getInstance();
}