package com.example.officebureauapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.format.annotation.DateTimeFormat;

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
    UUID id;
    String employeeId;
    UUID desktopId;

    @Positive(message = "Booked chairs must be a positive value")
    int bookedChairs;

    @NotNull(message = "Start time cannot be null")
    @FutureOrPresent(message = "Start time must be in the future or present")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime startTstamp;

    @NotNull(message = "End time cannot be null")
    @Future(message = "End time must be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime endTstamp;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime createdTstamp;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime lastModifiedTstamp;

    UUID createdBy;
    UUID lastModifiedBy;
    boolean isDeleted;

    @AssertTrue(message = "Start time must be previous to end time")
    private boolean isValidDataRange() {
        return startTstamp != null && endTstamp != null && startTstamp.isBefore(endTstamp);
    }
}
