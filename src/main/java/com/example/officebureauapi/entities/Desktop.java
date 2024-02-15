package com.example.officebureauapi.entities;

import com.example.officebureauapi.enums.DesktopType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department")
    private Department department;

    @Column(name = "total_chairs", nullable = false)
    private int totalChairs;

    @Enumerated(EnumType.STRING)
    private DesktopType desktopType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean isDeleted = false;

    public void setTotalChairs(int totalChairs) {
        this.totalChairs = this.desktopType.getCapacity();
    }

}
