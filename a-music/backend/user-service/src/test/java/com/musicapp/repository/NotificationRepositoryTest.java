package com.musicapp.repository;

import com.musicapp.domain.Notification;
import com.musicapp.repository.initializer.ContainerDB;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.junit.Assert.*;

@SqlGroup({
        @Sql("/db/sql/countriesInsert.sql"),
        @Sql("/db/sql/citiesInsert.sql"),
        @Sql("/db/sql/userInsert.sql"),
        @Sql("/db/sql/notificationMetaInsert.sql"),
        @Sql("/db/sql/notificationDataInsert.sql")
})
public class NotificationRepositoryTest extends ContainerDB {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationMetadataRepository notificationMetadataRepository;

    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
        notificationRepository.deleteAll();
    }

    @Test
    public void findAllByUser() {
        List<Notification> notifications = notificationRepository.findAllByUser(userRepository.getOne(1L));
        assertFalse(notifications.isEmpty());
        long count = notifications.size();
        assertTrue(count > 1);
    }
}