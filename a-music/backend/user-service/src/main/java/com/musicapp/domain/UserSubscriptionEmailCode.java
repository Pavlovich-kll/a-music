package com.musicapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "user_subscription_email_code")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class UserSubscriptionEmailCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_subscription_id")
    Long userSubscriptionId;

    @Column(name = "invited_user_id")
    Long invitedUserId;

    @Column(name = "code")
    String code;
}
