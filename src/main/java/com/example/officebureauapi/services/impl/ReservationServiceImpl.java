package com.example.officebureauapi.services.impl;

import com.example.officebureauapi.dto.ReservationDto;
import com.example.officebureauapi.entities.Reservation;
import com.example.officebureauapi.mappers.entitytodto.ReservationEntityToDtoMapper;
import com.example.officebureauapi.repositories.ReservationRepository;
import com.example.officebureauapi.services.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationEntityToDtoMapper reservationEntityToDtoMapper;

    @Override
    public List<ReservationDto> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(reservation -> reservationEntityToDtoMapper.toDto(reservation))
                .collect(Collectors.toList());
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
}
