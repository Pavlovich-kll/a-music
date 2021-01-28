package com.musicapp.service.impl;

import com.musicapp.domain.FriendInvite;
import com.musicapp.domain.FriendStatus;
import com.musicapp.domain.User;
import com.musicapp.dto.UserDto;
import com.musicapp.exception.FriendInviteException;
import com.musicapp.exception.NotFoundException;
import com.musicapp.mapper.UserMapper;
import com.musicapp.repository.FriendInviteRepository;
import com.musicapp.service.FriendService;
import com.musicapp.util.constants.MessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса для управления списком друзей.
 *
 * @author a.nagovicyn
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FriendServiceImpl implements FriendService {

    private final FriendInviteRepository friendInviteRepository;
    private final UserMapper userMapper;

    @Override
    public FriendInvite makeInvite(User initiator, User acceptor) {
        if (friendInviteRepository.existsByUserInitiatorAndUserAcceptor(initiator, acceptor)) {
            log.error("Friend invite already exists.");
            throw new FriendInviteException("friendInvite.request.exists", "id");
        }

        FriendInvite invite = new FriendInvite()
                .setUserInitiator(initiator)
                .setUserAcceptor(acceptor)
                .setStatus(FriendStatus.PENDING);
        log.info("Friend invite has been made.");
        return friendInviteRepository.save(invite);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Page<UserDto> getAllFriends(User user, Pageable pageable) {
        Page<User> users = friendInviteRepository.findAllFriends(user.getId(), FriendStatus.ADDED, pageable);
        log.info("List of all friends has been sent.");
        return users.map(userMapper::map);
    }

    @Override
    public FriendInvite acceptFriend(User acceptor, User initiator) {
        FriendInvite invite = friendInviteRepository.findByUserInitiatorAndUserAcceptor(initiator, acceptor)
                .orElseThrow(() -> {
                    log.error("Invite initiator has not been found.");
                    return new NotFoundException(MessageConstants.INVITE_INITIATOR_NOT_FOUND, "userInitiator");
                });

        if (!invite.getStatus().equals(FriendStatus.PENDING)) {
            log.error("Invite with status pending has not been found.");
            throw new NotFoundException(MessageConstants.INVITE_STATUS_NOT_FOUND, "status");
        }

        invite.setStatus(FriendStatus.ADDED);
        friendInviteRepository.save(invite);
        log.info("Friend invite has been accepted.");
        FriendInvite acceptedInvite = new FriendInvite()
                .setUserAcceptor(initiator)
                .setUserInitiator(acceptor)
                .setStatus(FriendStatus.ADDED);

        return friendInviteRepository.save(acceptedInvite);
    }

    @Override
    public FriendInvite deleteFriend(User initiator, User deleting) {
        FriendInvite addedFriendInvite = friendInviteRepository.findByUserInitiatorAndUserAcceptor(initiator, deleting)
                .orElseThrow(() -> new NotFoundException(MessageConstants.INVITE_ACCEPTOR_NOT_FOUND, "userAcceptor"));

        if (!addedFriendInvite.getStatus().equals(FriendStatus.ADDED)) {
            throw new NotFoundException(MessageConstants.INVITE_STATUS_NOT_FOUND, "status");
        }

        friendInviteRepository.delete(addedFriendInvite);
        FriendInvite deletingFriendInvite = friendInviteRepository.findByUserInitiatorAndUserAcceptor(deleting, initiator)
                .orElseThrow(() -> new NotFoundException(MessageConstants.INVITE_ACCEPTOR_NOT_FOUND, "userAcceptor"));
        deletingFriendInvite.setStatus(FriendStatus.PENDING);
        log.info("Friend has been deleted.");
        return deletingFriendInvite;
    }
}
