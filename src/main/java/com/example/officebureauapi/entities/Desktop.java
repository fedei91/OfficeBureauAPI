package com.example.officebureauapi.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@EqualsAndHashCode(of = {"id"})
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


}
