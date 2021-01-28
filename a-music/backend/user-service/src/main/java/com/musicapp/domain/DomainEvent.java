package com.musicapp.domain;

import lombok.Value;

/**
 * Доменное событие.
 *
 * @author evgeniycheban
 */
@Value
public class DomainEvent<T> {

    T subject;
    DomainEventType type;
}
