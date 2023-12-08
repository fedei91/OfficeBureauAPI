package com.example.officebureauapi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Desktop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


}
