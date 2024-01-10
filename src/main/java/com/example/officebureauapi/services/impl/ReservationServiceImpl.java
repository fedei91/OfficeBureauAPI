package com.example.officebureauapi.services.impl;

import com.example.officebureauapi.dto.ReservationDto;
import com.example.officebureauapi.entities.Reservation;
import com.example.officebureauapi.exceptions.InvalidIdException;
import com.example.officebureauapi.mappers.entitytodto.ReservationEntityToDtoMapper;
import com.example.officebureauapi.repositories.ReservationRepository;
import com.example.officebureauapi.services.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationEntityToDtoMapper reservationEntityToDtoMapper;

    @Override
    public Page<ReservationDto> findAll(Pageable pageable) {
        return reservationRepository.findByIsDeletedIsFalse(pageable)
                .map(reservationEntityToDtoMapper::toDto);
    }

    @Override
    public Page<ReservationDto> findAllByUserId(UUID userId, Pageable pageable) {
        return reservationRepository.findByCreatedBy(userId, pageable)
                .map(reservationEntityToDtoMapper::toDto);
    }

    @Override
    public void save(ReservationDto reservationDto) {
        Reservation reservation = Reservation.builder().build();
        reservation = reservationEntityToDtoMapper.toEntity(reservationDto, reservation);

        reservationRepository.save(reservation);
    }

    @Override
    public ReservationDto update(String reservationId, ReservationDto updatedDto) {
        Reservation existingReservation = findById(reservationId);

        existingReservation = reservationEntityToDtoMapper.toEntity(updatedDto, existingReservation);
        reservationRepository.save(existingReservation);

        return reservationEntityToDtoMapper.toDto(existingReservation);
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
                .orElseThrow(() -> new EntityNotFoundException(String.format("Reservation with id %s not found", reservationId)));

    }

    @Override
    public ReservationDto findReservationById(String id) {
        UUID reservationId;
        try {
            reservationId = UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new InvalidIdException("Invalid reservation id format", ex);
        }

        Optional<Reservation> existingReservation = reservationRepository.findById(reservationId);

        return existingReservation.map(reservationEntityToDtoMapper::toDto)
                .orElseThrow( () -> new EntityNotFoundException(String.format("No reservation found with id %s", id)));
    }
}
