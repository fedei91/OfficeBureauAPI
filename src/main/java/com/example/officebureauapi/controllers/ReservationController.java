package com.example.officebureauapi.controllers;

import com.example.officebureauapi.dto.ReservationDto;
import com.example.officebureauapi.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@AllArgsConstructor
//@PreAuthorize()
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationDto>> findAllReservations() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> makeReservation(
            @RequestBody ReservationDto reservationDto
            ) {
        reservationService.save(reservationDto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateReservation(
            @PathVariable String id,
            @RequestBody ReservationDto updatedReservation
    ) {
        reservationService.update(id, updatedReservation);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReservation(
            @PathVariable String id
    ) {
        reservationService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
