package com.example.officebureauapi.controllers;

import com.example.officebureauapi.dto.ReservationDto;
import com.example.officebureauapi.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservations")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Page<ReservationDto>> findAllReservations(
            Pageable pageable
    ) {
        return ResponseEntity.ok(reservationService.findAll(pageable));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<ReservationDto>> findAllByUserId(
            @PathVariable String id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(reservationService.findAllByUserId(UUID.fromString(id), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(
            @PathVariable String id
    ) {
        ReservationDto reservation = reservationService.findReservationById(id);
        return ResponseEntity.ok(reservation);
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
    @PreAuthorize("hasAuthority('admin::delete')")
    public ResponseEntity<?> deleteReservation(
            @PathVariable String id
    ) {
        reservationService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
