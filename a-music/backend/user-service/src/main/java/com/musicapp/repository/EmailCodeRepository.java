package com.musicapp.repository;

import com.musicapp.domain.EmailCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репоизторий для работы с таблицей кодов подтверждения e-mail.
 *
 * @author a.nagovicyn
 */
public interface EmailCodeRepository extends JpaRepository<EmailCode, Long> {

    /**
     * Ищет запись в бд по коду подтверждения
     *
     * @param code отправленный код подтверждения
     * @return Optional сущность кода подтверждения
     */
    Optional<EmailCode> findByCode(String code);
}
