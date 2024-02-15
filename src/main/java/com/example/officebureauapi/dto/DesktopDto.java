package com.example.officebureauapi.dto;

import com.example.officebureauapi.enums.DesktopType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DesktopDto {

    UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    DesktopType desktopType;

    @Positive(message = "Total chairs must be a positive value")
    int totalChairs;

    @NotNull(message = "Desktop must belong to a department")
    UUID departmentId;

    boolean isDeleted;

    public void setTotalChairs() {
        this.totalChairs = this.desktopType.getCapacity();
    }
}
