package com.musicapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @Column(name = "description")
    private String description;

    @Column(name = "trial_period")
    private int trialPeriod;

    @Column(name = "user_capacity")
    private int userCapacity;

    @OneToMany(mappedBy = "subscription")
    private Set<UserSubscription> userSubscriptions;
}
