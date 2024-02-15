package com.example.officebureauapi.mappers;

import com.example.officebureauapi.dto.DesktopDto;
import com.example.officebureauapi.entities.Department;
import com.example.officebureauapi.entities.Desktop;
import com.example.officebureauapi.services.DepartmentService;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = DepartmentMapper.class
)
public interface DesktopMapper {

    DesktopDto toDto(Desktop desktop, @MappingTarget DesktopDto desktopDto);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "department.id", source = "departmentId")
    })
    Desktop toEntity(DesktopDto desktopDto, @MappingTarget Desktop desktop);

    @Named("uuidToDepartment")
    default Department mapUUIDToEntity(UUID departmentId, @Context DepartmentService departmentService) {
        System.out.println("mapUUIDToEntity default method invoked");
        if (departmentId == null) {
            return null;
        }

        return departmentService.getDepartmentById(departmentId);
    }
}
