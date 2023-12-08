package com.example.officebureauapi.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
