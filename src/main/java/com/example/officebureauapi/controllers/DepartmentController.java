package com.example.officebureauapi.controllers;

import com.example.officebureauapi.dto.DepartmentDto;
import com.example.officebureauapi.services.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/departments")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<DepartmentDto> getDepartmentById(
            @PathVariable String id
    ) {
        DepartmentDto department = departmentService.findDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDepartment(
            @RequestBody DepartmentDto departmentDto
    ) {
        departmentService.save(departmentDto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin::update')")
    public ResponseEntity<?> updateDepartment(
        @PathVariable String id,
        @RequestBody DepartmentDto updatedDepartment
    ) {
        departmentService.update(id, updatedDepartment);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin::delete')")
    public ResponseEntity<?> deleteDepartment(
        @PathVariable String id
    ) {
        departmentService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
