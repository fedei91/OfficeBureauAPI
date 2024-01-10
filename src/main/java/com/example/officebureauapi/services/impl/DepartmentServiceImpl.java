package com.example.officebureauapi.services.impl;

import com.example.officebureauapi.dto.DepartmentDto;
import com.example.officebureauapi.entities.Department;
import com.example.officebureauapi.exceptions.InvalidIdException;
import com.example.officebureauapi.mappers.entitytodto.DepartmentEntityToDtoMapper;
import com.example.officebureauapi.repositories.DepartmentRepository;
import com.example.officebureauapi.services.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentEntityToDtoMapper departmentEntityToDtoMapper;

    @Override
    public Page<DepartmentDto> findAll(Pageable pageable) {
        return departmentRepository.findByIsDeletedIsFalse(pageable)
                .map(departmentEntityToDtoMapper::toDto);
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

        Optional<Department> existingDepartment = departmentRepository.findById(departmentId);

        return existingDepartment.map(departmentEntityToDtoMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No department found with id %s", id)));
    }

    @Override
    public void save(DepartmentDto departmentDto) {
        Department department = Department.builder().build();
        department = departmentEntityToDtoMapper.toEntity(departmentDto, department);
        departmentRepository.save(department);
    }

    @Override
    public DepartmentDto update(String departmentId, DepartmentDto updatedDto) {
        Department existingDepartment = findById(departmentId);
        existingDepartment = departmentEntityToDtoMapper.toEntity(updatedDto, existingDepartment);
        departmentRepository.save(existingDepartment);

        return departmentEntityToDtoMapper.toDto(existingDepartment);
    }

    @Override
    public void delete(String departmentId) {
        Department existingDepartment = findById(departmentId);
        existingDepartment.setDeleted(true);

        departmentRepository.save(existingDepartment);
    }
}
