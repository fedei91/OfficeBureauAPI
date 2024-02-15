package com.example.officebureauapi.mappers;

import com.example.officebureauapi.dto.DepartmentDto;
import com.example.officebureauapi.entities.Department;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DepartmentMapper {

    DepartmentDto toDto(Department department, @MappingTarget DepartmentDto departmentDto);

    @Mapping(target = "id", ignore = true)
    Department toEntity(DepartmentDto departmentDto, @MappingTarget Department department);
}
