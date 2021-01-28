package com.musicapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Сущность города
 */
@Entity
@Table(name = "cities")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Column(name = "city_name")
    private String cityName;
    @ManyToOne
    @JoinColumn(name = "country_id")
    @EqualsAndHashCode.Exclude
    private Country country;
}
