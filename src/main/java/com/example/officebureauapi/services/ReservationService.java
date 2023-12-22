package com.example.officebureauapi.services;

import com.example.officebureauapi.entities.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReservationService {

    List<Reservation> findAll();
}
