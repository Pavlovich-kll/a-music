package com.musicapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

/**
 * Сущность Плейлиста.
 *
 * @author lizavetashpinkova
 */
@Entity
@Table(name = "playlists")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_id")
    private DatabaseFile cover;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "playlists_audios",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "audio_id"))
    private Set<Audio> audios;
    @ManyToMany
    @JoinTable(name = "playlists_likes",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @EqualsAndHashCode.Exclude
    private Set<User> likeUsers;
}
