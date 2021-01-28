package com.musicapp.service.impl;

import com.musicapp.domain.City;
import com.musicapp.domain.Concert;
import com.musicapp.dto.ConcertCreateDto;
import com.musicapp.dto.ConcertDto;
import com.musicapp.dto.ConcertUpdateDto;
import com.musicapp.dto.UnsoldTicketsForConcertDto;
import com.musicapp.exception.NotFoundException;
import com.musicapp.mapper.ConcertMapper;
import com.musicapp.repository.ConcertRepository;
import com.musicapp.service.CityService;
import com.musicapp.service.ConcertService;
import com.musicapp.util.constants.MessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Реализация сервиса для управления концертами.
 *
 * @author lizavetashpinkova
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertMapper concertMapper;
    private final CityService cityService;

    @Override
    public Concert getConcert(long id) {
        return concertRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Concert with following id has not been found: " + id);
                    return new NotFoundException(MessageConstants.CONCERT_ID_NOT_FOUND, "ID");
                });
    }


    @Transactional
    @Override
    public void deleteConcert(long id) {
        concertRepository.deleteById(id);
        log.info("Concert with id " + id + " has been deleted");
    }

    //можно убрать этот метод и использовать getAllConcertByConcertDateIsAfter(LocalDate.MIN)
    @Override
    public List<Concert> getAllConcerts() {
        log.info("List with with all concerts has been sent");
        return concertRepository.findAll();
    }

    @Override
    public List<Concert> getAllConcertByConcertDateIsAfter(LocalDate concertDate) {
        log.info("Sending list with concerts after date : " + concertDate);
        return concertRepository.findAllConcertByConcertDateIsAfter(concertDate);
    }

    @Transactional
    @Override
    public void updateConcert(long id, ConcertUpdateDto concertUpdateDto) {
        Concert updatedConcert = concertRepository.findById(id)
                .map(concert -> concertMapper.update(concertUpdateDto, concert))
                .orElseThrow(() -> {
                    log.error("Concert with id " + id + " has not been found");
                    return new NotFoundException(MessageConstants.CONCERT_ID_NOT_FOUND, "ID");
                });
        City city = cityService.getById(concertUpdateDto.getCityId());
        updatedConcert.setCity(city);
        concertRepository.save(updatedConcert);
    }

    @Override
    public List<UnsoldTicketsForConcertDto> countUnsoldTicketsForEachConcertByConcertDateIsAfter(LocalDate concertDate) {
        log.info("Sending list with unsold tickets combined with concerts after date : " + concertDate);
        return concertRepository.countUnsoldTicketsForEachConcertByConcertDateIsAfter(concertDate);
    }

    @Transactional
    @Override
    public ConcertDto create(ConcertCreateDto concertCreateDto) {
        log.info("Creating concert");
        Concert concert = concertMapper.map(concertCreateDto);
        City city = cityService.getById(concertCreateDto.getCityId());
        concert.setCity(city);
        concertRepository.save(concert);
        return concertMapper.map(concert);
    }
}
