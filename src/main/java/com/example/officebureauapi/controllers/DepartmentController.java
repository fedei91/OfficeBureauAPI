package com.example.officebureauapi.controllers;

import com.example.officebureauapi.dto.DepartmentDto;
import com.example.officebureauapi.services.DepartmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/departments")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    @PreAuthorize("hasAuthority('department:read')")
    public ResponseEntity<Page<DepartmentDto>> findAllDepartments(
            Pageable pageable
    ) {
        return ResponseEntity.ok(departmentService.findAll(pageable));
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('department:read')")
    public ResponseEntity<DepartmentDto> getDepartmentById(
            @PathVariable String id
    ) {
        DepartmentDto department = departmentService.findDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('department:create')")
    public ResponseEntity<?> createDepartment(
            @RequestBody @Valid DepartmentDto departmentDto
    ) {
        departmentService.save(departmentDto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('department::update')")
    public ResponseEntity<?> updateDepartment(
        @PathVariable String id,
        @RequestBody @Valid DepartmentDto updatedDepartment
    ) {
        departmentService.update(id, updatedDepartment);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('department::delete')")
    public ResponseEntity<?> deleteDepartment(
        @PathVariable String id
    ) {
        departmentService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
