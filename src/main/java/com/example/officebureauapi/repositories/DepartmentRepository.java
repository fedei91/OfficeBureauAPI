package com.example.officebureauapi.repositories;

import com.example.officebureauapi.entities.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    Page<Department> findByIsDeletedIsFalse(Pageable pageable);
}
