package com.example.officebureauapi.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "reservations")
public class Reservation {
    @Id
    @UuidGenerator
    @Column(nullable = false)
    private UUID id;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "desktop_id")
    private UUID desktopId;

    @Column(name = "department_id")
    private UUID departmentId;

    @Column(name = "booked_chairs", nullable = false)
    private int bookedChairs;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTstamp;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedTstamp;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private UUID createdBy;
    @LastModifiedBy
    @Column(insertable = false)
    private UUID lastModifiedBy;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean isDeleted = false;

    public void decreaseAvailability() {
        if (bookedChairs > 0) {
            bookedChairs--;
        }
    }

    public void increaseAvailability() {
        bookedChairs++;
    }
}
