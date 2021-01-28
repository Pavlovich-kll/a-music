package com.musicapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "user_subscriptions")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class UserSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "actual_price")
    private Long actualPrice;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "is_valid")
    private boolean isValid;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @Column(name = "host_user_id")
    private Long hostUser;

    @OneToMany(mappedBy = "userSubscription")
    private Set<User> invitedUsers;
}
