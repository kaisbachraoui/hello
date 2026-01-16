package com.example.securityfindings.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record ProjectRequest(
        @NotBlank String name,
        String description,
        @NotBlank String businessOwner,
        @NotBlank String technicalOwner,
        LocalDate startDate,
        LocalDate goLiveDate,
        @NotBlank String status
) {
}
