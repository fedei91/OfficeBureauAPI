package com.example.officebureauapi.services;

import com.example.officebureauapi.dto.DepartmentDto;
import com.example.officebureauapi.entities.Department;

import java.util.List;

public interface DepartmentService {

    Department findById(String id);
    DepartmentDto findDepartmentById(String id);
    void save(DepartmentDto departmentDto);
    DepartmentDto update(String id, DepartmentDto dto);
    void delete(String id);
}
