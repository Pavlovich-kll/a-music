package com.musicapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Сущность страны
 */
@Entity
@Table(name = "countries")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Column(name = "country_name")
    private String countryName;
}
