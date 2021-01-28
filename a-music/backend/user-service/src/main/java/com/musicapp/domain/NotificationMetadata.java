package com.musicapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

/**
 * Сущность для работы с таблицей метаданных уведомлений
 *
 * @author i.dubrovin
 */
@Entity
@Table(name = "notification_metadata")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class NotificationMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "secondary_description")
    private String secondaryDescription;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "metadataId", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    private Set<Notification> setUsers;
}
