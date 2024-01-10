package com.example.officebureauapi.services;

import com.example.officebureauapi.dto.DepartmentDto;
import com.example.officebureauapi.entities.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentService {

    Page<DepartmentDto> findAll(Pageable pageable);
    Department findById(String id);
    DepartmentDto findDepartmentById(String id);
    void save(DepartmentDto departmentDto);
    DepartmentDto update(String id, DepartmentDto dto);
    void delete(String id);
}
