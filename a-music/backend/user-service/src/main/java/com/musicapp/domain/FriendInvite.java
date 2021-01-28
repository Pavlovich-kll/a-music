package com.musicapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Сущность заявки в друзья
 *
 * @author a.nagovicyn
 */
@Entity
@Table(name = "friends")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class FriendInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "friend_id")
    private User userInitiator;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User userAcceptor;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private FriendStatus status;
}
