package com.example.officebureauapi.mappers.entitytodto;

import com.example.officebureauapi.dto.DepartmentDto;
import com.example.officebureauapi.entities.Department;
import com.example.officebureauapi.mappers.EntityToDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DepartmentEntityToDtoMapper implements EntityToDtoMapper<Department, DepartmentDto> {
    @Override
    public DepartmentDto toDto(Department entity) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(entity.getId().toString());
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public Department toEntity(DepartmentDto dto, Department entity) {
        if (dto.getId() != null && !dto.getId().isEmpty()) {
            entity.setId(UUID.fromString(dto.getId()));
        }
        entity.setName(dto.getName());
        entity.setDeleted(dto.isDeleted());
        return entity;
    }
}
