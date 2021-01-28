package com.musicapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Сущность для работы с таблицей уведомлений
 *
 * @author i.dubrovin
 */
@Entity
@Table(name = "notification")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "notification_metadata_id", nullable = false)
    private NotificationMetadata metadataId;
    @Column(name = "mobile_notification", columnDefinition = "boolean default true")
    private boolean mobileNotification;
    @Column(name = "mail_notification", columnDefinition = "boolean default true")
    private boolean mailNotification;
    @Column(name = "text_notification", columnDefinition = "boolean default true")
    private boolean textNotification;
}
