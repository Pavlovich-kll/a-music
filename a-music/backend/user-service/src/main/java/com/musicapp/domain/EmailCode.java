package com.musicapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Сущность кода подтверждения e-mail
 *
 * @author a.nagovicyn
 */
@Entity
@Table(name = "email_codes")
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class EmailCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "code")
    private String code;
}
