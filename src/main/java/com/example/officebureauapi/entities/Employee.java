package com.example.officebureauapi.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @UuidGenerator
    @Column(nullable = false)
    private UUID id;

    private String name;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "supervisor_id")
    private String supervisorId;

    @OneToMany(mappedBy = "supervisorId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Employee> employeesInCharge = new HashSet<>();

    @Column(name = "is_deleted", columnDefinition = "boolean default false", nullable = false)
    private boolean isDeleted = false;
}
