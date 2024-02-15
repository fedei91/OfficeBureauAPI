package com.example.officebureauapi.repositories.specifications;

import com.example.officebureauapi.entities.Reservation;
import com.example.officebureauapi.entities.Reservation_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ReservationSpecifications {

    static Specification<Reservation> conflictingReservation(UUID desktopId, LocalDateTime startTime, LocalDateTime endTime) {
        return (root, query, builder) -> builder.and(
                builder.equal(root.get(Reservation_.DESKTOP_ID), desktopId),
                builder.or(
                        builder.between(root.get(Reservation_.START_TIME), startTime, endTime),
                        builder.between(root.get(Reservation_.END_TIME), startTime, endTime),
                        builder.and(
                                builder.lessThanOrEqualTo(root.get(Reservation_.START_TIME), startTime),
                                builder.greaterThanOrEqualTo(root.get(Reservation_.END_TIME), endTime)
                        )
                )
        );
    }
}
