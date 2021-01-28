package com.musicapp.domain;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Set;

/**
 * Сущность аудио записи
 */
@Entity
@Table(name = "audios")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class Audio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;
    @Column(name = "title")
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @Type(type = "pgsql_enum")
    private AudioType type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private Genre genre;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "album_id")
    private Album album;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private DatabaseFile file;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_id")
    private DatabaseFile cover;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "audios_likes",
            joinColumns = {@JoinColumn(name = "audio_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @EqualsAndHashCode.Exclude
    private Set<User> userLikes;
}
