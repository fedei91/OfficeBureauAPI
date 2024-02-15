package com.example.officebureauapi.repositories;

import com.example.officebureauapi.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID>, JpaSpecificationExecutor<Reservation> {
    Page<Reservation> findByIsDeletedIsFalse(Pageable pageable);
    Page<Reservation> findByCreatedBy(UUID createdBy, Pageable pageable);
}
