package com.example.officebureauapi.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @UuidGenerator
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Desktop> desktops = new ArrayList<>();

    @Column(name = "max_desktops", nullable = false)
    private int maxDesktops;

    @Column(name = "max_chairs", nullable = false)
    private int maxChairs;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean isDeleted = false;

    public int getCurrentDesktopUsage() {
        return desktops.size();
    }

    public int getCurrentChairUsage() {
        return desktops.stream()
                .mapToInt(Desktop::getTotalChairs)
                .sum();
    }

    public int decreaseAvailability() {
        int remainingDesktopCapacity = maxDesktops - getCurrentDesktopUsage();
        if (remainingDesktopCapacity <= 0) {
            throw new IllegalArgumentException("Maximum allowed desktops for the department reached");
        }
        return remainingDesktopCapacity;
    }

    public int calculateMaxChairs() {
        return maxChairs - getCurrentChairUsage();
    }

}
