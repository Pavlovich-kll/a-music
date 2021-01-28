package com.musicapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Сущность музыкального альбома
 */
@Entity
@Table(name = "albums")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_id")
    private DatabaseFile cover;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "album")
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private List<Audio> audios;
}
