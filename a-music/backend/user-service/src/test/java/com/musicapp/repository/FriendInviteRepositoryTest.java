package com.musicapp.repository;

import com.musicapp.domain.FriendInvite;
import com.musicapp.domain.FriendStatus;
import com.musicapp.domain.User;
import com.musicapp.repository.initializer.ContainerDB;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SqlGroup({
        @Sql("/db/sql/countriesInsert.sql"),
        @Sql("/db/sql/citiesInsert.sql"),
        @Sql("/db/sql/userInsert.sql"),
        @Sql("/db/sql/friendsInsert.sql")
})
public class FriendInviteRepositoryTest extends ContainerDB {

    @Autowired
    FriendInviteRepository friendInviteRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAllFriends() {
        Page<User> friends = friendInviteRepository.findAllFriends(2L, FriendStatus.PENDING, Pageable.unpaged());
        assertTrue(friends.getTotalPages() != 0);
    }

    @Test
    public void findByUserInitiatorAndUserAcceptor() {
        Optional<FriendInvite> invite = friendInviteRepository.findByUserInitiatorAndUserAcceptor(getInitiator(), getAcceptor());
        assertTrue(invite.isPresent());
        assertEquals(invite.get(), getActualInvite());
    }

    @Test
    public void existsByUserInitiatorAndUserAcceptor() {
        boolean exists = friendInviteRepository.existsByUserInitiatorAndUserAcceptor(getInitiator(), getAcceptor());
        assertTrue(exists);
    }

    private User getInitiator() {
        return userRepository.getOne(2L);
    }

    private User getAcceptor() {
        return userRepository.getOne(1L);
    }

    private FriendInvite getActualInvite() {
        FriendInvite initiator = new FriendInvite();
        initiator.setStatus(FriendStatus.PENDING);
        initiator.setUserAcceptor(getAcceptor());
        initiator.setUserInitiator(getInitiator());
        return initiator;
    }
}