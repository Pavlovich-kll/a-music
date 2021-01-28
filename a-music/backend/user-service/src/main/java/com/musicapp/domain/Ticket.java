package com.musicapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;

/**
 * Сущность Билета.
 *
 * @author lizavetashpinkova
 */
@Entity
@Table(name = "tickets")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;
    @Min(1)
    @Column(name = "price")
    private int price;
    @Column(name = "date_of_payment")
    private LocalDate dateOfPayment;
    @ManyToOne(optional = false)
    @JoinColumn(name = "concert_id")
    private Concert concert;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}
