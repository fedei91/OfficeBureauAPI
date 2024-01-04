package com.example.officebureauapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationDto {
    String id;
    String employeeId;
    String desktopId;
    LocalDateTime createdTstamp;
    LocalDateTime lastModifiedTstamp;

    UUID createdBy;
    UUID lastModifiedBy;
    boolean isDeleted;
}
