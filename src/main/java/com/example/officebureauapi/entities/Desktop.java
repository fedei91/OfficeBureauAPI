package com.example.officebureauapi.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "desktops")
public class Desktop {
    @Id
    @UuidGenerator
    @Column(nullable = false)
    private UUID id;

    @Column(name = "department_id")
    private String departmentId;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean isDeleted = false;

}
