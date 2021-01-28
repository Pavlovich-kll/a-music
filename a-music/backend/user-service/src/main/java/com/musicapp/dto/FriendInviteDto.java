package com.musicapp.dto;

import com.musicapp.domain.FriendStatus;
import lombok.Builder;
import lombok.Value;

/**
 * DTO представление сущности заявки в друзья
 *
 * @author a.nagovicyn
 */
@Value
@Builder(setterPrefix = "with")
public class FriendInviteDto {

    String initiatorUsername;
    String acceptorUsername;
    FriendStatus status;
}
