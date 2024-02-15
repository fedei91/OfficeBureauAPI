package com.example.officebureauapi.services;

import com.example.officebureauapi.dto.ReservationDto;
import com.example.officebureauapi.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReservationService {

    Page<ReservationDto> findAll(Pageable pageable);
    Page<ReservationDto> findAllByUserId(UUID createdBy, Pageable pageable);
    Reservation findById(String id);
    ReservationDto findReservationById(String id);
    void save(ReservationDto reservationDto);
    ReservationDto update(String id, ReservationDto dto);
    void delete(String id);

}
