package com.example.officebureauapi.services.impl;

import com.example.officebureauapi.dto.ReservationDto;
import com.example.officebureauapi.entities.Reservation;
import com.example.officebureauapi.exceptions.InvalidIdException;
import com.example.officebureauapi.mappers.ReservationMapper;
import com.example.officebureauapi.repositories.ReservationRepository;
import com.example.officebureauapi.services.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private static final String NOT_FOUND = "Reservation.NotFound";
    private static final String RESERVATION_ID_NOT_FOUND = "Reservation.Id.NotFound";
    private static final String INVALID_RESERVATION_ID_FORMAT = "Reservation.Id.InvalidFormat";
    private static final String RESERVATION_EXISTS = "Reservation.Exists";

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public Page<ReservationDto> findAll(Pageable pageable) {
        return reservationRepository.findByIsDeletedIsFalse(pageable)
                .map(reservation -> reservationMapper.toDto(reservation, ReservationDto.builder().build()));
    }

    @Override
    public Page<ReservationDto> findAllByUserId(UUID userId, Pageable pageable) {
        return reservationRepository.findByCreatedBy(userId, pageable)
                .map(reservation -> reservationMapper.toDto(reservation, ReservationDto.builder().build()));
    }

    @Override
    public void save(ReservationDto reservationDto) {
        Reservation reservation = reservationMapper.toEntity(reservationDto, Reservation.builder().build());
        reservationRepository.save(reservation);
    }

    @Override
    public ReservationDto update(String reservationId, ReservationDto updatedDto) {
        Reservation existingReservation = findById(reservationId);

        reservationMapper.toEntity(updatedDto, existingReservation);
        reservationRepository.save(existingReservation);

        return reservationMapper.toDto(existingReservation, updatedDto);
    }

    @Override
    public void delete(String reservationId) {
        Reservation existingReservation = findById(reservationId);

        existingReservation.setDeleted(true);
        reservationRepository.save(existingReservation);
    }

    @Override
    public Reservation findById(String reservationId) {
        UUID id = UUID.fromString(reservationId);

        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(NOT_FOUND, reservationId)));

    }

    @Override
    public ReservationDto findReservationById(String id) {
        UUID reservationId;
        try {
            reservationId = UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new InvalidIdException(INVALID_RESERVATION_ID_FORMAT, ex);
        }

        return reservationRepository.findById(reservationId)
                .map(reservation -> reservationMapper.toDto(reservation, ReservationDto.builder().build()))
                .orElseThrow( () -> new EntityNotFoundException(String.format(NOT_FOUND, id)));
    }
}
