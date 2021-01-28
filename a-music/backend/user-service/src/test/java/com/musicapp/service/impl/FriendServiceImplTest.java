package com.musicapp.service.impl;

import com.musicapp.domain.FriendInvite;
import com.musicapp.domain.FriendStatus;
import com.musicapp.domain.User;
import com.musicapp.exception.FriendInviteException;
import com.musicapp.exception.NotFoundException;
import com.musicapp.mapper.UserMapperImpl;
import com.musicapp.repository.FriendInviteRepository;
import com.musicapp.service.FriendService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FriendServiceImplTest {

    @Mock
    private FriendInviteRepository friendInviteRepositoryMock;
    private FriendService friendService;
    private List<User> testUsers;
    private User testUser1;
    private User testUser2;
    private FriendInvite testFriendInvite;

    @Before
    public void setUp() {
        friendService = new FriendServiceImpl(friendInviteRepositoryMock, new UserMapperImpl());

        testUsers = LongStream.iterate(1, seed -> seed + 1)
                .limit(10)
                .mapToObj(id -> new User()
                        .setId(id)
                        .setFirstName("" + id))
                .collect(Collectors.toList());

        Iterator<User> iterator = testUsers.iterator();
        testUser1 = iterator.next();
        testUser2 = iterator.next();

        testFriendInvite = new FriendInvite()
                .setId(1L)
                .setUserInitiator(testUser1)
                .setUserAcceptor(testUser2)
                .setStatus(FriendStatus.PENDING);
    }

    @Test(expected = FriendInviteException.class)
    public void givenFriendInviteExists_whenMakeInvite_thenThrowFriendInviteException() {
        when(friendInviteRepositoryMock.existsByUserInitiatorAndUserAcceptor(any(), any())).thenReturn(true);

        friendService.makeInvite(testUser1, testUser2);
    }

    @Test
    public void givenFriendInviteDoNotExist_whenMakeInvite_thenReturnTestFriendInvite() {
        when(friendInviteRepositoryMock.save(any())).thenReturn(testFriendInvite);

        assertEquals(testFriendInvite, friendService.makeInvite(testUser1, testUser2));
    }

    @Test
    public void whenGetAllFriends_thenReturnPageOfTestUsers() {
        when(friendInviteRepositoryMock.findAllFriends(anyLong(), any(), any())).thenReturn(new PageImpl<>(testUsers));

        assertEquals(testUsers.size(),
                friendService.getAllFriends(testUser1, PageRequest.of(0, testUsers.size())).getContent().size());
    }

    @Test(expected = NotFoundException.class)
    public void givenFriendInviteDoNotExist_whenAcceptFriend_thenThrowNotFoundException() {
        friendService.acceptFriend(testUser1, testUser2);
    }

    @Test(expected = NotFoundException.class)
    public void givenInviteStatusAdded_whenAcceptFriend_thenThrowNotFoundException() {
        when(friendInviteRepositoryMock.findByUserInitiatorAndUserAcceptor(any(), any()))
                .thenReturn(Optional.of(new FriendInvite().setStatus(FriendStatus.ADDED)));

        friendService.acceptFriend(testUser1, testUser2);
    }

    @Test(expected = NotFoundException.class)
    public void givenInviteStatusDeleted_whenAcceptFriend_thenThrowNotFoundException() {
        when(friendInviteRepositoryMock.findByUserInitiatorAndUserAcceptor(any(), any()))
                .thenReturn(Optional.of(new FriendInvite().setStatus(FriendStatus.DELETED)));

        friendService.acceptFriend(testUser1, testUser2);
    }

    @Test
    public void givenInviteStatusPending_whenAcceptFriend_thenReturnFriendInvite() {
        when(friendInviteRepositoryMock.findByUserInitiatorAndUserAcceptor(any(), any()))
                .thenReturn(Optional.of(testFriendInvite));
        when(friendInviteRepositoryMock.save(any())).thenReturn(testFriendInvite);

        assertNotNull(friendService.acceptFriend(testUser1, testUser2));
        verify(friendInviteRepositoryMock, times(2)).save(any());
    }

    @Test(expected = NotFoundException.class)
    public void givenFriendInviteDoNotExist_whenDeleteFriend_thenThrowNotFoundException() {
        friendService.deleteFriend(testUser1, testUser2);
    }

    @Test(expected = NotFoundException.class)
    public void givenInviteStatusPending_whenDeleteFriend_thenThrowNotFoundException() {
        when(friendInviteRepositoryMock.findByUserInitiatorAndUserAcceptor(any(), any()))
                .thenReturn(Optional.of(new FriendInvite().setStatus(FriendStatus.PENDING)));

        friendService.deleteFriend(testUser1, testUser2);
    }

    @Test(expected = NotFoundException.class)
    public void givenInviteStatusDeleted_whenDeleteFriend_thenThrowNotFoundException() {
        when(friendInviteRepositoryMock.findByUserInitiatorAndUserAcceptor(any(), any()))
                .thenReturn(Optional.of(new FriendInvite().setStatus(FriendStatus.DELETED)));

        friendService.deleteFriend(testUser1, testUser2);
    }

    @Test(expected = NotFoundException.class)
    public void givenSecondFriendInviteDoNotExist_whenDeleteFriend_thenThrowNotFoundException() {
        when(friendInviteRepositoryMock.findByUserInitiatorAndUserAcceptor(testUser1, testUser2))
                .thenReturn(Optional.of(new FriendInvite().setStatus(FriendStatus.ADDED)));

        friendService.deleteFriend(testUser1, testUser2);
    }

    @Test
    public void givenSecondFriendInviteExists_whenDeleteFriend_thenReturnTestFriendInvite() {
        when(friendInviteRepositoryMock.findByUserInitiatorAndUserAcceptor(testUser1, testUser2))
                .thenReturn(Optional.of(new FriendInvite().setStatus(FriendStatus.ADDED)));
        when(friendInviteRepositoryMock.findByUserInitiatorAndUserAcceptor(testUser2, testUser1))
                .thenReturn(Optional.of(testFriendInvite));

        assertEquals(FriendStatus.PENDING, friendService.deleteFriend(testUser1, testUser2).getStatus());
    }
}

