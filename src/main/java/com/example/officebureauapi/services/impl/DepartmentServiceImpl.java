package com.example.officebureauapi.services.impl;

import com.example.officebureauapi.dto.DepartmentDto;
import com.example.officebureauapi.entities.Department;
import com.example.officebureauapi.exceptions.InvalidIdException;
import com.example.officebureauapi.mappers.DepartmentMapper;
import com.example.officebureauapi.repositories.DepartmentRepository;
import com.example.officebureauapi.services.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public Page<DepartmentDto> findAll(Pageable pageable) {
        return departmentRepository.findByIsDeletedIsFalse(pageable)
                .map(department -> departmentMapper.toDto(department, DepartmentDto.builder().build()));
    }

    @Override
    public Department findById(String departmentId) {
        UUID id = UUID.fromString(departmentId);

        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Department with id %s not found", departmentId)));
    }

    @Override
    public DepartmentDto findDepartmentById(String id) {
        UUID departmentId;
        try {
            departmentId = UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new InvalidIdException("Invalid department id format", ex);
        }

        return departmentRepository.findById(departmentId)
                .map(department -> departmentMapper.toDto(department, DepartmentDto.builder().build()))
                .orElseThrow(() -> new EntityNotFoundException(String.format("No department found with id %s", id)));
    }

    @Override
    public void save(DepartmentDto departmentDto) {
        Department department = Department.builder().build();
        department = departmentMapper.toEntity(departmentDto, department);
        departmentRepository.save(department);
    }

    @Override
    public DepartmentDto update(String departmentId, DepartmentDto updatedDepartmentDto) {
        Department existingDepartment = findById(departmentId);
        existingDepartment = departmentMapper.toEntity(updatedDepartmentDto, existingDepartment);
        departmentRepository.save(existingDepartment);

        return departmentMapper.toDto(existingDepartment, updatedDepartmentDto);
    }

    @Override
    public void delete(String departmentId) {
        Department existingDepartment = findById(departmentId);
        existingDepartment.setDeleted(true);

        departmentRepository.save(existingDepartment);
    }
}
