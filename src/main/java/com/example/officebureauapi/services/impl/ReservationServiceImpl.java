package com.example.officebureauapi.services.impl;

import com.example.officebureauapi.entities.Reservation;
import com.example.officebureauapi.repositories.ReservationRepository;
import com.example.officebureauapi.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }
}
