package com.example.officebureauapi.repositories;

import com.example.officebureauapi.entities.Desktop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DesktopRepository extends JpaRepository<Desktop, UUID> {
    Page<Desktop> findByIsDeletedIsFalse(Pageable pageable);
}
