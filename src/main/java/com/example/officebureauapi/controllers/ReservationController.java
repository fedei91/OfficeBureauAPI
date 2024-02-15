package com.example.officebureauapi.controllers;

import com.example.officebureauapi.dto.ReservationDto;
import com.example.officebureauapi.services.ReservationService;
import jakarta.validation.Valid;
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
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    @PreAuthorize("hasAuthority('reservation:read')")
    public ResponseEntity<Page<ReservationDto>> findAllReservations(
            Pageable pageable
    ) {
        return ResponseEntity.ok(reservationService.findAll(pageable));
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('reservation:read')")
    public ResponseEntity<Page<ReservationDto>> findAllByUserId(
            @PathVariable String id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(reservationService.findAllByUserId(UUID.fromString(id), pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('reservation:read')")
    public ResponseEntity<ReservationDto> getReservationById(
            @PathVariable String id
    ) {
        ReservationDto reservation = reservationService.findReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('reservation:create')")
    public ResponseEntity<?> makeReservation(
            @RequestBody @Valid ReservationDto reservationDto
            ) {
        reservationService.save(reservationDto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('reservation:update')")
    public ResponseEntity<?> updateReservation(
            @PathVariable String id,
            @RequestBody @Valid ReservationDto updatedReservation
    ) {
        reservationService.update(id, updatedReservation);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('reservation::delete')")
    public ResponseEntity<?> deleteReservation(
            @PathVariable String id
    ) {
        reservationService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
