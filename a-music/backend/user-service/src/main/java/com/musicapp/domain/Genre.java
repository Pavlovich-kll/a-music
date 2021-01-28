package com.musicapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

/**
 * Сущность жанра.
 *
 * @author lizavetashpinkova
 */
@Entity
@Table(name = "genres")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;
    @Column(name = "title")
    private String title;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "genre")
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private List<Audio> audios;
}
