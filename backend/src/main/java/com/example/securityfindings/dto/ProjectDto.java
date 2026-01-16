package com.example.securityfindings.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ProjectDto(
        UUID id,
        String name,
        String description,
        String businessOwner,
        String technicalOwner,
        LocalDate startDate,
        LocalDate goLiveDate,
        String status
) {
}
