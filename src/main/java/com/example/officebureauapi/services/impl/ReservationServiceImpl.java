package com.example.officebureauapi.services.impl;

import com.example.officebureauapi.dto.ReservationDto;
import com.example.officebureauapi.entities.Desktop;
import com.example.officebureauapi.entities.Reservation;
import com.example.officebureauapi.exceptions.DesktopNotFoundException;
import com.example.officebureauapi.exceptions.InvalidIdException;
import com.example.officebureauapi.exceptions.ReservationConflictException;
import com.example.officebureauapi.mappers.ReservationMapper;
import com.example.officebureauapi.repositories.DesktopRepository;
import com.example.officebureauapi.repositories.ReservationRepository;
import com.example.officebureauapi.repositories.specifications.ReservationSpecifications;
import com.example.officebureauapi.services.DesktopService;
import com.example.officebureauapi.services.ReservationService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private static final String NOT_FOUND = "Reservation.NotFound";
    private static final String RESERVATIONS_DESKTOP_NOT_FOUND = "Reservations.Desktop.NotFound";
    private static final String INVALID_RESERVATION_ID_FORMAT = "Reservation.Id.InvalidFormat";
    private static final String RESERVATION_EXISTS = "Reservation.Exists";
    private static final String RESERVATION_EXCEEDS_CAPACITY = "Reservation.Exceeds.Capacity";

    private final DesktopService desktopService;
    private final DesktopRepository desktopRepository;
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
        boolean reservationExists = reservationRepository.existsById(reservationDto.getId());
        if (reservationExists) {
            throw new EntityExistsException(String.format(RESERVATION_EXISTS, reservationDto.getId()));
        }

        UUID desktopId = reservationDto.getDesktopId();
        Reservation reservation = reservationMapper.toEntity(reservationDto, Reservation.builder().build());

        if (desktopService.checkAvailability(desktopId, reservation)) {
            Desktop desktop = desktopRepository.findById(desktopId)
                    .orElseThrow(() -> new DesktopNotFoundException(String.format(RESERVATIONS_DESKTOP_NOT_FOUND, desktopId)));

            if (checkIntervalAvailability(desktop, reservation)) {
                desktop.getReservations().add(reservation);
                reservation.setDesktopId(desktop.getId());
                reservationRepository.saveAndFlush(reservation);
            } else {
                throw new ReservationConflictException(RESERVATION_EXCEEDS_CAPACITY);
            }
        } else {
            throw new ReservationConflictException(RESERVATION_EXCEEDS_CAPACITY);
        }
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

    public boolean checkIntervalAvailability(Desktop desktop, Reservation newReservation) {
        Specification<Reservation> spec = ReservationSpecifications.conflictingReservation(
                desktop.getId(), newReservation.getStartTime(), newReservation.getEndTime());

        List<Reservation> overlappingReservations = reservationRepository.findAll(spec);

        int bookedChairsInInterval = overlappingReservations.stream()
                .mapToInt(Reservation::getBookedChairs)
                .sum();

        int availableChairs = desktop.getTotalChairs() - bookedChairsInInterval;
        return availableChairs >= newReservation.getBookedChairs();
    }

}
