package com.example.officebureauapi.services;

import com.example.officebureauapi.dto.ReservationDto;
import com.example.officebureauapi.entities.Reservation;

import java.util.List;

public interface ReservationService {

    List<ReservationDto> findAll();
    Reservation findById(String id);
    void save(ReservationDto reservationDto);
    ReservationDto update(String id, ReservationDto dto);
    void delete(String id);

}
