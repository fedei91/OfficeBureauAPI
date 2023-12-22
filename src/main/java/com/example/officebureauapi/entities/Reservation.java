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

@Data
@EqualsAndHashCode(of = {"id"})
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
    private String desktopId;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTstamp;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedTstamp;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Integer createdBy;
    @LastModifiedBy
    @Column(insertable = false)
    private Integer lastModifiedBy;

    @Column(name = "is_deleted", columnDefinition = "boolean default false", nullable = false)
    private boolean isDeleted = false;

}
