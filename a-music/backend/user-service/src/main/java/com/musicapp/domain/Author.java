package com.musicapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

/**
 * Сущность автора.
 *
 * @author lizavetashpinkova
 */
@Entity
@Table(name = "authors")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private List<Audio> audios;
}
