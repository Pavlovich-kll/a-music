package com.musicapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Сущность пользователя.
 *
 * @author evgeniycheban
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "enabled")
    private boolean enabled;
    @Column(name = "social")
    @Enumerated(EnumType.STRING)
    private Social social;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userInitiator")
    @EqualsAndHashCode.Exclude
    private Set<FriendInvite> invitesByThisUser;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userAcceptor")
    @EqualsAndHashCode.Exclude
    private Set<FriendInvite> invitedByOtherUsers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @EqualsAndHashCode.Exclude
    private Set<Ticket> userTickets;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @EqualsAndHashCode.Exclude
    private Set<Notification> userNotifications;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @ManyToOne
    @JoinColumn(name = "current_user_subscription_id")
    private UserSubscription userSubscription;
    @ManyToMany(mappedBy = "userLikes", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Audio> audioLikes;
    @ManyToMany(mappedBy = "likeUsers")
    @EqualsAndHashCode.Exclude
    private Set<Playlist> likePlaylist;
}
