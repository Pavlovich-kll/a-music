package com.musicapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Set;

/**
 * Сущность концерта.
 *
 * @author lizavetashpinkova
 */
@Entity
@Table(name = "concerts")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "performer")
    private String performer;
    @Column(name = "concert_date")
    private LocalDate concertDate;
    @Min(1)
    @Column(name = "age_restriction")
    private int ageRestriction;
    @Min(1)
    @Column(name = "total_number_of_tickets")
    private int totalNumberOfTickets;
    @Min(1)
    @Column(name = "base_price")
    private int basePrice;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "concert")
    @EqualsAndHashCode.Exclude
    private Set<Ticket> concertTickets;
    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id")
    @EqualsAndHashCode.Exclude
    private City city;
}
