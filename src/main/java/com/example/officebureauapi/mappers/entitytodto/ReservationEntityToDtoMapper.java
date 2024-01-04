package com.example.officebureauapi.mappers.entitytodto;

import com.example.officebureauapi.dto.ReservationDto;
import com.example.officebureauapi.entities.Reservation;
import com.example.officebureauapi.mappers.EntityToDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReservationEntityToDtoMapper implements EntityToDtoMapper<Reservation, ReservationDto> {
    @Override
    public ReservationDto toDto(Reservation entity) {
        ReservationDto dto = new ReservationDto();
        dto.setId(entity.getId().toString());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setDesktopId(entity.getDesktopId());
        dto.setCreatedTstamp(entity.getCreatedTstamp());
        dto.setLastModifiedTstamp(entity.getLastModifiedTstamp());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLastModifiedBy(entity.getLastModifiedBy());
        return dto;
    }

    @Override
    public Reservation toEntity(ReservationDto dto, Reservation entity) {
        if (dto.getId() != null && !dto.getId().isEmpty()) {
            entity.setId(UUID.fromString(dto.getId()));
        }

        entity.setEmployeeId(dto.getEmployeeId());
        entity.setDesktopId(dto.getDesktopId());
        entity.setDeleted(dto.isDeleted());
        entity.setCreatedTstamp(dto.getCreatedTstamp());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setLastModifiedTstamp(dto.getLastModifiedTstamp());
        entity.setLastModifiedBy(dto.getLastModifiedBy());
        return entity;
    }
}
