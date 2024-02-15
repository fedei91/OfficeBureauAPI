package com.example.officebureauapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentDto {
    UUID id;

    @NotBlank(message = "Department name cannot be blank")
    String name;

    @Builder.Default
    List<UUID> desktopIdList = new ArrayList<>();

    @Positive(message = "Max desktopts must be a positive value")
    int maxDesktops;

    @Positive(message = "Max chairs must be a positive value")
    int maxChairs;

    boolean isDeleted;
}
