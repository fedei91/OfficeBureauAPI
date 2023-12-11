package com.example.officebureauapi.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @UuidGenerator
    @Column(nullable = false)
    private UUID id;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "desktop_id")
    private String desktopId;

    private LocalDateTime startTstamp;
    private LocalDateTime endTstamp;

}
