package com.example.officebureauapi.mappers;

import com.example.officebureauapi.dto.ReservationDto;
import com.example.officebureauapi.entities.Reservation;
import org.mapstruct.*;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReservationMapper {

    ReservationDto toDto(Reservation reservation, @MappingTarget ReservationDto reservationDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "startTime", source = "startTstamp")
    @Mapping(target = "endTime", source = "endTstamp")
    Reservation toEntity(ReservationDto reservationDto, @MappingTarget Reservation reservation);
}
